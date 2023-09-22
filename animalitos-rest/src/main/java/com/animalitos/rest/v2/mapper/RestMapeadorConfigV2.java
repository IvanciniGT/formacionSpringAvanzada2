package com.animalitos.rest.v2.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestMapeadorConfigV2 {

		@Bean
		// Si alguien te pide un objeto de tipo Mapeadores, devuelve éste:
		Mapeador configurarMapeadorRestV2() {
			return Mappers.getMapper(Mapeador.class);
		}
}
