package ar.com.billetera.api.entities;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.Table;

import ar.com.billetera.api.entities.Pais.PaisEnum;
import ar.com.billetera.api.entities.Pais.TipoDocuEnum;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @Column(name = "persona_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personaId;

    private String nombre;

    @Column(name = "pais_id")
    private Integer paisId;

    @Column(name = "tipo_documento_id")
    private Integer tipoDocumentoId;

    private String documento;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Billetera billetera;


    public Integer getPersonaId() {
        return this.personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PaisEnum getPaisId() {
        return PaisEnum.parse(this.paisId);
    }

    public void setPaisId(PaisEnum paisId) {
        this.paisId = paisId.getValue();
    }

    public TipoDocuEnum getTipoDocumentoId() {
        return TipoDocuEnum.parse(this.tipoDocumentoId);
    }

    public void setTipoDocumentoId(TipoDocuEnum tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId.getValue();
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuario.setPersona(this);
    }

    public Billetera getBilletera() {
        return billetera;
    }

    //La relación bidireccional se hace a traves del set
    public void setBilletera(Billetera billetera) {
        this.billetera = billetera;
        this.billetera.setPersona(this);
    }

}
