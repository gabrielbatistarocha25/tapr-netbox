# Usa a imagem base do Java 17 (mesma versão do seu pom.xml)
FROM eclipse-temurin:17-jdk 
# Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia todo o conteúdo da pasta atual (organization-service) para /app no container
COPY . .
# Dá permissão de execução para o Maven Wrapper
RUN chmod +x mvnw
# Expõe a porta interna que o Spring Boot usa por padrão
EXPOSE 8080 
# Comando para iniciar a aplicação usando o Maven Wrapper
ENTRYPOINT ["sh", "mvnw", "spring-boot:run"]