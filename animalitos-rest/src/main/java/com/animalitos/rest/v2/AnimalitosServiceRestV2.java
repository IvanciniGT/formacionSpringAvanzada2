package com.animalitos.rest.v2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalitos.rest.v2.dto.DatosAnimalitoRest;
import com.animalitos.rest.v2.dto.DatosNuevoAnimalitoRest;
import com.animalitos.rest.v2.mapper.Mapeador;
import com.animalitos.service.AnimalitosService;
import com.animalitos.service.dto.DatosAnimalito;

@RestController
@RequestMapping("/api/v2")
public class AnimalitosServiceRestV2 {

	private AnimalitosService servicio ;
	private Mapeador miMapeador;
	
	public AnimalitosServiceRestV2(AnimalitosService servicio, Mapeador miMapeador) {
		this.servicio = servicio;
		this.miMapeador = miMapeador;
	}
	
	// ResponseEntity es qel equivalente en jee / servlets a un HttpServletResponse
	@GetMapping("/animalitos/{id}")
	public ResponseEntity<DatosAnimalitoRest> recuperarAnimalito(@PathVariable("id") long id) {
		try {
			Optional<DatosAnimalito> animalito = servicio.recuperarAnimalito(id);
			if(!animalito.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<>(miMapeador.datosAnimalito2DatosAnimalitoRest(animalito.get()) ,HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/animalitos")
	public ResponseEntity<List<DatosAnimalitoRest>> recuperarAnimalitos() {
		try {
			return new ResponseEntity<>(
					servicio.recuperarAnimalitos()
						.stream()
						.map(miMapeador::datosAnimalito2DatosAnimalitoRest)
						.collect(Collectors.toList())
					,HttpStatus.OK
			);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/animalitos")											 // Esto es un JSON que me mandan en el cuerpo del request http
	public ResponseEntity<DatosAnimalitoRest> altaAnimalito(@RequestBody DatosNuevoAnimalitoRest datosNuevoAnimalito) {
		try {
			DatosAnimalito animalito = servicio.altaAnimalito( miMapeador.datosAnimalitoRest2DatosAnimalito(datosNuevoAnimalito) );
				return new ResponseEntity<>(miMapeador.datosAnimalito2DatosAnimalitoRest(animalito) ,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		//}catch(Exception e) {
		//	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
