package ar.com.billetera.api.models.request;

import java.util.Date;

public class RegistrationRequest {
    
    public String fullName;
    public int country;
    public int identificationType;
    public String identification;
    public Date birthDate;
    public String email;
    public String password;
    
}
