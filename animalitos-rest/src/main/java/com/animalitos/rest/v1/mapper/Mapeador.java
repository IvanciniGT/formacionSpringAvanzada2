package com.animalitos.rest.v1.mapper;

import org.mapstruct.Mapper;

import com.animalitos.rest.v1.dto.DatosAnimalitoRest;
import com.animalitos.rest.v1.dto.DatosNuevoAnimalitoRest;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;

@Mapper
public interface Mapeador {

	// La propieada nombre -> name
	DatosAnimalitoRest datosAnimalito2DatosAnimalitoRest(DatosAnimalito animalito);
	DatosNuevoAnimalito datosAnimalitoRest2DatosAnimalito(DatosNuevoAnimalitoRest animalito);
	
}