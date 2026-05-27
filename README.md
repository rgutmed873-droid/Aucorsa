# 🚌 Aucorsa - Sistema de Gestión de Flota y Rutas

> **Package de Entrega para Transferencia**  
> *Tiempo estimado para poner en marcha y entender el proyecto: **< 2 horas***

[![Java](https://img.shields.io/badge/Java-25-blue)](https://adoptium.net/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)](https://mysql.com)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-red)](https://maven.apache.org)

## 📋 Tabla de Contenidos
- [Requisitos de Software](#requisitos-de-software)
- [Despliegue en Local (paso a paso)](#despliegue-en-local-paso-a-paso)
- [Variables de Entorno](#variables-de-entorno)
- [Documentación de la API](#documentación-de-la-api)
- [Guía de Arquitectura](#guía-de-arquitectura)
- [Manual de Usuario](#manual-de-usuario)
- [Mantenimiento y Tests](#mantenimiento-y-tests)

---

## Requisitos de Software

| Software     | Versión                      | Comando de verificación       |
|--------------|------------------------------|-------------------------------|
| Java JDK     | 17, 21 o **25** (recomendado)| `java -version`               |
| Maven        | 3.8+                         | `mvn -version`                |
| MySQL Server | 8.0                          | `mysql --version`             |
| Git          | cualquier moderna            | `git --version`               |
| IDE          | IntelliJ IDEA (recomendado)  | -                             |

> ⚠️ El proyecto está configurado para JDK 25 (ver `misc.xml`). Si usas JDK 17/21, cambia el `languageLevel` en tu IDE.

---

## Despliegue en Local (paso a paso)

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/Aucorsa.git
cd Aucorsa
