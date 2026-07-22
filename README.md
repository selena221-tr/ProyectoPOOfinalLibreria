# Sistema de Gestión de Librería

Aplicación de escritorio en Java para administrar el catálogo de una librería y gestionar reservas de libros, con inicio de sesión y permisos según el rol del usuario.

## Tecnologías

- **Java** + **JavaFX** (interfaz gráfica)
- **Spring Boot** + **Spring Data JPA / Hibernate** (lógica y persistencia)
- **MySQL** (base de datos)
- **BCrypt** (encriptar contraseñas)
- **Maven** (gestión de dependencias)

## Roles del sistema

| Rol | Puede hacer |
|---|---|
| **Administrador** | Crear, editar, eliminar y ver todos los libros. Ver quién tiene reservado cada libro. No puede reservar. |
| **Cliente** | Ver el catálogo, buscar por título y **reservar** libros disponibles. No puede crear/editar/eliminar. |
| **Invitado** | Solo ver el catálogo y buscar por título. Entra sin necesidad de registrarse. |

## Cómo ejecutar el proyecto

1. Clonar el repositorio.
2. Tener MySQL corriendo y ejecutar el script de la base de datos (ver abajo).
3. En `src/main/resources/application.properties`, configurar tu conexión:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/libreria?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=tu_contrasenia
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Ejecutar la clase `Launcher.java`.
5. Iniciar sesión con el usuario ya cargado por el script:
   - Usuario: `admin` — Rol: `Administrador` — Contraseña: `admin` *(ajusta si tu compañera usó otra)*

## Base de datos

```sql
CREATE DATABASE IF NOT EXISTS libreria;
USE libreria;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    rol VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (nombre, contrasenia, rol) VALUES
('admin', '$2a$10$MxMJhXf3X1fZXfPCdowwPuTlzZCBWU6gkU4vunSBD2ya9Y3zVEjTm', 'Administrador');

CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    editorial VARCHAR(100) NOT NULL,
    formato VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    anio_publicacion INT NOT NULL,
    numero_paginas INT NOT NULL,
    disponibilidad BOOLEAN NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO libros (titulo, autor, editorial, formato, categoria, anio_publicacion, numero_paginas, disponibilidad) VALUES
('El Principito', 'Antoine de Saint-Exupéry', 'Salamandra', 'Físico', 'Infantil', 1943, 96, true),
('Cien años de soledad', 'Gabriel García Márquez', 'Sudamericana', 'Digital', 'Novela', 1967, 471, true),
('1984', 'George Orwell', 'Debolsillo', 'Digital', 'Ciencia ficción', 1949, 328, true),
('Harry Potter y la piedra filosofal', 'J. K. Rowling', 'Salamandra', 'Físico', 'Fantasía', 1997, 320, true),
('Orgullo y prejuicio', 'Jane Austen', 'Penguin', 'Digital', 'Romance', 1813, 432, true),
('El Hobbit', 'J. R. R. Tolkien', 'Minotauro', 'Físico', 'Fantasía', 1937, 310, true),
('Crónica de una muerte anunciada', 'Gabriel García Márquez', 'Norma', 'Digital', 'Novela', 1981, 144, true);
```

> ⚠️ Nota: la columna `usuario_id` en `libros` es necesaria porque un libro reservado queda vinculado al usuario que lo reservó.

## Estructura del código

```
epn.esfot.proyectofinallibreria/
├── HelloApplication.java     → arranque de JavaFX + Spring
├── Launcher.java             → punto de entrada del programa
├── SpringBootConfig.java     → configuración de Spring Boot
├── LoginController.java      → login, registro y sesión de invitado
├── LibroController.java      → tabla de libros, CRUD y reservas
├── Servicio.java             → lógica de negocio de libros
├── ServicioCliente.java      → lógica de negocio de usuarios
└── modelo/
    ├── Libro.java             → entidad → tabla libros
    ├── Usuario.java           → entidad → tabla usuarios
    ├── LibroRepository.java
    └── UsuarioRepository.java
```

## Integrantes

- Moncayo Montalvo Haziel
- Torres Lema Selena Alexandra
- Ortiz Mena Angel Joel

**Escuela Politécnica Nacional (EPN) — ESFOT**
