package com.animalitos.rest.v1.dto;

import lombok.Data;

@Data // Mete @Getter @Setter @Equals...
public class DatosNuevoAnimalitoRest {
	private String nombre;
	private String tipo;
	private String color;
	private Integer edad;
	private Integer peso = 0;
}

// Esto debe tener una documentación: Swagger / OpenAPI (swagger v3)
// Cómo se genera?