# Arquitectura del Proyecto Aucorsa

## Patrones de Diseño
- **MVC**: Modelo (model/), Vista (view/), Controlador (controller/).
- **DAO**: Cada entidad tiene su DAO en `controller/dao/`, aislando la lógica de persistencia.
- **Excepción personalizada**: `AucorsaException` con códigos `AucorsaErrorCode` para manejo uniforme de errores.

## Organización de carpetas
