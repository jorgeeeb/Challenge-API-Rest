# Challenge Api Rest - Alura Latam

## Descripción

Este proyecto es una API REST desarrollada en Spring Boot para gestionar tópicos en un foro. Permite registrar, listar y obtener información sobre tópicos, así como autenticar usuarios. La API utiliza JWT para la autenticación y sigue las buenas practicas del curso para el diseño del software. El registro de cada usuario se hace sobre la base de datos directamente.

## Funcionalidades

- **Autenticación de usuarios**: Implementa un sistema de autenticación mediante JWT, garantizando la seguridad de las sesiones.
- **Gestión de tópicos**: Permite crear, listar y obtener tópicos, asegurando que solo se puedan acceder a aquellos tópicos que están activos.
- **Actualización de tópicos**: Permite actualizar la información de un tópico, asegurando que no se puedan crear duplicados con el mismo título y mensaje.


## Endpoints

### Autenticación de Usuario

- **POST** `/login`
  - **Body**:
    ```json
    {
      "email": "juan.perez@example.com",
      "contraseña": "securePassword123"
    }
    ```
  - **Respuesta**: 200 OK con el token JWT si la autenticación es exitosa (si el usuario está registrado en la base de datos); Si el email es incorrecto da un error 404 con "Usuario no encontrado"; Si la contraseña es incorrecta da un error 401 con "Credencial incorrecta"

### Registro de Tópico

- **POST** `/topicos`
  - **Body**:
    ```json
    {
      "titulo": "Título del Tópico",
      "mensaje": "Este es el mensaje del tópico.",
      "autor": {
        "id": 2
      },
      "nombreCurso": "HARDSKILLS"
    }
    ```
  - **Respuesta**: 201 Created si el tópico fue registrado exitosamente; 409 Conflict si ya existe un tópico con el mismo título o mensaje.

### Obtener Tópico por ID

- **GET** `/topicos/{id}`
  - **Respuesta**: 200 OK con los detalles del tópico si está activo; 404 Not Found si el tópico no existe o está inactivo.

### Listar Tópicos

- **GET** `/topicos`
  - **Respuesta**: 200 OK con una lista paginada de tópicos activos.

### Actualizar Tópico

- **PUT** `/topicos/{id}`
  - **Body**:
    ```json
    {
      "titulo": "Título Actualizado",
      "mensaje": "Mensaje actualizado del tópico.",
      "curso": "HARDSKILLS"
    }
    ```
  - **Respuesta**: 200 OK si el tópico fue actualizado exitosamente; 404 Not Found si el tópico no existe o está inactivo.

## Tecnologías Utilizadas
- **Spring Boot**: Para el desarrollo del backend.
- **JPA/Hibernate**: Para la gestión de la base de datos.
- **MySQL**: Como sistema de gestión de bases de datos.
- **Spring Security**: Para la gestión de la seguridad y autenticación.

## Instalación
- Deberá clonar el repositorio, configurar la base de datos y recien podrá ejecutar la aplicación.
