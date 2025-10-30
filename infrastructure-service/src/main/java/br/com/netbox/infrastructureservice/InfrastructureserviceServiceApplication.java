package br.com.netbox.infrastructureservice;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class InfrastructureserviceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfrastructureserviceServiceApplication.class, args);
    }
}