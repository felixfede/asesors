# asesors
Sistema para Registro, Auditoría y Fortalecimiento de Seguridad en Cuentas de Redes Sociales

⚠️ AVISO 

El codigo publicado en este repositorio corresponde a una vesion de (prototipo) con fines universitarios.
En esta version de prototipo se podran observar credenciales en texto plano, todos los datos insertados en la BD son ficticios, generados manualmente con fines de validar el funcionamiento del sistema.

​🎯 En futuras versiones se incorporaran mejoras a la seguridad para ofuscacion de credenciales y controles de segregacion de funciones por ROL.       


# Acerca del sistema 
ASESORS permite a agencias de marketing digital auditar el estado de seguridad de las cuentas de redes sociales de sus clientes, mediante:
- Diagnósticos por cuenta
- Registros centralizados 
- Reportes visuales con representacion de estados por colores 🔴🟡​🟢

# Caracteristicas y Funcionalidades principales

- Gestión de clientes y cuentas de RRSS
- Diagnósticos por cuenta: detección de 2FA, contraseñas débiles y actividad sospechosa
- Reportes de estado general y por cliente, con códigos de color (verde, amarillo, rojo)
- Inicio de sesion de usuarios con validación de credenciales hasheadas
- Persistencia en MySQL con conexión segura vía JDBC
- Interfaz gráfica desarrollada en Java Swing

## 🔒 Seguridad

El sistema utiliza hash SHA-256 para proteger contraseñas en la tabla de usuarios, el acceso está segmentado por credenciales.

Se prevé para la próxima release:
- Cifrado completo de configuraciones sensibles (como la clase de conexión)
- Restricciones por rol (administrador vs operador)
- Verificación en línea con servicios como HaveIBeenPwned

## ⚙️ Requisitos

- Java 17+
- MySQL Server (8.x)
- IntelliJ IDEA (u otro IDE compatible)
- Conector JDBC