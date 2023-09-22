# Validaciones


Formulario que me permite dar de alta un usuario... con un DNI 	|
   VVVV															| FRONTAL
JS**																|  Como cortesía de la Lógica de negocio y del dato
   VVVV
Controlador REST													|
   VVVV															| BACKEND
Servicio*****													| Logica de negocio / Como cortesía con la BBDD podré validar también el DNI
   VVVV															|						Lógica del dato
Persistencia  													|
   VVVV															|
BBDD	*															|
  v
  Los datos es lo más sagrado que tiene una empresa
 
 Yo no se si tirarán unas queries por ahi que metan datos.. y que no los estén validando. NO VOY A PERMITIRLO
 Esa BBDD quizás se usará para otros menesteres. Informes..... 
 O se usará desde otra app en el futuro y la vuestra va a la basura.

Validar DNI: Formato (De 1 a 8 numeros) + 1 letra
             La letra corresponde al número

             
LA BBDD es el garante del DATO. Y no debe permitir NUNCA bajo ningún pretexto que se meta un DNI inválido.


Cuidado que hoy en día hemos caído en la trampa de LA FAMOSA LEY DEL PENDULO.
Hace 40 años ... 25 años.... toda la lógica se metía en BBDD: PL/SQL = RUINA ENORME !!!!!
Pasamos al  otro extremo: QUE NO SE META LOGICA EN BBDD, la lógica debe ir en la app = RUINA también !!!
Y ésto ha sido especialmente inducido por los frameworks tipo HIBERNATE : PELIGROSOS DE COJONES
- Hay muchos tipos de lógica:
	- Logica de negocios
	- Logica de datos

Tengo que validar que el animalito si es un perro sea menor de 6 meses. < REGLA DE NEGOCIO (Yo, empresa, no vendo animalitos de más de 3 meses)
- Puede un animalito ser mayor de 6 meses? Un animalito puede tener 6 meses o 3 años... o 15 años 
- Puede un animalito ser menos de 0 meses? NO... en ningún caso: < REGLA DE NEGOCIO? NO . Eso es Lógica del dato.
	^ Y esto es una validación a nivel del dato

	
Tengo montado un servicio para el alta de animalitos

SERVICIO WEB = SERVICIO = RUINA ! GORDA ! GRANDE Y OILOROSA !
SERVICIO WEB = CONTROLADOR = ES ¡¡¡UNA!!! FORMA de exponer mi servicio (que es donde reside la lógica de negocio)


Peticiones										Peticiones
  vvv												vvvv
						Controlador REST 			Controlador SOAP (Responsabilidad: EXPONER UN SERVICIO)
  								vvv				vvvv
						Servicio de alta de animalitos (Responsanilidad: LOGICA DE NEGOCIO)(Validación de la edad<6)
									  vvv
									 PERSISTENCIA


SOLID: S: Principio de responsabilidad UNICA


Distribuyo ya mi app o aún no? VAya gilipollez de pregunta Ivan... qué aplicación? Tenemos app?
Hemos creado una app?

# Que proyectos debería tener en el backend de Animalitos Fermin?
(NOTA: para la funcionaldiad que tenemos desarrollado)

Animalitos-Entidades √

	Animalitos-Servicio-API √     

Animalitos-Servicio-IMPL √
Animalitos-Servicio-REST-V1 √ 


# Maven goals

compile
test-compile
	test		---> Pruebas unitarias... que al ser componentes aislados, se deben poder ejecutar en cualquier entorno
	integration-test # Los test de integración pueden requerir infra (BBDD real, Kafka comunicaciones entre miscroservicios)
					 # Me voy a comunicar con un moicroservicio que me va a dasr unos datos que necesito
					 # Renimiento: JMETER
					 # INTERFAZ: SELECIUM
verify
package
install
clean

Cuando ésto lo procesa un JENKINS

