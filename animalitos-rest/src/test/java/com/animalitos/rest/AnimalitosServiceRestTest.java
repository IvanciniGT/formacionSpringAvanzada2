package com.animalitos.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.animalitos.AplicacionDePrueba;

import com.animalitos.entity.Animalito;
import com.animalitos.repository.AnimalitosRepository;
import com.animalitos.service.EmailsService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;

// Esto es una colección de pruebas que debe ejecutar con JUnit
@Suite
// Ahora bien... Junit platform, debe delegar la ejecucion a Cucumber
@IncludeEngines("cucumber")
// Para decirle a Cucumber, el archivo de features que debe usar
// Lo hacemos a través del Junit platform
@SelectClasspathResource("features")
// Debe ser JUnit el que arranque este ficheroi
// En cucumber este fichero se llama un fichero de Steps (glue-code)
// Esta anotación es la que me permite que cucumber tenga inyección de dependencias desde spring
@CucumberContextConfiguration
// Arranca en segundo plano la app...
// PERO OJO!!! Qué tipo de app estoy arrancando? WEB... que corre en un tomcat... Que configure el Tomcat con un puerto al azar
// Esto me asegura que en otros entoros funcionará. Si ve que el puerto que intenta abrir está en uso... abrirá otro.
@SpringBootTest(classes = {AplicacionDePrueba.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
// Esto me crea un cliente HTTP apuntadoo a mi servidor en el puerto correspondiente
// Para que pueda usarlo de cara a hacer mis pruebas
// Realmente esto crea un cliente HTTP...
// Lo tengo accesible en mi clase ... en mi codigo? (1)
@AutoConfigureMockMvc
public class AnimalitosServiceRestTest {
	
	// DEPENDENCIAS
    private final AnimalitosRepository miRepositorio;
    private final MockMvc clienteHttp; // (1)
    // Esto es muy raro... en una prueba de Sistema... YA DEBERIA TENER UNA IMPLEMENTACION del emailsService
    // En el curso no la vamos a crear. En  un proyecto real YA DEBERIA ESTAR ... y esto no sería necesario
    @MockBean
    private EmailsService miEmailsServiceDummy;
	
	// Variables propias de mi clase
	private Animalito animalito;
	private ResultActions resultado;
	private JSONObject objetoJson;
	private MockHttpServletRequestBuilder constructorPeticion;
	private String metodoHttp;
	private String endPoint;
	private long numeroDeAnimalesGuardadosInicialmente;

 	
	public AnimalitosServiceRestTest(AnimalitosRepository miRepositorio, MockMvc clienteHttp) {
		this.miRepositorio=miRepositorio;
		this.clienteHttp=clienteHttp;
	}

	@Given("que tengo tengo un animalito")
	public void que_tengo_tengo_un_animalito() {
		animalito = new Animalito();
	}
	
	@Dado("ese animalito tiene por {string}: {string}")
	public void ese_animalito_tiene_por_campo(String campo, String valor) {
		switch(campo.toUpperCase()) {
			case "NOMBRE":
				animalito.setNombre(valor);
				break;
			case "COLOR":
				animalito.setColor(valor);
				break;
			case "TIPO":
				animalito.setTipo(valor);
				break;
		}
	}

	@Dado("ese animalito tiene por {string}: {int}")
	public void ese_animalito_tiene_por(String campo, Integer valor) {
		switch(campo.toUpperCase()) {
			case "ID":
				animalito.setId(valor.longValue());
				break;
			case "EDAD":
				animalito.setEdad(valor);
				break;
			case "PESO":
				animalito.setPeso(valor);
				break;
		}	
	}
		
	@Dado("el animalito está guardado en la BBDD dentro de la tabla {string}")
	public void el_animalito_está_guardado_en_la_bbdd_dentro_de_la_tabla(String tabla) {
		animalito = miRepositorio.save(animalito);
	}

	@Dado("el animalito no está guardado en la BBDD dentro de la tabla {string}")
	public void el_animalito_no_está_guardado_en_la_bbdd_dentro_de_la_tabla(String tabla) {
		// Entonces: Son los asserts
		// Assertions.assertFalse(miRepositorio.existsById(animalito.getId()));
		// Me estoy pasando por las narices: fIrst
		// El test no es INDEPENDIENTE de otros test. NO FIJA SUS CONDICIONES
		// Sino simplementes confía en que las condiciones se den.
		
		try{
			miRepositorio.deleteById(animalito.getId());
		}catch(Exception e) {}
		// Ahora el test es independiente de otros.. que otros han creado esos animalitos con ese id. Me los cargo.
	}
	
	@Dado("que tengo un objeto JSON,")
	public void que_tengo_un_objeto_json() {
		objetoJson = new JSONObject();
	}
	
	@Dado("ese objeto JSON tiene por {string}: {string}")
	public void ese_objeto_json_tiene_por(String campo, String valor) throws JSONException {
		objetoJson.put(campo, valor);	
	}
	
	@Dado("ese objeto JSON tiene por {string}: {int}")
	public void ese_objeto_json_tiene_por(String campo, Integer valor) throws JSONException {
		objetoJson.put(campo, valor);
	}
	
	
	
	
	
	
	
	@Cuando("hacemos una petición por método {string} al servicio {string}")
	public void hacemos_una_petición_por_método_al_servicio(String metodo, String endPoint) throws Exception {
		constructorPeticion = null;
		this.metodoHttp=metodo;
		this.endPoint=endPoint;
	}
	
	@Cuando("añado en el path de la petición el {string} que tiene el animalito")
	public void añado_en_el_path_de_la_petición_el(String campo) {
		endPoint+="/"+animalito.getId();
	}

	@Cuando("le mandamos el objeto JSON en el cuerpo de la petición")
	public void le_mandamos_el_objeto_json_en_el_cuerpo_de_la_petición() {
		crearPeticionHttp();
		constructorPeticion.contentType("application/json").content(objetoJson.toString());
	}
	
	private void crearPeticionHttp() {
		switch(metodoHttp.toUpperCase()) {
		case "GET":
			constructorPeticion = MockMvcRequestBuilders.get(endPoint);
			break;
		case "POST":
			constructorPeticion = MockMvcRequestBuilders.post(endPoint);
			break;
		}
	}
	
	@Entonces("el servicio nos devuelve un código de estado {string}")
	public void el_servicio_nos_devuelve_un_código_de_estado(String codigoEstado) throws Exception {
		if(constructorPeticion == null)crearPeticionHttp();
		resultado = clienteHttp.perform(constructorPeticion);
		switch(codigoEstado.toUpperCase()) {
			case "OK":
				resultado.andExpect(status().isOk());
				break;
			case "NOT_FOUND":
				resultado.andExpect(status().isNotFound());
				break;
			case "BAD_REQUEST":
				resultado.andExpect(status().isBadRequest());
				break;
		}
	}
		
	
	@Entonces("el servicio nos devuelve un objeto JSON,")
	public void el_servicio_nos_devuelve_un_objeto_json() throws Exception {
		resultado.andExpect(content().contentType("application/json"));
	}
	
	@Entonces("el objeto JSON tiene por {string}: {string}")
	public void el_objeto_json_tiene_por_campo(String campo, String valor) throws Exception {
		resultado.andExpect(jsonPath("$."+campo).value(valor));
	}
		
	@Entonces("el objeto JSON tiene por {string}: {int}")
	public void el_objeto_json_tiene_por(String campo, Integer valor) throws Exception {
		resultado.andExpect(jsonPath("$."+campo).value(valor));
	}
	@Entonces("el objeto JSON tiene por {string} el {string} que tiene el animalito")
	public void el_objeto_json_tiene_por(String campo, String campo2) throws Exception {
																//campo2 = "id"
		resultado.andExpect(jsonPath("$."+campo).value(animalito.getId()));
	}
	
	@Entonces("el objeto JSON tiene un {string} de tipo {string}")
	public void el_objeto_json_tiene_un_de_tipo(String campo, String tipo) throws Exception {
		switch(tipo.toUpperCase()){
			case "NUMERICO":
				resultado.andExpect(jsonPath("$."+campo).isNumber());
				break;		
			case "BOOLEANO":
				resultado.andExpect(jsonPath("$."+campo).isBoolean());
				break;
			case "TEXTO":
				resultado.andExpect(jsonPath("$."+campo).isString());
				break;
		}
	}
	
	
	@Before // Se ejecuta antes de cada escenario
	// @After
	// @BeforeStep
	// @AfterStep
	public void contarAnimalesExistentesEnBBDD() {
		numeroDeAnimalesGuardadosInicialmente = numeroActualDeAnimales();
	}
	
	private long numeroActualDeAnimales() {
		return miRepositorio.count();
	}
	
	@Entonces("debe haberse creado una entrada en la BBDD dentro de la tabla {string}")
	public void debe_haberse_creado_una_entrada_en_la_bbdd_dentro_de_la_tabla(String tabla) {
		long numeroDeAnimalesGuardadosAlFinal = numeroActualDeAnimales();
		Assertions.assertEquals(numeroDeAnimalesGuardadosInicialmente+1, numeroDeAnimalesGuardadosAlFinal);
	}

	@Entonces("no debe haberse creado una entrada en la BBDD dentro de la tabla {string}")
	public void no_debe_haberse_creado_una_entrada_en_la_bbdd_dentro_de_la_tabla(String tabla) {
		long numeroDeAnimalesGuardadosAlFinal = numeroActualDeAnimales();
		Assertions.assertEquals(numeroDeAnimalesGuardadosInicialmente, numeroDeAnimalesGuardadosAlFinal);
	}

	private Animalito animalitoEnBBDD;
	
	@Entonces("con el {string} del animalito igual al {string} que nos ha devuelto el servicio")
	public void con_el_del_animalito_igual_al_que_nos_ha_devuelto_el_servicio(String campo, String campo2) throws Exception, JSONException {
		Long id = new JSONObject(resultado.andReturn().getResponse().getContentAsString()).getLong("id");
		animalitoEnBBDD = miRepositorio.findById(id).get();
	}	
	
	@Entonces("con el {string} del animalito {string}")
	public void con_el_del_animalito(String campo, String valor) {
		switch(campo.toUpperCase()) {
			case "NOMBRE":
				Assertions.assertEquals(valor, animalitoEnBBDD.getNombre());
				break;
			case "COLOR":
				Assertions.assertEquals(valor, animalitoEnBBDD.getColor());
				break;
			case "TIPO":
				Assertions.assertEquals(valor, animalitoEnBBDD.getTipo());
				break;
		}
	}

	@Entonces("con la {string} del animalito {int}")
	public void con_la_del_animalito(String campo, Integer valor) {
		switch(campo.toUpperCase()) {
			case "ID":
				Assertions.assertEquals(valor, animalitoEnBBDD.getId());
				break;
			case "EDAD":
				Assertions.assertEquals(valor, animalitoEnBBDD.getEdad());
				break;
			case "PESO":
				Assertions.assertEquals(valor, animalitoEnBBDD.getPeso());
				break;
		}	
	}
	
	

	
	
	
	
	
	
	
	@Entonces("tiene que mandar un email a {string}")
	public void tiene_que_mandar_un_email_a(String destinatario) {
	}
	
	@Entonces("en el asunto tiene que poner {string}")
	public void en_el_asunto_tiene_que_poner(String asunto) {
	}
	
	@Entonces("en el cuerpo del email tiene que aparecer el texto {string}")
	public void en_el_cuerpo_del_email_tiene_que_aparecer_el_texto(String cuerpo) {
	}
	
}
