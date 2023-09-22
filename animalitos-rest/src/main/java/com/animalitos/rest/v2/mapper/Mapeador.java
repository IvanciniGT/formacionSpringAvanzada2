package com.animalitos.rest.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.animalitos.rest.v2.dto.DatosAnimalitoRest;
import com.animalitos.rest.v2.dto.DatosNuevoAnimalitoRest;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;

@Mapper
public interface Mapeador {

	// La propieada nombre -> name
	@Mapping(source ="nombre", target ="name") // Y ESTO ES CODIGO, QUE TENGO QUE PROBAR!
	@Mapping(source ="tipo", target ="type")
	@Mapping(source ="edad", target ="age")
	@Mapping(source ="peso", target ="weight")
	DatosAnimalitoRest datosAnimalito2DatosAnimalitoRest(DatosAnimalito animalito);

	
	@Mapping(source ="name", target ="nombre")
	@Mapping(source ="type", target ="tipo")
	@Mapping(source ="age", target ="edad")
	@Mapping(source ="weight", target ="peso")
	DatosNuevoAnimalito datosAnimalitoRest2DatosAnimalito(DatosNuevoAnimalitoRest animalito);
	
}