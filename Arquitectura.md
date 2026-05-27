# Arquitectura del Proyecto Aucorsa

## Patrones de Diseño
- **MVC**: Modelo (model/), Vista (view/), Controlador (controller/).
- **DAO**: Cada entidad tiene su DAO en `controller/dao/`, aislando la lógica de persistencia.
- **Excepción personalizada**: `AucorsaException` con códigos `AucorsaErrorCode` para manejo uniforme de errores.

## Organización de carpetas

## Diagrama de clases (Mermaid)
```mermaid
classDiagram
    class Principal {
        +main()
    }
    class AucorsaView {
        -JTabbedPane tabs
        +getBusPanel()
    }
    class MainController {
        -BusController busController
        -ConductorController conductorController
        +MainController(AucorsaView)
    }
    class BusController {
        +cargarBuses()
        +añadirBus()
    }
    class BusDAO {
        +añadirBus(Connection, Bus) boolean
        +mostrarTodosLosBuses(Connection) List~Bus~
    }
    class ConnectionBBDD {
        +getConexion() Connection
    }
    Principal --> AucorsaView
    Principal --> MainController
    MainController --> BusController
    BusController --> BusDAO
    BusDAO ..> ConnectionBBDD
<img width="3091" height="1727" alt="Diagrama E-R Aucorsa" src="https://github.com/user-attachments/assets/cbde249d-45d3-4e9d-b4a5-e81e38ed42a8" />
