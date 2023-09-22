package com.animalitos.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.animalitos.AplicacionDePrueba;
import com.animalitos.entity.Animalito;
import com.animalitos.repository.AnimalitosRepository;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.mappers.Mapeador;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AplicacionDePrueba.class}) // Arranca en segundo plano la app
public class AnimalitosServiceIntegrationTest {

	private AnimalitosRepository miRepositorio;
	@MockBean
	private EmailsService servicioEmailsDummy;
	private final AnimalitosService miServicio;
	private final Mapeador miMapeador;

	@Autowired
	public AnimalitosServiceIntegrationTest(AnimalitosService miServicio, Mapeador miMapeador, AnimalitosRepository miRepositorio) {
		this.miServicio = miServicio;
		this.miMapeador = miMapeador;
		this.miRepositorio=miRepositorio;
	}
	
	//
	// PRUEBAS para la INTEGRACION del servicio-animalitos con el repositorio
	//
	/*
	@BeforeAll
	public void cargarDatosParaPruebas() { // Juego con 1 único conjunto de datos
		listaDeAnimalitos().forEach( animalito -> miRepositorio.save(animalito));
	}*/
	@BeforeEach
	public void borrarTodoRastroDeAnimalitos() {
		miRepositorio.deleteAll();
	} // CHAPUZA CONSCIENTE: Mis test no son independientes... y haré esto para salir del paso! = RAPIDO
	
	@Test
	@DisplayName("Recuperar todos los animalitos")
	public void recuperarListaDeAnimalitos() throws Exception {
		// Principios fIRst de pruebas
		// Esta prueba es independiente de otras? NO 
		// Si la de abajo se ejecuta antes que ésta... ésta SI se ve afectada. NO FUNCIONA BIEN -> DEPENDIENTE
		// Dado
		//  - que tengo a pepe, pipo y pancho en el repositorio... y me guarlo los IDS que me da la BBDD
		List<Long> ids = listaDeAnimalitos().stream().map( animalito -> miRepositorio.save(animalito).getId()).collect(Collectors.toList());
		// Cuando
		//  - pido el listado de animalitos
		List<DatosAnimalito> animalitos = miServicio.recuperarAnimalitos();
		// Entonces
		// - debo recibir una lista con pepe, pipo y pancho (como DTOs)
		List<DatosAnimalito> animalidosQueDebeDevolver = listaDeAnimalitos().stream()
																			.map(miMapeador::animalito2DatosAnimalito)
																			.collect(Collectors.toList());
		// A los que inyecto el ID que me dió la BBDD
		for (int i =0; i< animalidosQueDebeDevolver.size();i++)
			animalidosQueDebeDevolver.get(i).setId(ids.get(i));
		
		// Y comparo
		for(int i = 0; i<animalitos.size();i++) 
			Assertions.assertEquals(animalidosQueDebeDevolver.get(i), animalitos.get(i));
	}
	
	@Test
	@DisplayName("Recuperar un animalito que existe")
	public void recuperarUnAnimalito() throws Exception{
		// Esta prueba es independiente de otras? SI 
		// Si la de arriba se ejecuta antes que ésta... ésta no se ve afectada. SIGUE FUNCIONANDO BIEN -> INDEPENDIENTE
		// Dado que hay un animalito en el repo, con un id que conozco
		Animalito elAnimalito = new Animalito();
		elAnimalito.setNombre("Pipo");
		elAnimalito.setColor("rojo");
		elAnimalito.setEdad(3);
		elAnimalito.setTipo("perro");
		// Y que el repositorio lo va a devolver cuando le pidan que lo guarde
		Animalito animalitoGuardado = miRepositorio.save(elAnimalito);
		// Cuando pido el animalito
		Optional<DatosAnimalito> datosAnimalitoExistenteOptional = miServicio.recuperarAnimalito(animalitoGuardado.getId());
		// Entonces, me lo devuelve
		DatosAnimalito datosAnimalitoExistente = datosAnimalitoExistenteOptional.get();
		// - Que me devuelva unos datos de animalito que incluyan los datos guays y el id
		Assertions.assertEquals(elAnimalito.getNombre(), datosAnimalitoExistente.getNombre());
		Assertions.assertEquals(elAnimalito.getColor(), datosAnimalitoExistente.getColor());
		Assertions.assertEquals(elAnimalito.getTipo(), datosAnimalitoExistente.getTipo());
		Assertions.assertEquals(elAnimalito.getEdad(), datosAnimalitoExistente.getEdad());
		Assertions.assertEquals(animalitoGuardado.getId(), datosAnimalitoExistente.getId());
	}
	
	
	// TODO: Faltan pruebas
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Animalito> listaDeAnimalitos(){
		return Arrays.asList(
				crearAnimalito("pipo","perro","rojo",3),
				crearAnimalito("pepe","gato","verde",2),
				crearAnimalito("lucrecia","elefante","azul",1)
				);
	}
	private Animalito crearAnimalito(String nombre, String tipo, String color, int edad) {
		Animalito animalito = new Animalito();
		animalito.setNombre(nombre);
		animalito.setEdad(edad);
		animalito.setColor(color);
		animalito.setTipo(tipo);
		return animalito;
	}
	

	//
	// PRUEBAS para la INTEGRACION del servicio-animalitos con el servicio-emails
	//
	
}
