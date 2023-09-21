package com.animalitos.rest.v1.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// Al implementar esos métodos ten en cuenta las propiedades que heredamos
public class DatosAnimalitoRest extends DatosNuevoAnimalitoRest{
	private Long id;
}
