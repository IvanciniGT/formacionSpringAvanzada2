package com.animalitos.service.dto;

import lombok.Data;

@Data // Mete @Getter @Setter @Equals...
public class DatosNuevoAnimalito {
	private String nombre;
	private String tipo;
	private String color;
	private Integer edad;
}
