# chinook-spring

Tienda de música Chinook en el navegador: el modelo de la PG3 de la UT2 (entidades y repositorios de Spring Data JPA) con controladores que escriben páginas HTML sencillas, servicios con interfaz e implementación, DTO, manejo de errores y pruebas sin base de datos. Prácticas guiadas 3.1 y 3.2 de DWES (UT3).

## Cómo arrancar

1. Requisitos: Java 21, IntelliJ IDEA y MariaDB con la base de datos Chinook cargada (`chinook_autoincrement` en `localhost:3306`, usuario `root` sin contraseña).
2. File › Open y elige la carpeta que contiene `pom.xml`. Espera a que Maven descargue las dependencias.
3. Ejecuta `ChinookSpringApplication`. En la consola debe aparecer `Tomcat started on port 8080`.
4. Abre `http://localhost:8080` en el navegador.

## Qué hace

| Método | Ruta | Qué muestra |
|---|---|---|
| GET | `/` y `/inicio` | Portada con enlaces y contador de visitas |
| GET | `/generos` | Géneros con su número de pistas |
| GET | `/artistas` | Redirige (302) a `/artistas/buscar` |
| GET | `/artistas/buscar?texto=` | Buscador de artistas |
| GET | `/artistas/{id}` | Ficha del artista con sus álbumes (404 si no existe) |
| GET | `/albumes/{id}` | Ficha del álbum con sus pistas (404 si no existe) |
| GET | `/hola` y `/hola-html` | Primeras pruebas del controlador |

Cualquier otra ruta muestra la página `static/error/404.html`; un id que no es un número (`/artistas/abc`) da 400.

### Capas

```mermaid
flowchart LR
    N[Navegador] -->|"GET /artistas/1"| C[ArtistaController]
    C -->|"buscarPorId(1)"| S[ArtistaService]
    S -->|"findById(1)"| R[ArtistaRepository]
    R -->|SQL| BD[("MariaDB · chinook")]