package com.pokemon.registry.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PokemonRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonRegistryServiceApplication.class, args);
	}

}
