Hasta ahora hemos ido a remolque del desarrollo

Es decir... lo primero que he hecho ha sido el desarrollo.
Después he definido las pruebas
Para acabar ejecutando las pruebas

Requisitos -> Defino pruebas -> Escribo código y voy ejecutando pruebas... hasta que se pongan en verde!

	TEST-FIRST

# TDD

	TEST-FIRST
	+ Refactorización en cada iteración de pruebas

El escribir primero las pruebas, nos va a ayudar a definir el API que quiero... y validarlo.

---

JUnit / Cucumber / Spring

Quién crea la instancia de esa clase ahora? CUCUMBER
Con lo que el ExtendsWith... Una clase de cucumber que se integre con Spring

Quién queremos que ejecute los test? JUNIT  √
Por qué?
- Porque maven llama a Surefire, que llama a JUNIT.. no a Cucumber
- Junit me genera un informe de pruebas con un forma ACEPTADO POR LA INDUSTRIA 


Junit -> Cucumber -> Spring
En JUnit5 -> Platform -> Engine
Platform convierte a JUnit en un integrador de pruebas
Permite que JUnit llame a otras herramnientas de pruebas

En versión 4 de JUnit (@RunsWith)

JUNIT <- PLATFORM  < CUCUBER-PLATFORM >    CUCUMBER <> SPRING

---

# JSONPath

Es un equivalente a XPATH (Estandar del W3C para identificar / Extraer datos de un fichero XML).

Si bien... JSONPath no es un ESTANDAR... y más o menos todos los moteres de JSONPath trabajan con la misma sintaxis... aunque hay ligeras variaciones.













