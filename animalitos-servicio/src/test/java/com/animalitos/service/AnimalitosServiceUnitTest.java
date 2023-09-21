package com.animalitos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.animalitos.AplicacionDePrueba;
import com.animalitos.entity.Animalito;
import com.animalitos.repository.AnimalitosRepository;
import com.animalitos.service.dto.DatosAnimalito;
import com.animalitos.service.dto.DatosNuevoAnimalito;
import com.animalitos.service.mappers.Mapeador;
import com.animalitos.service.testdoubles.ServicioEmailsSpy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AplicacionDePrueba.class}) // Arranca en segundo plano la app
public class AnimalitosServiceUnitTest {

	@MockBean
	// Por un alado, nos estamos creando un Stub (Eso lo hace mockito)
	// Mockito va a crear una CLASE que implemente la interfaz: AnimalitosRepository, devolviendo NADA
	// NADA: significa lo más básico que cada función pueda devolver:
	// - Si tengo una función que devuelve un Animalito:  null
	// - Si tengo una función findAll que devuelve un List<Animalito>:  null
	// - Si tengo una función que devuelve un número (int): 0
	// Ese Stub lo va a usar spring (Bean) cuando alguien pida un AnimalitosRepository
	private AnimalitosRepository miRepositorioMock;
	//@MockBean
	//private EmailsService servicioEmailsDummy;
	private final AnimalitosService miServicio;
	private final Mapeador miMapeador;
	private final EmailsService servicioEmails;
	@Captor
	private ArgumentCaptor<Animalito> animalitoQueRecibeElRepositorio;
	

	@Autowired
	public AnimalitosServiceUnitTest(AnimalitosService miServicio, Mapeador miMapeador, EmailsService servicioEmails) {
		this.miServicio = miServicio;
		this.miMapeador = miMapeador;
		this.servicioEmails=servicioEmails;
	}
	
	@Test
	@DisplayName("Recuperar todos los animalitos")
	public void recuperarListaDeAnimalitos() throws Exception {
		// Dado
		//  - que tengo a pepe, pipo y pancho en el repositorio
		// Configurar el Stub para falsear la respuesta de la llamada a la función findAll
		when(miRepositorioMock.findAll()).thenReturn(listaDeAnimalitos()); // Sobreescribo el comportamiento de la función
		// Cuando
		//  - pido el listado de animalitos
		List<DatosAnimalito> animalitos = miServicio.recuperarAnimalitos();
		// Entonces
		// - debo recibir una lista con pepe, pipo y pancho
		List<DatosAnimalito> animalidosQueDebeDevolver = listaDeAnimalitos().stream()
																			.map(miMapeador::animalito2DatosAnimalito)
																			.collect(Collectors.toList());
		for(int i = 0; i<animalitos.size();i++) 
			Assertions.assertEquals(animalidosQueDebeDevolver.get(i), animalitos.get(i));
	}
	
	@Test
	@DisplayName("Recuperar un animalito que existe")
	public void recuperarUnAnimalito() throws Exception{
		// Dado que hay un animalito en el repo, con un id que conozco
		Animalito elAnimalito = new Animalito();
		elAnimalito.setNombre("Pipo");
		elAnimalito.setColor("rojo");
		elAnimalito.setEdad(3);
		elAnimalito.setId(33L);
		elAnimalito.setTipo("perro");
		// Y que el repositorio lo va a devolver cuando le pidan que lo guarde
		when(miRepositorioMock.findById(elAnimalito.getId())).thenReturn(Optional.of(elAnimalito)); // Sobreescribo el comportamiento de la función
		// Cuando pido el animalito
		Optional<DatosAnimalito> datosAnimalitoExistenteOptional = miServicio.recuperarAnimalito(elAnimalito.getId());
		// Entonces, me lo devuelve
		DatosAnimalito datosAnimalitoExistente = datosAnimalitoExistenteOptional.get();
		// - Que me devuelva unos datos de animalito que incluyan los datos guays y el id
		Assertions.assertEquals(elAnimalito.getNombre(), datosAnimalitoExistente.getNombre());
		Assertions.assertEquals(elAnimalito.getColor(), datosAnimalitoExistente.getColor());
		Assertions.assertEquals(elAnimalito.getTipo(), datosAnimalitoExistente.getTipo());
		Assertions.assertEquals(elAnimalito.getEdad(), datosAnimalitoExistente.getEdad());
		Assertions.assertEquals(elAnimalito.getId(), datosAnimalitoExistente.getId());
	}
	
