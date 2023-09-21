package com.animalitos.service;

import java.util.List;
import java.util.Optional;

import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;

public interface AnimalitosService {

	List<DatosAnimalito> recuperarAnimalitos() throws Exception;
	Optional<DatosAnimalito> recuperarAnimalito(long id) throws Exception;
	DatosAnimalito altaAnimalito(DatosNuevoAnimalito datosNuevoAnimalito) throws Exception;
	//eliminarAnimalito();
	//modificarAnimalito();
}
