package com.animalitos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aplicacion {

    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args); // Inversión de control
        // Yo no escribo el flujo de mi aplicación. Le dejo a Spring hacerlo.
    }

	
}
