package ar.com.billetera.api.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
// Se identifica la cuenta por la moneda
//La idea es hacerle la vida mas facil a las personas bacarisandolas
import javax.persistence.Table;

import ar.com.billetera.api.entities.Transaccion.TipoTransaccionEnum;

@Entity
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @Column(name = "cuenta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cuentaId;
    private BigDecimal saldo;
    private String moneda;

    @ManyToOne
    @JoinColumn(name = "billetera_id", referencedColumnName = "billetera_id")
    private Billetera billetera;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones = new ArrayList<>();

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Billetera getBilletera() {
        return billetera;
    }

    public void setBilletera(Billetera billetera) {
        this.billetera = billetera;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    // La relacion bidireccional se hace a traves de un metodo que agrega a la lista
    // de transacciones, una cuenta!
    public void agregarTransaccion(Transaccion transaccion) {

        this.transacciones.add(transaccion);
        transaccion.setCuenta(this);

        BigDecimal saldoActual = this.getSaldo();
        BigDecimal importe = transaccion.getImporte();
        BigDecimal saldoNuevo;

        if (transaccion.getTipoOperacion() == TipoTransaccionEnum.ENTRANTE) {

            saldoNuevo = saldoActual.add(importe);

        } else {

            saldoNuevo = saldoActual.subtract(importe);

        }
        this.setSaldo(saldoNuevo);
    }

    public Transaccion generarTransaccion(String conceptoOperacion, String detalle, BigDecimal importe,
            TipoTransaccionEnum tipoOp) {

        Transaccion transaccion = new Transaccion();

        transaccion.setMoneda(moneda);
        transaccion.setFecha(new Date());
        transaccion.setConceptoOperacion(conceptoOperacion);
        transaccion.setDetalle(detalle);
        transaccion.setImporte(importe);
        transaccion.setTipoOperacion(tipoOp);// 1 Entrada, 0 Salida
        transaccion.setEstadoId(2);// -1 Rechazada 0 Pendiente 2 Aprobada

        if (transaccion.getTipoOperacion() == TipoTransaccionEnum.ENTRANTE) { // Es de entrada

            transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
            transaccion.setaCuentaId(this.getCuentaId());
        } else {
            // Es de salida
            transaccion.setDeCuentaId(this.getCuentaId());
            transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        }

        return transaccion;
    }

}
