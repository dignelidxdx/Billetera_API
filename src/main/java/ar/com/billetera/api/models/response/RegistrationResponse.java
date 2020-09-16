package ar.com.billetera.api.models.response;

import java.util.ArrayList;
import java.util.List;

public class RegistrationResponse {
    
    public boolean isOk = false;
    public String message = "";
    public Integer userId ;
    public List<ErrorResponseItem> errors = new ArrayList<>();
    
}
