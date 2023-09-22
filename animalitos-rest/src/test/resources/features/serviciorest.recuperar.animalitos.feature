#language:es

Característica: Servicio REST de recuperación de un animalito v1

    Esquema del escenario: Cuando se solicita la información de un animalito dado un id, que exista en la BBDD, se devuelven los datos del animalito
        # Condiciones que deben darse para que la prueba pueda ejecutarse
        Dado que tengo tengo un animalito 
            Y ese animalito tiene por "nombre": "<nombre>"
            Y ese animalito tiene por "edad": <edad>
            Y ese animalito tiene por "tipo": "<tipo>"
            Y ese animalito tiene por "color": "<color>"
            Y el animalito está guardado en la BBDD dentro de la tabla "animalitos"
        Cuando hacemos una petición por método "GET" al servicio "/api/v1/animalitos"
            Y añado en el path de la petición el "id" que tiene el animalito 
        Entonces el servicio nos devuelve un código de estado "OK"
           Y el servicio nos devuelve un objeto JSON,
           Y el objeto JSON tiene por "nombre": "<nombre>"
           Y el objeto JSON tiene por "tipo": "<tipo>"
           Y el objeto JSON tiene por "color": "<color>"
           Y el objeto JSON tiene por "edad": <edad>
           Y el objeto JSON tiene por "id" el "id" que tiene el animalito

        Ejemplos:
            | nombre | edad | tipo    | color  | 
            | Pipo   | 2    | perro   | marrón | 
            | Miau   | 1    | gato    | blanco | 
            | Pepa   | 3    | pájaro  | verde  | 

    Esquema del escenario: Cuando se solicita la información de un animalito dado un id, que no exista en la BBDD, se devuelve un error
        # Condiciones que deben darse para que la prueba pueda ejecutarse
        Dado que tengo tengo un animalito 
            Y ese animalito tiene por "id": <id>
            Y el animalito no está guardado en la BBDD dentro de la tabla "animalitos"
        Cuando hacemos una petición por método "GET" al servicio "/api/v1/animalitos"
            Y añado en el path de la petición el "id" que tiene el animalito 
        Entonces el servicio nos devuelve un código de estado "NOT_FOUND"

        Ejemplos:
            | id  |
            | 1   |
            | 2   |
            | 3   |

# Si la BBDD está caída, se devuelve un error de tipo 500