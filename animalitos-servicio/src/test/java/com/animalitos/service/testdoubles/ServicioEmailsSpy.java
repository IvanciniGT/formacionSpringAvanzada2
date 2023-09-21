package com.animalitos.service.testdoubles;

import org.springframework.stereotype.Service;

import com.animalitos.service.EmailsService;

import lombok.Getter;

@Service
// Para qué serviría un dummy?
// Para hacer más rápidas otras pruebas... donde no quiera que se estén enviando correos
@Getter
public class ServicioEmailsSpy implements EmailsService{

	private String destinatario;
	private String asunto;
	private String cuerpo;
	
	@Override
	public void enviarEmail(String destinatario, String asunto, String cuerpo) {
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
	}

}
