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


---



# BackEnd de Animalitos Fermín

                                        Animalitos-Entidades
                                         ^                                      Emails-Service-Impl
                                         ^                                        v
    [Animalitos-Servicio-API]            ^                                        v
      ^                 ^                ^                                        v
      ^               Animalitos-Servicio-Impl >>>>>>>>>>>>>>>>>>>> [Emails-Service-API]
      ^
      ^
    Animalitos-Controller-Rest-V1

    Respect!!!!! El principio de Inversión de dependencias

    Pero en algún sitio tengo que juntar todo: Aplicación
        Animalitos-Servicio-API -> Animalitos-Servicio-Impl
        Emails-Service-API -> Emails-Service-Impl

Según una arquitectura limpia, en capa de persistencia, no debería atarme a ningún framework.
    Genial tio bob... paso de tu culo!... en capa de persistencia me ato: DECISION CONSCIENTE... con sus consecuencias

---

# Cambios al proyecto. Su vida.. nuestro día a día

## Cambio 1

Entidad Animalito, que gestionamos mediante un servicio
Y que exponemos mediante rest

El animalito tiene:
- nombre
- tipo
- color
- edad
+ peso(kg) Opcional

De entrada... lo primero que deberíamos preguntarnos: Qué tipo de cambio tengo?
- Breaking change
- No breaking change *** 

Qué incremento hacemos en la versión? MINOR (+ funcionalidad (non breaking change)) 

Segunda pregunta... A qué impacta? 
- Entidad/Persistencia? SI
- Servicio? SI -> DTO
- Controller? SI -> DTO

## Cambio 2a

- El peso Obligatorio

De entrada... lo primero que deberíamos preguntarnos: Qué tipo de cambio tengo?
- Breaking change ***
- No breaking change

Qué incremento hacemos en la versión? MAYOR (breaking change)

Segunda pregunta... A qué impacta? 
- Entidad/Persistencia? SI
- Servicio? NO
- Controller? SI -> DTO


A partir de este momento, tengo 2 versiones que van a evolucionar en paralelo
Ahora mismo, las 2 son medianamente compatibles
Qué pasa si el día de mañana quiero que mis campos tengan nombre en ingles?
- En qué versión lo hago? v 1.1.0 RETROCOMPATIBILIDAD  controladorv1.jar < main
                                  v 1.1.1 
                          v 2.0.0 *                    controladorv2.jar < main

## Cambio 2b

Los campos ahora se llaman en ingles

Las APIs las consumen internacionalmente

Tipo de cambio?
- Breaking change ***

Qué incremento hacemos en la versión? MAYOR (breaking change)

Qué se ve afectado?
- Entidad/Persistencia? NO
- Servicio? NO
- Controlador? SI... cuál? v1 o v2? En v2

## Cambio 2c

En la BBDD el nombre del animalito ahora se tiene que llamar apodo

---

# Batch <- Anotaciones básicas de Spring

# Seguridad

Backend con microservicios... hemos dejado la puerta abierta... que pase el que quiera.

                    JS         Java (Spring)
Usuario ->Ivan -> Frontal -> Backend

Cómo gestionamos aquí la seguridad en los accesos?
- Identificación    La hace un persona/app... y efectivamente dice quién es.
                        - usuario
- Autenticación     Validar que eres quien dices ser
                        - Porque tenga algo que sé que solo él tiene ( el ... y yo)
                          - contraseña                                      * 
                          - cara/huella/características biométricas         * 
                          - Teléfono movil AUTENTICACION de 2 factores
                        - Por su DNI (y similares)
                          - Que alguien en quien confío me diga que eres quién dices ser.
- Autorización      Sabiendo que eres quien dices ser, qué puedes hacer

El mundo web, qué usamos normalmente, y más hoy en día? Usamos de todo

Pero el tema se ha vuelto complejo... y hay que tener en cuenta muchas cosas a la hora de autenticar... guardar las contraseñas...

Antiguamente en cada app que montábamos teníamos que hacerlo todo... y era un trabajón... y además, dejaba muchas puertas abiertas.

Contraseña -> BBDD 
    Encriptada? Ni de coña. Como me ganen el sistema, sacan todas las contraseñas de mis usuarios.
    Cómo se guarda una contraseña en una BBDD? No se guardan. Bajo pena capital
    Se guarda una huella de la contraseña (HASH) md5, sha-512, "sha-2048"
    DATO -> algoritmo de huella -> huella 
    huella -> contraseña? NO Directamente NO... por fuerza bruta... dame rato!

De hecho un buen sistema de almacenamiento de contraseña lo que guarda no es un hash.
El hash de un hash... y así 30k veces

Uso sistemas de autenticación externos... que lo hace gente que sabe de esto. Proveedor de identidades (autentica/autorizar)
    Oauth2 -> Protocolo estandar

              Navegador               WEB                   Proveedor de identidad      Backend
             HTML       JS            desde donde sirvo:
                                     HTML y JS
Felipe >>>>  boton login
(click en el boton)     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
             formulario <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
+ felipe                    
+ felipe123             >>>client_id>>>>>>>>>>>>>>>>>>>>>>> Los valida y genera un token (JWT)*
                            app que pide el token                       Va anotando los JWT que va entregando y 
                        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<             controla si siguen válidos (REVOCADO)
                        token (validar el token)
                            2 estrategias:
                                - Lo leo... y miro que esté bien
                                    + Felipe
                                    + Expiración
                                    + Y de paso te digo los roles
                                    + Un encriptado Pub/priv* de una huella de los datos
                                - Preguntar al Proveedor ... si el token sigue valiendo?
                                          >>>>>>>>>>>>>>>>>> Que el tio haya hecho logout
(click)     altaAnimalito
                         JS
            formulario
rellena                  JS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                                datos del animalito / token                            validar el token
                                                                                            + Lo leo... valido y punto
                                                                                            + Pregunto al proveedor AQUI SI !!!
                                                                            <<<<<<<<<<< 
                                                            Y tu quien cojines eres? para preguntarme a mi? por ese tio?
                                                                                            client_id
                                                                                            client_secreto (en algún momento ha estado expuesto)
                                                                                             (o te entrego un certificado que acredite que soy quien digo ser... emitido por alguien que tu conozcas)
                                                            OK ya se quien eres.
                                                            Valido el token y te digo si sigue siendo válido
                                                                                            Una vez validado y sabiendo los roles que tiene.. 
                                                                                                Decido (ESTO ES LO QUE PROGRAMAMOS NOSOTROS)
Y todo lo de arriba lo que se come la librería de seguridad de Spring
Para usar todo esto:
- Añadir dependencia de la lib de Spring para seguridad
- Añadir dependencia que implemente la parte de la lib de Spring para seguridad con mi proveedor concreto
- Integrar (configurar) mi servidor con el proveedor externo
  - Dar de alta configuraciones en Spring (Esto ya depende de CADA PROVEEDOR DE IDENTIDAD)
- Configurar las decisiones en mi app (REST)
