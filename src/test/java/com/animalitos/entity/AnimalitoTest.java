package com.animalitos.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.animalitos.repository.AnimalitosRepository;

// Las pruebas las vamos a hacer con JUnit 5 / Junit 4

// Sabeis quien va a crear una instancia de esta clase? JUNIT
// Tengo que indicarle a JUNIT que debe delegar el constructor a Spring
@ExtendWith(SpringExtension.class)
// Realmente hace que Junit pueda pedirle datos a Spring
// JUnit al crear una instancia de una clase, en ocasiones también le puede dar parámetros: Parameterized Test
// Esto a JUnit le genera una ambigüedad, no sabe si él tiene que dar el mepositorio o es Spring.
// Este es un caso muy especial... raro de narices en otros sitios del código... pero muy habitual en pruebas

// Me falta una BBDD
// Me falta montar una aplicación
// Me falta configurar en la aplicación la BBDD de pruebas
// Me falta arrancar mi aplicación... que Spring debe crearla primero, que ni existe...
// Para que pueda usar su repositorio (el de mi aplicación)
// 1 dependencia en el pom
// 1 linea de codigo
// 1 anotacion
public class AnimalitoTest {

	private final AnimalitosRepository miRepositorio;
	
	@Autowired // Le explica a JUnit que los datos del constructor debve proveerlos Spring
	public AnimalitoTest(AnimalitosRepository miRepositorio) { // Inyección de dependencias
		this.miRepositorio = miRepositorio;
	}
	
	@Test
	public void altaDeAnimalitoConDatosOK() {
		// Dado
		Animalito animalito = new Animalito();
		animalito.setNombre("Pipo");
		animalito.setEdad(2);
		animalito.setColor("verde");
		animalito.setTipo("Perro");
		// Cuando
		Animalito animalitoGuardado = miRepositorio.save(animalito);
		// Entonces <- Test
		Assertions.assertNotNull(animalitoGuardado.getId());
		Assertions.assertTrue(animalitoGuardado.getId() >= 0);
		Assertions.assertEquals(animalito.getNombre(), animalitoGuardado.getNombre());
		Assertions.assertEquals(animalito.getEdad(),   animalitoGuardado.getEdad());
		Assertions.assertEquals(animalito.getTipo(),   animalitoGuardado.getTipo());
		Assertions.assertEquals(animalito.getColor(),  animalitoGuardado.getColor());	
	}
	
	
}

