# Billetera Virtual ADA

_Creación de una Web Api de una billetera virtual: transacciones, usuario, persona, billetera, cuenta_

## Comenzando 🚀

_Realizar en base de datos las entidades, filas, columnas correspondientes para usar JPA y relacionar los datos del código que sera realizado con Spring-boot y la base de datos MySQL._

## Construido con 🛠️ y Despliegue 📦

_Herramientas a utilizar_

* [Spring-boot](https://spring.io/projects/spring-boot) - El framework para el lenguaje java
* [MySQL y Postgres](https://www.oracle.com/ar/database/what-is-a-relational-database/) - Base de datos utilizada
* [HEROKU](https://www.heroku.com/) - Despliegue de la APP
* [JWT](https://es.wikipedia.org/wiki/JSON_Web_Token) - Autentización, autorización y seguridad


## Wiki Info 📖

Puedes encontrar mucho más sobre el negocio de billetera virtual y su importancia en -> [Billetera Virtual](https://chequeado.com/hilando-fino/que-es-una-billetera-virtual-y-como-funciona/)


## Autores ✒️

_Menciona a todos aquellos que ayudaron a levantar el proyecto desde sus inicios_

* **Digneli Dávila** - *Backend* - [Digneli Davila](https://github.com/dignelidxdx)

##    Entidades

## Billetera
-billeteraId
-persona
-cuentas

## Usuario
-usuarioId
-username
-password
-email
-fechaLogin
-persona

## Persona(Cliente)
-personaId
-nombre
-dni
-fechaNacimiento
-nacionalidad
-usuario
-billetera

## Cuenta
-cuentaId
-saldo
-moneda
-billetera
-transacciones

## Transaccion(Movimiento)
-transaccionId
-cuenta
-fecha
-estado
-importe
-moneda
-tipoOperacion(INGRESO/EGRESO)
-conceptoOperacion
-detalle
-deUsuarioId
-aUsuarioId
-deCuentaId
-aCuentaId

-entidadSalida
-entidadLlegada

Se puede hacer como historial

## EntidadCuenta
-idUsuario
-cuentaId
-direccion(INGRESO/EGRESO)

billera 1--1 p
usuario 1--1 b
persona 1--1 u
transacciones m --1 p
 

##     Funcionalidad:

Envio y recibo de dinero
Pagos de facturas y compras 
Recargas y extracciones de dinero
Puntaje por usuario
Consultar saldo 
Historial 



	billetera :
id 
lista de transacciones 
persona 
tarjetas
cuenta

	usuario:
id usuario
nombre de usuario
contrasaña
persona 
mail
fecha de login

	persona :
id persona
nombre
dni 
billetera
usuario
f de nacimiento
nacionalidad

	transacciones:
id transacion 
importe
fechade transaccion 
estado 
forma de pago

	tarjetas :
id 
banco
numero 
codigo de seguridad


				tablas sql :

	billetera: 
columnas
id (pk)
id_transaccion(fk) 
id_persona(fk)
id_tarjeta
cuenta

	usuario:

id(pk)
id_persona(fk)
nombre_usuario
contrasaña
mail
fecha_login

	persona :
id_persona(pk)
nombre
dni 
id_billetera
id_usuario(fk)
f_de nacimiento
nacionalidad


	transacciones:
id_transacion pk
importe
fechade_transaccion 
estado 
forma_pago
billetera



---
⌨️ con ❤️ por [Digneli Davila](https://github.com/dignelidxdx) 😊