	@Test
	@DisplayName("Dar de alta un animalito con datos guays")
	public void altaAnimalito() throws Exception{
		// Dado que
		// - tengo unos DatosNuevoAnimalito
		Animalito elAnimalito = new Animalito();
		elAnimalito.setNombre("Pipo");
		elAnimalito.setColor("rojo");
		elAnimalito.setEdad(3);
		elAnimalito.setId(33L);
		elAnimalito.setTipo("perro");
		
		DatosNuevoAnimalito nuevo = miMapeador.animalito2DatosNuevoAnimalito(elAnimalito); // Sin ID
		
		// Y que el repositorio lo va a devolver cuando le pidan que lo guarde
		when(miRepositorioMock.save(any(Animalito.class))).thenReturn(elAnimalito); // Sobreescribo el comportamiento de la función

		// Cuando
		// - Pido al Servicio que guarde los datos
		DatosAnimalito datosAnimalitoGuardado = miServicio.altaAnimalito(nuevo);
		
		// Entonces
		// - Que se haya solicitado que se guarden en el repositorio los datos guays
		// Compruebo lo que el Servicio mandó al REPO
		verify(miRepositorioMock).save(animalitoQueRecibeElRepositorio.capture());
		Assertions.assertEquals(elAnimalito.getNombre(), animalitoQueRecibeElRepositorio.getValue().getNombre());
		Assertions.assertEquals(elAnimalito.getColor(), animalitoQueRecibeElRepositorio.getValue().getColor());
		Assertions.assertEquals(elAnimalito.getTipo(), animalitoQueRecibeElRepositorio.getValue().getTipo());
		Assertions.assertEquals(elAnimalito.getEdad(), animalitoQueRecibeElRepositorio.getValue().getEdad());
		Assertions.assertNull(animalitoQueRecibeElRepositorio.getValue().getId());
		
		// - Que me devuelva unos datos de animalito que incluyan los datos guays y el id
		Assertions.assertEquals(elAnimalito.getNombre(), datosAnimalitoGuardado.getNombre());
		Assertions.assertEquals(elAnimalito.getColor(), datosAnimalitoGuardado.getColor());
		Assertions.assertEquals(elAnimalito.getTipo(), datosAnimalitoGuardado.getTipo());
		Assertions.assertEquals(elAnimalito.getEdad(), datosAnimalitoGuardado.getEdad());
		Assertions.assertEquals(elAnimalito.getId(), datosAnimalitoGuardado.getId());
		// - Que se haya solicitado el envío del email
		// Compruebo lo que el Servicio mandó al servicio de Emails
		// TODO: Pasar esto a Mockito
		Assertions.assertEquals("altas@animalitosfermin.com", ((ServicioEmailsSpy)servicioEmails).getDestinatario());
		Assertions.assertEquals("Nuevo animalito disponible", ((ServicioEmailsSpy)servicioEmails).getAsunto());
		Assertions.assertTrue(((ServicioEmailsSpy)servicioEmails).getCuerpo().contains(nuevo.getNombre()));
	}
	
	// TODO: Probar a intntar dar de alta un animalito con datos mal... y ver que explota
	// TODO: Probar a recuperar un animalito que nu existe y ver que explota
	
	// CUIDADO QUE TE CAGAS !!!! QUE ESTOY DANDO POR SENTADO AL ESCRIBIR ESTA FUNCION?
	// Que el mapeador ... FUNCIONA BIEN !
	// Lo sabemos? Que no tengo npi
	// Por tanto, Debo meter mis pruebas unitarias al mapeador
	
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
	
	
}
