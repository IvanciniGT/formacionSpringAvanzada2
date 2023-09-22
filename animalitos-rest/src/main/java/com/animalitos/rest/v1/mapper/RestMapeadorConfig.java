package com.animalitos.rest.v1.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestMapeadorConfig {

		@Bean
		// Si alguien te pide un objeto de tipo Mapeadores, devuelve éste:
		Mapeador configurarMapeadorRest() {
			return Mappers.getMapper(Mapeador.class);
		}
}
