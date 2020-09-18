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

    public void cargarSaldo(BigDecimal saldo, String moneda, Billetera billetera, String conceptoOperacion, String detalle){

        Cuenta cuenta = billetera.getCuenta(moneda);
   
        Transaccion transaccion = new Transaccion();
 
        transaccion.setMoneda(moneda);
        transaccion.setFecha(new Date());
        transaccion.setConceptoOperacion(conceptoOperacion);
        transaccion.setDetalle(detalle);
        transaccion.setImporte(saldo);
        transaccion.setTipoOperacion(1);
        transaccion.setEstadoId(2);
        transaccion.setDeCuentaId(cuenta.getCuentaId());
        transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaCuentaId(cuenta.getCuentaId());
        
        cuenta.agregarTransaccion(transaccion);

        this.grabar(billetera);
    }

    public BigDecimal consultarSaldo(Integer billeteraId, String moneda) {

        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);

        Cuenta cuenta = billetera.getCuenta(moneda);

        return cuenta.getSaldo();

    }

     public Billetera buscarPorId(Integer id) {

        return billeteraRepository.findByBilleteraId(id);
    }



}
