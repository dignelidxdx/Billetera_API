package ar.com.billetera.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.billetera.api.models.request.RegistrationRequest;
import ar.com.billetera.api.models.response.RegistrationResponse;

@RestController
public class AuthController {

    @Autowired
    private JWTokenUtil JWTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping("auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req) {

        RegistrationResponse r = new RegistrationResponse();
        
        r.isOk = true;
        r.message = "Te registraste con exito!!!!";
        r.userId = 0;
        return ResponseEntity.ok(r);

    }



    
}
