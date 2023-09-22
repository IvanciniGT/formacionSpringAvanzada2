package com.animalitos.entity;

import javax.persistence.Column;
import javax.persistence.Entity; // Desde Spring 3, en lugar de javax, sería jakarta
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="animalitos")
public class Animalito {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(updatable = false, length = 50, nullable = false)
	private String nombre;

	@Column(updatable = false, length = 50, nullable = false)
	private String tipo;
	
	@Column(updatable = false, length = 50, nullable = false)
	private String color;

	@Column(updatable = true, nullable = false)
	private Integer edad;

	@Column(updatable = true, nullable = false)
	private Integer peso;
	
}
