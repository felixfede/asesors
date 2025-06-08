# asesors
Proyecto para Registro, Auditoria y Hardening de cuentas de RRSS

⚠️ AVISO: El codigo publicado en los repositorios corresponde a una vesion de (prototipo) con fines universitarios.
          *** En esta version de prototipo se podran observar passwords en texto plano ***, todos los datos insertados corresponden a informacion de prueba (no real) generada manualmente.

​🎯 En versiones posteriores se aplicaran buenas practicas de mejora a la seguridad para ofuscacion de credenciales.       


# Acerca del sistema 
El sistema ASESORS permite a agencias de marketing digital auditar el estado de seguridad de las cuentas de redes sociales de sus clientes, mediante diagnósticos, registros centralizados y visualización estructurada de la información.

# Funcionalidades principales

- Gestión de clientes y sus cuentas de RRSS.
- Registro y actualización de diagnósticos de seguridad por cuenta.
- Reportes de estado general por cliente (cuentas comprometidas / seguras).
- Login con control de acceso por usuario.
- Conexión a base de datos MySQL para persistencia de informacion.

## ⚙️ Requisitos

- Java 17+
- MySQL Server (8.x)
- IntelliJ IDEA (u otro IDE compatible)
- Conector JDBC