package com.animalitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalitos.entity.Animalito;

//import java.util.List;

public interface AnimalitosRepository extends JpaRepository<Animalito, Long>{
	// Spring se va a encargar de crear una clase que implemente los métodos aquí definidos
	// Yo no he definido métodos propios... que podría... pero de JpaRepository heredo un huevo:
	// save(Animalito)
	// delete(id)
	// delete(Animalito)
	
	// Yo podría meter mis propios métodos:
	/*
		List<Animalito> findByColor(String color); // Spring va a implentar está función
		// SELECT * FROM animalitos WHERE color = "color"... el truco está en la palabra findBy
		List<Animalito> findByEdadIsNotNull(); // Spring va a implentar está función
		List<Animalito> findByNombreStartingWith(String nombre); // Spring va a implentar está función
		List<Animalito> findByNombreEndingWith(String nombre); // Spring va a implentar está función
		List<Animalito> findByNombreContainingWith(String nombre); // Spring va a implentar está función
		List<Animalito> findByEdadLessThan(int edad); // Spring va a implentar está función
		List<Animalito> findByEdadGreaterThanEqual(int edad); // Spring va a implentar está función
		List<Animalito> findByTipoAndColor(String tipo, String color); // Spring va a implentar está función
	 */
}
