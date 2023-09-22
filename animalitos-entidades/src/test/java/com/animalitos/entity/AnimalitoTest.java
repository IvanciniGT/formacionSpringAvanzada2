package com.animalitos.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.animalitos.AplicacionDePrueba;
import com.animalitos.repository.AnimalitosRepository;


// Las pruebas las vamos a hacer con JUnit 5 / Junit 4

// Sabeis quien va a crear una instancia de esta clase? JUNIT
// Tengo que indicarle a JUNIT que debe delegar el constructor a Spring
@ExtendWith(SpringExtension.class)
// Realmente hace que Junit pueda pedirle datos a Spring
// JUnit al crear una instancia de una clase, en ocasiones también le puede dar parámetros: Parameterized Test
// Esto a JUnit le genera una ambigüedad, no sabe si él tiene que dar el mepositorio o es Spring.
// Este es un caso muy especial... raro de narices en otros sitios del código... pero muy habitual en pruebas

// Me falta una BBDD √
// Me falta montar una aplicación √
// Me falta configurar en la aplicación la BBDD de pruebas √
// Me falta arrancar mi aplicación... que Spring debe crearla primero, que ni existe...
// Para que pueda usar su repositorio (el de mi aplicación)
// 1 dependencia en el pom
// 1 linea de codigo
// 1 anotacion
@SpringBootTest(classes = {AplicacionDePrueba.class}) // Arranca en segundo plano la app
public class AnimalitoTest {

	private final AnimalitosRepository miRepositorio;
	
	@Autowired // Le explica a JUnit que los datos del constructor debve proveerlos Spring
	public AnimalitoTest(AnimalitosRepository miRepositorio) { // Inyección de dependencias
		this.miRepositorio = miRepositorio;
	}

	@ParameterizedTest
	//@CsvSource({"Pipo,verde,lagarto,2"})
	@DisplayName("Alta de un Animalito con datos sin peso")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoConDatosSinPeso(String nombre, String color, String tipo, int edad) {
		// Dado
		Animalito animalito = crearAnimalito(nombre, color, tipo, edad);
		// Cuando
		//Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito));
		// Entonces <- Test
		/*
		Assertions.assertNotNull(animalitoGuardado.getId());
		Assertions.assertTrue(animalitoGuardado.getId() >= 0);
		Assertions.assertEquals(animalito.getNombre(), animalitoGuardado.getNombre());
		Assertions.assertEquals(animalito.getEdad(),   animalitoGuardado.getEdad());
		Assertions.assertEquals(animalito.getTipo(),   animalitoGuardado.getTipo());
		Assertions.assertEquals(animalito.getColor(),  animalitoGuardado.getColor());	
		*/
	}

	@ParameterizedTest
	//@CsvSource({"Pipo,verde,lagarto,2"})
	@DisplayName("Alta de un Animalito con datos con peso")
	@CsvFileSource(resources = "/datosAnimalitosConPeso.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoConDatosConpeso(String nombre, String color, String tipo, int edad, int peso) {
		// Dado
		Animalito animalito = crearAnimalito(nombre, color, tipo, edad, peso);
		// Cuando
		Animalito animalitoGuardado = miRepositorio.save(animalito);
		// Entonces <- Test
		Assertions.assertNotNull(animalitoGuardado.getId());
		Assertions.assertTrue(animalitoGuardado.getId() >= 0);
		Assertions.assertEquals(animalito.getNombre(), animalitoGuardado.getNombre());
		Assertions.assertEquals(animalito.getEdad(),   animalitoGuardado.getEdad());
		Assertions.assertEquals(animalito.getTipo(),   animalitoGuardado.getTipo());
		Assertions.assertEquals(animalito.getColor(),  animalitoGuardado.getColor());	
		Assertions.assertEquals(animalito.getPeso(),  animalitoGuardado.getPeso());	
	}
	
	@ParameterizedTest
	@DisplayName("Alta de un Animalito sin nombre")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoSinNombre(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(null, color, tipo, edad);
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito) );
	}
	
	@ParameterizedTest
	@DisplayName("Alta de un Animalito sin color")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoSinColor(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(nombre, null, tipo, edad);
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito) );
	}
	
	@ParameterizedTest
	@DisplayName("Alta de un Animalito sin tipo")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoSinTipo(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(nombre, color, null, edad);
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito) );
	}

	@ParameterizedTest
	@DisplayName("Alta de un Animalito sin edad")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaDeAnimalitoSinEdad(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(nombre, color, tipo, null);
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito) );
	}
/*
	@ParameterizedTest
	@DisplayName("Editar el nombre de un Animalito")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void editarNombreAnimalito(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(nombre, color, tipo, edad);
		Animalito animalitoGuardado = miRepositorio.save(animalito) ; // Doy por hecho que esto funciona... porque si no altaDeAnimalitoConDatosOK debería haber fallado
		animalitoGuardado.setNombre(nombre+"Editado");
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalitoGuardado) );
	}
	// TODO: Falta el color y tipo
*/
	@ParameterizedTest
	@DisplayName("Editar la edad de un Animalito")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void editarEdadAnimalito(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito(nombre, color, tipo, edad);
		Animalito animalitoGuardado = miRepositorio.save(animalito) ; // Doy por hecho que esto funciona... porque si no altaDeAnimalitoConDatosOK debería haber fallado
		animalitoGuardado.setEdad(edad+5);
		Animalito animalitoEditado = miRepositorio.save(animalito);
		Assertions.assertEquals(edad+5, animalitoEditado.getEdad());
	}
	
	@ParameterizedTest
	@DisplayName("Alta de un anilamito con nombre Extraordinario")
	@CsvFileSource(resources = "/datosAnimalitos.csv", numLinesToSkip = 1)
	public void altaAnimalitoConNombreExtraordinario(String nombre, String color, String tipo, int edad) {
		Animalito animalito = crearAnimalito("En un lugar de la mancha de cuyo nombre no quiero acordarme...", color, tipo, edad);
		Assertions.assertThrows(Exception.class, () -> miRepositorio.save(animalito) );
	}
	// TODO: Falta el color y tipo

	private Animalito crearAnimalito(String nombre, String color, String tipo, Integer edad) {
		Animalito animalito = new Animalito();
		animalito.setNombre(nombre);
		animalito.setEdad(edad);
		animalito.setColor(color);
		animalito.setTipo(tipo);
		// RUINA... solo por ir rápido en el curso
		animalito.setPeso(5);
		return animalito;
	}
	private Animalito crearAnimalito(String nombre, String color, String tipo, Integer edad, Integer peso) {
		Animalito animalito =  crearAnimalito(nombre,  color,  tipo,  edad);
		animalito.setPeso(peso);
		return animalito;
	}
	
}

