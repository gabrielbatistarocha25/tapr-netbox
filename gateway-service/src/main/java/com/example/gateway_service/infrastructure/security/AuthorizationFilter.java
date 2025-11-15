package com.example.gateway_service.infrastructure.security;

// --- Imports Necessários ---
import java.nio.charset.StandardCharsets;
import java.util.Map; // <-- IMPORT ADICIONADO

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

// Imports da biblioteca JWT
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
// Import da RoleType do Gateway
import com.example.gateway_service.domain.user.vo.RoleType;

import reactor.core.publisher.Mono;
// --- Fim dos Imports ---

@Component
public class AuthorizationFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // ---- INÍCIO DA CORREÇÃO ----
    // O mapa agora é preenchido com as rotas que exigem autenticação
    private static final Map<String, RoleType> routeRoles;

    static {
        // Rotas que começam com "/organization-service" exigem no mínimo a role CUSTOMER
        // Rotas que começam com "/inventory-service" exigem no mínimo a role CUSTOMER
        // Rotas que começam com "/infrastructure-service" exigem no mínimo a role CUSTOMER
        // Rotas do auth-service (login/registro) e service-discovery (eureka) não estão aqui,
        // logo, são públicas.
        routeRoles = Map.of(
            "/organization-service", RoleType.CUSTOMER,
            "/inventory-service", RoleType.CUSTOMER,
            "/infrastructure-service", RoleType.CUSTOMER
        );
    }
    // ---- FIM DA CORREÇÃO ----


    // Método isAuthorized precisa existir
    private boolean isAuthorized(String path, RoleType role) {
        for (Map.Entry<String, RoleType> entry : routeRoles.entrySet()) {
            // O path da requisição começa com o path protegido definido no mapa?
            // Ex: path = "/inventory-service/api/devices/1", entry.getKey() = "/inventory-service/api/devices" -> true
            if (path.startsWith(entry.getKey())) {
                // A role do usuário (do token) cobre a role mínima necessária para a rota?
                return role.covers(entry.getValue());
            }
        }
        // Se a rota não foi encontrada no mapa 'routeRoles', considera-se permitida por padrão
        // (ou seja, não requer uma role específica listada aqui).
        // A validação de token (se ele existe e é válido) é feita antes.
        return true;
    }

    // Método helper para retornar erro 401
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        // Verifica se a rota ATUAL está na lista de protegidas por role específica
        // Usa a variável 'routeRoles' que agora existe (mesmo vazia)
        boolean isProtectedRouteByRole = routeRoles.entrySet().stream()
                                          .anyMatch(entry -> path.startsWith(entry.getKey()));

        // Se NÃO for uma rota protegida por ROLE ESPECÍFICA listada no mapa...
        if (!isProtectedRouteByRole) {
            // ... simplesmente continua a cadeia de filtros.
            return chain.filter(exchange);
        }

        // --- Se a rota ESTÁ no mapa routeRoles, executa a validação do token ---

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange); // Retorna 401 se não tiver header Bearer
        }

        String token = authHeader.substring(7);
        DecodedJWT jwt; // Variável declarada
        try {
            // Usa as classes importadas da biblioteca JWT
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token); // Atribui o resultado à variável jwt
        } catch (Exception e) {
            // Se a verificação falhar (token inválido, expirado, assinatura errada)
            return unauthorized(exchange); // Retorna 401
        }

        // Verifica se é um token de acesso
        String tokenType = jwt.getClaim("type").asString();
        if (tokenType == null || !tokenType.equals("access")) {
            return unauthorized(exchange); // Rejeita se não for 'access'
        }

        // Extrai a role do token
        String userRoleType = jwt.getClaim("role").asString();
        RoleType role; // Variável declarada
        try {
            // Usa a RoleType importada do pacote correto
            role = RoleType.valueOf(userRoleType); // Converte String para Enum
        } catch (Exception e) {
            // Se a role no token não for válida (não existe no Enum RoleType)
            return unauthorized(exchange); // Retorna 401
        }
        // --- Fim da lógica de validação do token ---


        // Verifica a permissão da role extraída para a rota específica
        // Chama o método isAuthorized que agora existe
        if (!isAuthorized(path, role)) {
            // Se o método retornar false (role insuficiente)
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN); // Retorna 403 Forbidden
            return exchange.getResponse().setComplete();
        }

        // Se chegou até aqui, o token é válido e a role permite o acesso à rota protegida
        return chain.filter(exchange); // Continua para o próximo filtro ou para o serviço de destino
   }
}