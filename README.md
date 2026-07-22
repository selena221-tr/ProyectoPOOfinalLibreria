# Sistema de Gestión de Librería y Reserva de Libros

## Descripción General
Esta aplicación es un sistema de escritorio diseñado para la administración de catálogos bibliográficos y la gestión de reservas de libros en tiempo real. Fue desarrollada aplicando el paradigma de Programación Orientada a Objetos (POO) en Java, utilizando JavaFX para el diseño de la interfaz gráfica de usuario y Spring Boot para la lógica de negocio y la persistencia de datos en MySQL.

---

## Tecnologías Utilizadas

* Lenguaje de Programación: Java 17 o superior
* Interfaz Gráfica: JavaFX 26 respaldada por archivos FXML y hojas de estilo CSS
* Framework Backend: Spring Boot con Spring Data JPA
* Base de Datos: MySQL Server
* Seguridad de Credenciales: Encriptación de contraseñas mediante el algoritmo BCrypt
* Gestión de Dependencias: Apache Maven

---

## Control de Acceso Basado en Roles (RBAC)

La interfaz y las funcionalidades del sistema se adaptan dinámicamente según el nivel de acceso del usuario autenticado:

1. Rol Administrador:
   * Cuenta con acceso total al inventario de la librería.
   * Habilitado para crear, consultar, actualizar y eliminar registros de libros.
   * Permite supervisar la columna de usuarios para identificar qué cliente mantiene reservado cada ejemplar.
   * Mantiene restringida la función de reserva directa de libros.

2. Rol Cliente:
   * Permite la visualización del catálogo completo de libros.
   * Habilitado exclusivamente para realizar reservas de ejemplares que se encuentren disponibles.
   * Mantiene bloqueados los campos del formulario y deshabilitados los botones de modificación, creación o eliminación de registros.

3. Rol Invitado:
   * Modalidad de acceso rápido para consulta pública sin requerir credenciales.
   * Permite realizar búsquedas por título y examinar el catálogo.
   * Mantiene deshabilitada cualquier opción de reserva o alteración de datos.

---

## Estructura de la Base de Datos

A continuación se detalla el script SQL necesario para la creación del esquema relacional y la inserción de los datos iniciales de la aplicación:

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

---

## Organización del Proyecto

src/
  main/
    java/epn/esfot/proyectofinallibreria/
      modelo/
        Libro.java: Entidad JPA que representa la tabla de libros.
        Usuario.java: Entidad JPA que representa la tabla de usuarios.
        LibroRepository.java: Interfaz de persistencia para operaciones en la tabla de libros.
        UsuarioRepository.java: Interfaz de persistencia para consultas de usuarios.
      Servicio.java: Capa de servicio encargada de la lógica de negocio del catálogo de libros.
      ServicioCliente.java: Capa de servicio encargada de la validación y registro de usuarios.
      LoginController.java: Controlador encargado de la vista de autenticación y registros.
      LibroController.java: Controlador de la vista principal, encargado de la tabla, permisos y reservas.
      SpringBootConfig.java: Clase de configuración de Spring Boot.
      HelloApplication.java: Clase encarga de vincular el ciclo de vida de JavaFX con el contexto de Spring.
      Launcher.java: Punto de entrada principal para la ejecución del programa.
    resources/epn/esfot/proyectofinallibreria/
      login.fxml: Definición de la interfaz gráfica de inicio de sesión.
      libreria.fxml: Definición de la interfaz gráfica de gestión y consulta de libros.
      estilos.css: Hoja de estilos para la presentación visual de las vistas FXML.

---

## Instrucciones de Instalación y Ejecución

1. Clonar el repositorio localmente.
2. Verificar la instalación activa del servidor MySQL y ejecutar el script de base de datos provisto.
3. Configurar el archivo application.properties con los parámetros correspondientes de la conexión local a MySQL:
   spring.datasource.url=jdbc:mysql://localhost:3306/libreria?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=tu_contrasenia
   spring.jpa.hibernate.ddl-auto=update
4. Ejecutar la clase Launcher.java desde el entorno de desarrollo preferido.

---

## Integrantes del Proyecto

* Moncayo Montalvo Haziel[cite: 1]
* Torres Lema Selena Alexandra[cite: 1]
* Ortiz Mena Angel Joel[cite: 1]

Escuela Politécnica Nacional (EPN)
Escuela de Formación de Tecnólogos (ESFOT)[cite: 1]
