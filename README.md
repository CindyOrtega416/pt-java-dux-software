# Prueba Técnica JAVA - DUX Software

## Descripción

Esta es una aplicación REST API para la gestión de equipos de fútbol. Proporciona endpoints para gestionar información relacionada con los equipos.

---

## Requisitos previos

- Java 17 o superior
- Maven 3.6.0 o superior
- Docker instalado y configurado

---

## Configuración del entorno

Antes de ejecutar la aplicación, asegúrate de configurar las variables de entorno.

1. Copia el archivo `.env.example` y renómbralo como `.env`:
   ```bash
   cp .env.example .env
   ```
2. Configura las variables en el archivo `.env` con 
````bash
JWT_SECRET=wLkzH3XqdV7sdfsdKJdfsdfsdXsdjfHsd12sdAsdmfds=
````

---

## Ejecución con Docker

1. **Construir la imagen de Docker:**
   ```bash
   docker build -t pt-java-dux-software:v4
   ```

2. **Descargar la imagen desde un registro remoto:**
   ```bash
   docker pull cindy416/pt-java-dux-software
   ```

3. **Ejecutar el contenedor:**
   ```bash
   docker run -p 8080:8080 --env-file .env cindy416/pt-java-dux-software
   ```

4**Verificar el contenedor en ejecución:**
   ```bash
   docker ps
   ```
---

## Endpoints de la API

Aquí se listan los endpoints que estarían disponibles en Swagger:

### **Endpoints Públicos**

#### Autenticación
- `POST /auth/login`: Inicia sesión y devuelve un token JWT. Actualmente contamos con un único usuario de prueba

### **Endpoints Protegidos**

#### Gestión de Equipos
- `GET /teams`: Obtiene una lista de equipos.
- `POST /teams`: Crea un nuevo equipo.
- `GET /teams/{id}`: Obtiene información de un equipo específico.
- `PUT /teams/{id}`: Actualiza información de un equipo específico.
- `DELETE /teams/{id}`: Elimina un equipo específico.

### **Observaciones**
Todos los endpoints protegidos requieren un token JWT válido en el encabezado de autorización:
```
Authorization: Bearer <tu_token_jwt>
```

---

## Notas adicionales

- Actualmente, Swagger UI se encuentra disponible en:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
- Para más información sobre los endpoints, consulta este archivo o las pruebas unitarias.

---

## Contribución

Si deseas contribuir al proyecto:
1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad: `git checkout -b feature/nueva-funcionalidad`
3. Realiza tus cambios y haz un commit: `git commit -m 'Agrega nueva funcionalidad'`
4. Haz un push de tu rama: `git push origin feature/nueva-funcionalidad`
5. Abre un Pull Request.

---

## Contacto

Si tienes preguntas o problemas, no dudes en crear un issue o contactarme a través de mi correo: `cindyortega416@gmail.com`.
