package com.animalitos.rest;

import io.cucumber.java.en.Given;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class AnimalitosServiceRestTest {
	
	@Given("que tengo tengo un animalito")
	public void que_tengo_tengo_un_animalito() {
	}
	
	@Dado("ese animalito tiene por {string}: {string}")
	public void ese_animalito_tiene_por_campo(String campo, String valor) {
	}

	@Dado("ese animalito tiene por {string}: {int}")
	public void ese_animalito_tiene_por(String campo, Integer valor) {
	}
		
	@Dado("el animalito está guardado en la BBDD dentro de la tabla {string}")
	public void el_animalito_está_guardado_en_la_bbdd_dentro_de_la_tabla(String tabla) {
	}
	
	@Cuando("hacemos una petición por método {string} al servicio {string}")
	public void hacemos_una_petición_por_método_al_servicio(String metodo, String endPoint) {
		
	}
	
	@Cuando("añado en el path de la petición el {string}: {int}")
	public void añado_en_el_path_de_la_petición_el(String campo, Integer valor) {
	}
		
	@Entonces("el servicio nos devuelve un objeto JSON,")
	public void el_servicio_nos_devuelve_un_objeto_json() {
	}
	
	@Entonces("el objeto JSON tiene por {string}: {string}")
	public void el_objeto_json_tiene_por_campo(String campo, String valor) {
	}
		
	@Entonces("el objeto JSON tiene por {string}: {int}")
	public void el_objeto_json_tiene_por(String campo, Integer valor) {
	}
	
	@Dado("el animalito no está guardado en la BBDD dentro de la tabla {string}")
	public void el_animalito_no_está_guardado_en_la_bbdd_dentro_de_la_tabla(String tabla) {
	}
	
	@Dado("que tengo un objeto JSON,")
	public void que_tengo_un_objeto_json() {
	}
	
	@Dado("ese objeto JSON tiene por {string}: {string}")
	public void ese_objeto_json_tiene_por(String campo, String valor) {
	}
	
	@Dado("ese objeto JSON tiene por {string}: {int}")
	public void ese_objeto_json_tiene_por(String campo, Integer valor) {
	}
	
	@Cuando("le mandamos el objeto JSON en el cuerpo de la petición")
	public void le_mandamos_el_objeto_json_en_el_cuerpo_de_la_petición() {
	}
	
	@Entonces("el servicio nos devuelve un código de estado {string}")
	public void el_servicio_nos_devuelve_un_código_de_estado(String codigoEstado) {
	}
	
	@Entonces("el objeto JSON tiene un {string} de tipo {string}")
	public void el_objeto_json_tiene_un_de_tipo(String campo, String tipo) {
	}
	
	@Entonces("debe haberse creado una entrada en la BBDD dentro de la tabla {string}")
	public void debe_haberse_creado_una_entrada_en_la_bbdd_dentro_de_la_tabla(String tabla) {
	}
	
	@Entonces("con el {string} del animalito igual al {string} que nos ha devuelto el servicio")
	public void con_el_del_animalito_igual_al_que_nos_ha_devuelto_el_servicio(String campo, String valor) {
	}
	
	@Entonces("con el {string} del animalito {string}")
	public void con_el_del_animalito(String campo, String valor) {
	}
	
	@Entonces("con la {string} del animalito {int}")
	public void con_la_del_animalito(String campo, Integer valor) {
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
	
	@Entonces("no debe haberse creado una entrada en la BBDD dentro de la tabla {string}")
	public void no_debe_haberse_creado_una_entrada_en_la_bbdd_dentro_de_la_tabla(String tabla) {
	}

	
}
