package com.animalitos.service.mappers;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapeadorConfig {

		@Bean
		// Si alguien te pide un objeto de tipo Mapeadores, devuelve éste:
		Mapeador configurarMapeador() {
			return Mappers.getMapper(Mapeador.class);
		}
}
