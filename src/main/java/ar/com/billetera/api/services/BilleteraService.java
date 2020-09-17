package ar.com.billetera.api.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Validaciones va a tener un monton
// Hay que delegar (siempre a la entidad)
// Al push notification y enviar mails es con el backend

import ar.com.billetera.api.entities.Billetera;
import ar.com.billetera.api.entities.Cuenta;
import ar.com.billetera.api.entities.Transaccion;
import ar.com.billetera.api.repos.BilleteraRepository;
@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository billeteraRepository;

    public void grabar(Billetera billetera){
        billeteraRepository.save(billetera);
    }

    public void cargarSaldo(BigDecimal saldo, String moneda, Integer billeteraId, String conceptoOperacion, String detalle){

        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);

        Cuenta cuenta = billetera.getCuenta(moneda);
   
        Transaccion transaccion = new Transaccion();
 
        transaccion.setMoneda(moneda);
        transaccion.setFecha(new Date());
        transaccion.setConceptoOperacion(conceptoOperacion);
        transaccion.setDetalle(detalle);
        transaccion.setImporte(saldo);
        transaccion.setTipoOperacion(1);
        transaccion.setEstadoId(1);
        transaccion.setDeCuentaId(cuenta.getCuentaId());
        transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaCuentaId(cuenta.getCuentaId());
        
        cuenta.agregarTransaccion(transaccion);

        BigDecimal saldoActual = cuenta.getSaldo();
        saldoActual.add(saldo);
        cuenta.setSaldo(saldoActual);

        
    }

    public Transaccion crearTransaccion() {

        Transaccion transaccion = new Transaccion();

        return null;

    }



}
