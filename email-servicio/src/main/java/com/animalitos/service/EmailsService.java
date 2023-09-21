package com.animalitos.service;

public interface EmailsService {

	void enviarEmail(String destinatario, String asunto, String cuerpo);
	
}
