package com.animalitos.rest.v2.dto;

import lombok.Data;

@Data // Mete @Getter @Setter @Equals...
public class DatosNuevoAnimalitoRest {
	private String name;
	private String type;
	private String color;
	private Integer age;
	private Integer weight;
}

// Esto debe tener una documentación: Swagger / OpenAPI (swagger v3)
// Cómo se genera?