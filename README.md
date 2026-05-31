# 🚌 Aucorsa — Gestión de Flota y Rutas

> Aplicación de escritorio full-stack para la gestión integral de flota de autobuses, conductores y asignación de rutas. Construida con Java Swing, Maven y MySQL, implementa el patrón MVC con acceso a datos mediante DAO y un sistema de gestión de imágenes integrado.

![Java](https://img.shields.io/badge/Java-25-blue)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-red)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![Swing](https://img.shields.io/badge/GUI-Swing-6DB33F)
![Licencia](https://img.shields.io/badge/Licencia-Privada-red)


## Tabla de contenidos

1. [Descripción general](#descripción-general)
2. [Arquitectura del proyecto](#arquitectura-del-proyecto)
3. [Estructura de carpetas](#estructura-de-carpetas)
4. [Guía de instalación](#guía-de-instalación)
5. [Documentación de la API](#documentación-de-la-api)
6. [Diagramas](#diagramas)
7. [Manual de usuario](#manual-de-usuario)
8. [Mantenibilidad y guía para nuevos desarrolladores](#mantenibilidad-y-guía-para-nuevos-desarrolladores)
9. [Despliegue en producción](#despliegue-en-producción)
10. [Historial de versiones](#historial-de-versiones)


## 1. Descripción general

Aucorsa es un sistema de escritorio diseñado para optimizar la gestión de una flota de autobuses, simplificando el CRUD de unidades, la administración centralizada de conductores, lugares y la asignación de rutas. Está construido sobre el patrón **MVC (Modelo-Vista-Controlador)**.

### Tecnologías principales

| Capa          | Tecnología          | Versión |
|---------------|---------------------|---------|
| Interfaz      | Java Swing          | 25      |
| Lógica        | Java (MVC + DAO)    | 25      |
| Persistencia  | MySQL               | 8.x     |
| Gestión       | Maven               | 3.8+    |

#### ✨ Características destacadas

- **Gestión completa de flota**: CRUD de autobuses, conductores y lugares.
- **Asignación de rutas**: Define qué conductor conduce qué bus, a qué lugar y en qué día.
- **Detalle de conductor**: Ficha con navegación entre registros y carga de imagen personalizada.
- **Excepciones con código**: Gestión profesional de errores con mensajes amigables.

### Comparativa con otras soluciones

| Funcionalidad               | Aucorsa | Excel básico | Alternativas genéricas |
|-----------------------------|---------|--------------|------------------------|
| Asignación conductor-bus    | ✅      | ❌           | Parcial                |
| Gestión de imágenes         | ✅      | ❌           | ❌                     |
| Interfaz dedicada           | ✅      | Parcial      | Parcial                |
| Reportes de rutas por día   | ✅      | ❌           | Parcial                |


## 2. Arquitectura del proyecto

El proyecto sigue un patrón **MVC clásico con capa DAO**:

- **Modelo**: Clases POJO en `model/` (Bus, Conductor, Lugar, Routes) — representan los datos.
- **Vista**: Componentes Swing en `view/` — paneles con `JTable` y ventanas de formulario.
- **Controlador**: Clases en `controller/` — gestionan eventos de UI, validan datos y llaman a los DAO.
- **DAO**: Clases en `controller/dao/` — encapsulan el acceso a MySQL y lanzan `AucorsaException`.

### Flujo de petición de datos
Usuario
│
├── Interactúa con ventana principal (AucorsaView)
│ │
│ └── MainController detecta evento (Ej: "Refrescar")
│ │
│ └── BusController.cargarBuses()
│ │
│ └── BusDAO.mostrarTodosLosBuses(Connection)
│ │
│ └── MySQL ──► Devuelve lista de buses
│
└── La vista actualiza el JTable con los datos


### Flujo de autenticación

*La aplicación no implementa autenticación, ya que está diseñada para entornos cerrados y controlados.*

### Base de datos

El script `database/schema.sql` crea la estructura completa con datos de ejemplo:

- **Bus**: matrícula (PK), tipo, licencia.
- **Conductor**: numConductor (PK), nombre, apellido, imagen.
- **Lugar**: idLugar (PK), cp, ciudad, ubicacion.
- **Routes**: matrícula, numConductor, idLugar (PK compuesta), diaSemana; con claves foráneas.


## 3. Estructura de carpetas
Aucorsa/
│
├── app/ # Punto de entrada de la aplicación
│ └── Principal.java
│
├── controller/ # Controladores y acceso a datos
│ ├── db/ConnectionBBDD.java # Gestión de la conexión a MySQL
│ ├── dao/ # Objetos de acceso a datos
│ │ ├── BusDAO.java
│ │ ├── ConductorDAO.java
│ │ ├── LugarDAO.java
│ │ └── RoutesDAO.java
│ ├── bus/ # Controladores de la sección Bus
│ ├── conductor/ # Controladores de la sección Conductor
│ ├── lugar/ # Controladores de la sección Lugar
│ ├── route/ # Controladores de la sección Route
│ └── MainController.java # Controlador principal (coordina todo)
│
├── model/ # Clases del modelo de datos (POJOs)
│ ├── Bus.java
│ ├── Conductor.java
│ ├── Lugar.java
│ └── Routes.java
│
├── view/ # Vistas (interfaz gráfica con Swing)
│ ├── AucorsaView.java # Ventana principal
│ ├── bus/ # Vistas de la sección Bus
│ ├── conductor/ # Vistas de la sección Conductor
│ ├── lugar/ # Vistas de la sección Lugar
│ └── routes/ # Vistas de la sección Route
│
├── exception/ # Gestión centralizada de errores
│ ├── AucorsaErrorCode.java
│ └── AucorsaException.java
│
├── database/ # Scripts de base de datos
│ └── schema.sql
│
├── docs/ # Documentación complementaria
│ └── api/ # Documentación de la API
│
├── pom.xml # Configuración de Maven
└── README.md



## 4. Guía de instalación

### 4.1 Prerrequisitos

| Herramienta       | Versión mínima | Comprobación |
|-------------------|----------------|--------------|
| Java JDK          | 25 (o 17/21)   | `java -version` |
| Maven             | 3.8+           | `mvn -version`  |
| MySQL Server      | 8.0            | `mysql --version` |
| Git               | cualquier moderna | `git --version` |

### 4.2 Clonar el repositorio
```bash
git clone https://github.com/rgutmed873-droid/Aucorsa.git
cd Aucorsa

mysql -u root -p < database/schema.sql

El script `database/schema.sql` crea la estructura completa con datos de ejemplo:

- **Bus**: matrícula (PK), tipo, licencia.
- **Conductor**: numConductor (PK), nombre, apellido, imagen.
- **Lugar**: idLugar (PK), cp, ciudad, ubicacion.
- **Routes**: matrícula, numConductor, idLugar (PK compuesta), diaSemana; con claves foráneas.

```
### 4.3 Configurar la base de datos
mysql -u root -p < database/schema.sql

### 4.4 Configurar credenciales de acceso a BD
public static final String URL      = "jdbc:mysql://127.0.0.1:3306/Aucorsa";
public static final String USER     = "root";
public static final String PASSWORD = "";

### 4.5 Compilar y ejecutar con Maven
mvn clean compile
mvn exec:java -Dexec.mainClass="app.Principal"

## 5. Documentación de la API
- openapi.yaml
- Aucorsa.postman_collection.json

## 6. Diagramas
- Para poder ver el diagrama de clases (Mermaid) hay que pinchar en el archivo arquitectura.md en el que podremos ver una descripción de como está estructurado el contenido con relación de la base de datos y su diagrama de clase
- El diagrama entidad relación se muestra aquí:
<img width="2150" height="3003" alt="Diagrama entidad-relacion" src="https://github.com/user-attachments/assets/c635f2f1-baed-4307-8919-61009d15af8e" />

## 7. Manual del usuario
Para consultar el manual del usuario está en la pestaña de este propio github de wiki en el que se explica como se usa la aplicación al igual que todo con referente a la a la aplicación y también hay imagenes que ayudan y hace comprender de mejor manera ciertas funciones de la que dispone el programa



