package com.animalitos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.animalitos.entity.Animalito;
import com.animalitos.repository.AnimalitosRepository;
import com.animalitos.service.AnimalitosService;
import com.animalitos.service.EmailsService;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;
import com.animalitos.service.mappers.Mapeador;

@Service 
// Así le decimos a Spring que cuando alguien pida una inyección de dependencias de un 
// AnimalitosService se ele entregue una instancia de esta clase.
/*
@Bean... dentro de una clase anotada con @Configuration
public AnimalitosService generarAnimalitosService() {
	return new AnimalitosServiceImpl();
}
*/	

// En este fichero va la lógica de NEGOCIO
public class AnimalitosServiceImpl implements AnimalitosService {

	private final AnimalitosRepository repositorio;
	private final Mapeador miMapeador;
	private final EmailsService servicioEmails;
	
	public AnimalitosServiceImpl(AnimalitosRepository miRepositorio, Mapeador miMapeador, EmailsService servicioEmails) { // Inyección de dependencias
		this.repositorio = miRepositorio;
		this.miMapeador=miMapeador;
		this.servicioEmails = servicioEmails;
	}
	
	@Override
	public List<DatosAnimalito> recuperarAnimalitos() throws Exception {
		List<Animalito> animalitos = repositorio.findAll();
		return animalitos.stream()											// Para cada animalito
						 .map( miMapeador::animalito2DatosAnimalito )		// Lo transformo en un Datos animalito
						 .collect(Collectors.toList());						// Y devuelvo una lista
		/*
		List<DatosAnimalito> datos = new ArrayList<>();
		for(Animalito animalito:animalitos) {
			datos.add(animalito2DatosAnimalito(animalito));
		}
		return datos;
		*/
	}

	@Override
	public Optional<DatosAnimalito> recuperarAnimalito(long id) throws Exception {
		return repositorio.findById(id)										// Recupero el Animalito
						  .map( miMapeador::animalito2DatosAnimalito );		// Lo transformo en un DatosAnimalito
	}

	@Override
	public DatosAnimalito altaAnimalito(DatosNuevoAnimalito datosNuevoAnimalito) throws Exception {
		Animalito animalito = miMapeador.datosNuevoAnimalito2Animalito(datosNuevoAnimalito);
		// Persistir la información
		Animalito animalitoGuardado = repositorio.save(animalito);
		// Enviar un email
		servicioEmails.enviarEmail("altas@animalitosfermin.com",
				"Nuevo animalito disponible", 
				"Se ha dado de alta el animalito: "+animalitoGuardado.getNombre());
		return miMapeador.animalito2DatosAnimalito(animalitoGuardado);
	}
	
}
