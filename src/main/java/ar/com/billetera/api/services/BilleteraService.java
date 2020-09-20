package ar.com.billetera.api.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Validaciones va a tener un monton
// Hay que delegar (siempre a la entidad)
// Al push notification y enviar mails es con el backend

import ar.com.billetera.api.entities.Billetera;
import ar.com.billetera.api.entities.Cuenta;
import ar.com.billetera.api.entities.Transaccion;
import ar.com.billetera.api.entities.Usuario;
import ar.com.billetera.api.entities.Transaccion.ResultadoTransaccionEnum;
import ar.com.billetera.api.entities.Transaccion.TipoTransaccionEnum;
import ar.com.billetera.api.repos.BilleteraRepository;
import ar.com.billetera.api.sistema.pagada.PagADAService;
import ar.com.billetera.api.sistema.pagada.models.InfoPago;
import ar.com.billetera.api.sistema.pagada.models.ResultadoPago;
import ar.com.billetera.api.sistema.pagada.models.Servicio;

@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository billeteraRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PagADAService pagADAService;

    public void grabar(Billetera billetera) {
        billeteraRepository.save(billetera);
    }

    public void cargarSaldo(BigDecimal saldo, String moneda, Integer billeteraId, String conceptoOperacion,
            String detalle) {

        Billetera billetera = this.buscarPorId(billeteraId);

        cargarSaldo(saldo, moneda, billetera, conceptoOperacion, detalle);
    }

    public void cargarSaldo(BigDecimal saldo, String moneda, Billetera billetera, String conceptoOperacion,
            String detalle) {

        Cuenta cuenta = billetera.getCuenta(moneda);

        Transaccion transaccion = new Transaccion();

        transaccion.setMoneda(moneda);
        transaccion.setFecha(new Date());
        transaccion.setConceptoOperacion(conceptoOperacion);
        transaccion.setDetalle(detalle);
        transaccion.setImporte(saldo);
        transaccion.setTipoOperacion(TipoTransaccionEnum.ENTRANTE);
        transaccion.setEstadoId(2);// -1 Rechazada 0 Pendiente 2 Aprobada
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

    public ResultadoTransaccionEnum enviarSaldo(BigDecimal importe, String moneda, Integer billeteraOrigenId,
            Integer billeteraDestinoId, String concepto, String detalle) {

        if (importe.compareTo(new BigDecimal(0)) == -1)
            return ResultadoTransaccionEnum.ERROR_IMPORTE_NEGATIVO;
        /**
         * Metodo enviarSaldo buscar billetera por id se identifica cuenta por moneda
         * determinar importe a transferir billetera de origen y billetera destino
         * actualizar los saldos de las cuentas (resta en la origen y suma en la
         * destino) generar 2 transacciones
         * 
         * ver delegaciones sobre entidades
         * 
         */

        Billetera billeteraSaliente = this.buscarPorId(billeteraOrigenId);

        if (billeteraSaliente == null)
            return ResultadoTransaccionEnum.BILLETERA_ORIGEN_NO_ENCONTRADA;

        Billetera billeteraEntrante = this.buscarPorId(billeteraDestinoId);

        if (billeteraEntrante == null)
            return ResultadoTransaccionEnum.BILLETERA_DESTINO_NO_ENCONTRADA;

        Cuenta cuentaSaliente = billeteraSaliente.getCuenta(moneda);

        if (cuentaSaliente == null)
            return ResultadoTransaccionEnum.CUENTA_ORIGEN_INEXISTENTE;

        Cuenta cuentaEntrante = billeteraEntrante.getCuenta(moneda);

        if (cuentaEntrante == null)
            return ResultadoTransaccionEnum.CUENTA_DESTINO_INEXITENTE;

        if (cuentaSaliente.getSaldo().compareTo(importe) == -1)
            return ResultadoTransaccionEnum.SALDO_INSUFICIENTE;

        Transaccion tSaliente = new Transaccion();
        Transaccion tEntrante = new Transaccion();

        tSaliente = cuentaSaliente.generarTransaccion(concepto, detalle, importe, TipoTransaccionEnum.SALIENTE);
        tSaliente.setaCuentaId(cuentaEntrante.getCuentaId());
        tSaliente.setaUsuarioId(billeteraEntrante.getPersona().getUsuario().getUsuarioId());

        tEntrante = cuentaEntrante.generarTransaccion(concepto, detalle, importe, TipoTransaccionEnum.ENTRANTE);
        tEntrante.setDeCuentaId(cuentaSaliente.getCuentaId());
        tEntrante.setDeUsuarioId(billeteraSaliente.getPersona().getUsuario().getUsuarioId());

        cuentaSaliente.agregarTransaccion(tSaliente);
        cuentaEntrante.agregarTransaccion(tEntrante);

        this.grabar(billeteraSaliente);
        this.grabar(billeteraEntrante);


        return ResultadoTransaccionEnum.INICIADA;

    }

    public ResultadoTransaccionEnum enviarSaldo(BigDecimal importe, String moneda, Integer billeteraOrigenId,
            String email, String concepto, String detalle) {

        Usuario usuarioDestino = usuarioService.buscarPorEmail(email);

        if (usuarioDestino == null)
            return ResultadoTransaccionEnum.EMAIL_DESTINO_INEXISTENTE;
        return this.enviarSaldo(importe, moneda, billeteraOrigenId,
                usuarioDestino.getPersona().getBilletera().getBilleteraId(), concepto, detalle);

    }

    public List<Transaccion> listarTransacciones(Billetera billetera, String moneda) {

        List<Transaccion> movimientos = new ArrayList<>();

        Cuenta cuenta = billetera.getCuenta(moneda);

        for (Transaccion transaccion : cuenta.getTransacciones()) {

            movimientos.add(transaccion);
        }

        return movimientos;
    }

    public List<Transaccion> listarTransacciones(Billetera billetera) {

        List<Transaccion> movimientos = new ArrayList<>();

        for (Cuenta cuenta : billetera.getCuentas()) {

            for (Transaccion transaccion : cuenta.getTransacciones()) {

                movimientos.add(transaccion);
            }
        }
        return movimientos;
    }
    public List<Servicio> buscarServicioPorCodigoDeBarras(String codigoBarras) {
        return pagADAService.buscarServicioPorCodigoDeBarras(codigoBarras);
    }

    public ResultadoPago pagarServicio(Integer billeteraId, Integer servicioId, InfoPago pago) {

        ResultadoPago resultadoPago = new ResultadoPago();

        Billetera billetera = buscarPorId(billeteraId);

        Cuenta cuenta = billetera.getCuenta(pago.getMoneda());

        if (cuenta.getSaldo().compareTo(pago.getImportePagado()) == -1) {
            resultadoPago.isOk = false;
            resultadoPago.message = "Saldo insuficiente";
            return resultadoPago;
        }
        
        resultadoPago = pagADAService.pagarServicio(servicioId, pago);

        if (resultadoPago.isOk) {

            
            Transaccion transaccion = new Transaccion();
            // transaccion.setCuenta(cuenta);
            transaccion.setMoneda(pago.getMoneda());
            transaccion.setFecha(new Date());
            transaccion.setConceptoOperacion("Pago a " + resultadoPago.nombreEmpresa);
            transaccion.setDetalle("Pagaste a " + resultadoPago.nombreEmpresa);
            transaccion.setImporte(pago.getImportePagado());
            transaccion.setTipoOperacion(TipoTransaccionEnum.SALIENTE);// 1 Entrada, 0 Salida
            transaccion.setEstadoId(2);// -1 Rechazada 0 Pendiente 2 Aprobada
            transaccion.setDeCuentaId(cuenta.getCuentaId());
            transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
            transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
            transaccion.setaCuentaId(cuenta.getCuentaId());

            cuenta.agregarTransaccion(transaccion);

            this.grabar(billetera);



        }

        

        return resultadoPago;
    }

}
