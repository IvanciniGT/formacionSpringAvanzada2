package com.animalitos.service.mappers;

import org.mapstruct.Mapper;

import com.animalitos.entity.Animalito;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;

@Mapper
public interface Mapeador {

	// La propieada nombre -> name
	DatosAnimalito animalito2DatosAnimalito(Animalito animalito);
	Animalito datosNuevoAnimalito2Animalito(DatosNuevoAnimalito datosNuevoAnimalito);
	DatosNuevoAnimalito animalito2DatosNuevoAnimalito(Animalito animalito);
	
}
/* 
   El automapper me genera una clase que implementa esta interfaz, añadiendo a mi función el códig:o
 
    
	public static DatosAnimalito animalito2DatosAnimalito(Animalito animalito) {
		DatosAnimalito datosAnimalito = new DatosAnimalito();
		datosAnimalito.setNombre(animalito.getNombre());
		datosAnimalito.setEdad(animalito.getEdad());
		datosAnimalito.setColor(animalito.getColor());
		datosAnimalito.setTipo(animalito.getTipo());
		datosAnimalito.setId(animalito.getId());
		return datosAnimalito;
	}
	
	Para obtener una instancia de esa clase que el Autommaper me genera, escribo:

		Mapeadores miMapeador = Mappers.getMapper(Mapeadores.class);

 */
