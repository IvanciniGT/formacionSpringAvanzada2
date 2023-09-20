# Cómo solicito un  inyección

- Solicitando parámetros en cualquier función invocada por spring
- @Autowired: Lol podíamos poner en:
  - Propiedades de una clase
  - Constructor (No es necesario)
  Esta opción no es recomendable porque:
    - Las props no están disponibles en el constructor
    - Usa reflections: rendimiento, desde Java 1.9 (la máquina virtual de Java se convierte en un estándar y es modular), por defecto no está disponible.
  Podemos rellenar props de una clase mediante el constructor, simplemente solicitando los parámetros en el constructor.

# Cómo configuro una inyección

- @Component y derivados: @Service, @Repository, @Controller, @RestController
    Cuando es una clase mia la que quiero que se instancie y se inyecte cuando se necesite.
- @Bean / @Configuration
    Cuando es una clase de un framework o de una librería externa la que quiero que se instancie y se inyecte cuando se necesite.
    Y en este caso necesito crear yo la instancia dentro de una función que la devuelve. Esa función es la que marco con @Bean
    Y la meto en una clase con @Configuration

---

Historia de JAVA resumida

1995 - Sun microsystems - Java 1.0 - Empresa querida por todos
Tenia 2 problemas:
- La gran cantidad de cagadas en su sintaxis.
- La compra de Sun por Oracle - Empresa no tan querida 
    MySQL ---> MariaDB
    OpenOffice -> LibreOffice
    Hudson -> Jenkins

Java 1.6 sale en el 2006
Java 1.7 sale en el 2011    En 8 años salen 2 versiones
Java 1.8 sale en el 2014
    - Se introduce en soporte funcional al lenguaje: java.util.function.*, operador :: ->
    - Stream (map-reduce)
    - Optional
    - Posibilidad de implementar funciones estáticas en interfaces (publicas)
    - Paquete java.time (yoda-time)

----> Oracle dice... voy a cobrar por la licencia de Java
Java 1.9 sale en el 2017    En 3 años salen 2 versiones
    - Posibilidad de implementar funciones estáticas en interfaces (privadas)
    - El proyecto jigsaw -> Introduce el concepto de módulos en la JVM
        
        Modulo                          module "milibreria" {} -> Arquitecturas no monolíticas
            Paquetes
                Clase
                Interfaz
    - La JVM de Java pasa a ser un estándar. Y muchas empresas comienzan a crear sus propias JVM:
      - OpenJDK
      - Zulu
      - Amazon Corretto
      - Eclipse Temurin
      - Oracle JDK


js - node
kotlin


node es a JS lo que JVM es a JAVA

---

# Proyecto del curso

Backend de una tienda:
- BBDD
- API REST
- Pruebas
- Contra un Proveedor de Identidad (Keycloak)

---

# SOLID: Principios de desarrollo de software

# FIRST: Principios de pruebas de software

- F: Fast                   Rápida al ejecutarse
- I: Independent            Que no dependa de nada externo ni de otras pruebas
- R: Repeatable             Que la prueba se pueda repetir
- S: Self-validating        Debe tener sus propias validaciones
- T: Timely                 A tiempo. No me sirve cuando acabo el proyecto

Imagina que quiero hacer una prueba de interfaz gráfica.
- Tengo que hacer login en la app
- Y pinchar en un menú

Quizás me interesa que el servicio que hace login, no trabaje en realidad con la base de datos... validado al usuario y su contraseña contra una base de datos.
Ya tengo una prueba para eso, que valida que el servicio de login funciona correctamente.
Voy a montar un servicio de login de mentira (Fake, Stub que devuelva siempre True)
De esta forma, las pruebas se ejecutan más rápidas.

----

# Maven

Herramienta de automatización.
Le pedimos que ejecute tareas:
- compile               Compila lo que tengo en main/java/ y lo lleva a target/classes/
                        Copia lo que tengo en main/resources/ y lo lleva a target/classes/
- test-compile          Compila lo que tengo en test/java/ y lo lleva a target/test-classes/
                        Copia lo que tengo en test/resources/ y lo lleva a target/test-classes/
- test                  Ejecuta los test JUNIT que tengo en target/test-classes/ (a través del plugin surefire, que SOLO SABE EJECUTAR PRUEBAS JUNIT)
                        Genera un informe de pruebas, que deja en target/surefire-reports/ 
- package               Genera el jar (ear, war, pom)
- install               Copia el artefacto a mi carpeta .m2/repository/
                        Para que pueda ser usado como dependencia por otros proyectos dentro de mi máquina
- clean                 Borra la carpeta target/

src/
    main/
        java/
        resources/
    test/
        java/
        resources/
pom.xml

---

# J2EE -> JEE

Java Enterprise Edition -> Jakarta Enterprise Edition

Es una colección de especificaciones de Java, que me permiten desarrollar aplicaciones empresariales.
- Servlets
- JSP
- JSTL
- EJB
- JPA
- JMS
- JDBC
- ...

# Springboot

Es una extensión de spring... que nos ofrece formas sencillas de configurar apps Spring

Entre esas formas, encontramos los STARTERS, que me permiten meter un huevo de dependencias de una.