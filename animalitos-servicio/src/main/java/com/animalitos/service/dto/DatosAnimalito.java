package com.animalitos.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// Al implementar esos métodos ten en cuenta las propiedades que heredamos
public class DatosAnimalito extends DatosNuevoAnimalito{
	private Long id;
}
