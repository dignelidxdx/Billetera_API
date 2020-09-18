package ar.com.billetera.api.models.request;

import java.util.Date;

import ar.com.billetera.api.entities.Pais.PaisEnum;
import ar.com.billetera.api.entities.Pais.TipoDocuEnum;

public class RegistrationRequest {
    
    public String fullName;
    public PaisEnum country;
    public TipoDocuEnum identificationType;
    public String identification;
    public Date birthDate;
    public String email;
    public String password;
    
}
