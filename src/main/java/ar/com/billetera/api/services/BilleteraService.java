package ar.com.billetera.api.services;

import org.springframework.stereotype.Service;
// Validaciones va a tener un monton
// Hay que delegar (siempre a la entidad)
// Al push notification y enviar mails es con el backend
@Service
public class BilleteraService {

    // no existe cuenta de ahorro, no hay dos cuentas en pesos solo 1.
    // Tiene su propia moneda, el usuario nunca debe saber que tiene una cuenta, se maneja como saldo. Se maneja a traves de moneda.

    /* Funcionalidad minima

        1. Metodo: Cargar saldo (recibe un importe y se le agrega a la cuenta)
        1.1 -- Recibir un importe, se busca una billetera por id
        se busca una cuenta por la moneda
        1.2 -- hacer transaccion
        Importe, salgo billetera
        1.3 -- Actualizar el saldo de la billetera

        2. Metodo: Enviar plata
        2.1 -- recibir un importe, la moneda en la que va a estar ese importe
        Info a nivel transaccion
        Metodos overload puede ser de mail a mail.
        2.2 -- actualizar los saldos de las cuentas (a una se le suma y a la otra se le resta) 
        2.3 -- Generar dos transacciones
        Notificar

        3. Metodo: consultar saldo
        3.1 --  recibir el id de la billetera y la moneda en la que esta la cuenta

    */
    
}
