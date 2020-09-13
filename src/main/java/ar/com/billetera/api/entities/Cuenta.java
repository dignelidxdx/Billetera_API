package ar.com.billetera.api.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
// Se identifica la cuenta por la moneda
//La idea es hacerle la vida mas facil a las personas bacarisandolas
@Entity(name = "cuenta")
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
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    // La relacion bidireccional se hace a traves de un metodo que agrega a la lista de transacciones, una cuenta!
    public void agregarTransaccion(Transaccion transaccion){
        this.transacciones.add(transaccion);
        transaccion.setCuenta(this);
    }
    
}
