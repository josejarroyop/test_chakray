# User Management API - Chakray Technical Test

Esta es una API REST desarrollada con **Spring Boot** para la gestión de usuarios, cumpliendo con requisitos de seguridad, auditoría y containerización.

##Características Principales

* Seguridad: Cifrado de contraseñas mediante **AES-256** antes de la persistencia en base de datos.
* Auditoría: Registro automático de la fecha de creación en la zona horaria de Madagascar (`Indian/Antananarivo`) usando `@PrePersist`.
* Arquitectura: Uso de **DTOs** para desacoplar la capa de presentación de la capa de datos y proteger información sensible (como el password en las respuestas).
* Persistencia: PostgreSQL con generación automática de UUIDs para los identificadores de usuario.
* Dockerizado: Configuración lista para desplegar mediante Docker Compose.

## Requisitos

* Docker y Docker Compose.
* Java 17 (opcional, si se desea correr localmente).

## Instalación y Despliegue

Para levantar el proyecto completo (API + Base de Datos), sigue estos pasos:

1. Clona el repositorio.
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta el comando:
   ```bash
   docker-compose up --build
