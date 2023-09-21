package com.animalitos.service.testdoubles;

import com.animalitos.service.EmailsService;

//@Service
// Para qué serviría un dummy?
// Para hacer más rápidas otras pruebas... donde no quiera que se estén enviando correos
public class ServicioEmailsDummy implements EmailsService{

	@Override
	public void enviarEmail(String destinatario, String asunto, String cuerpo) {
	
	}

}
