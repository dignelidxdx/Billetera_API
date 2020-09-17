package ar.com.billetera.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ar.com.billetera.api.entities.Usuario;
import ar.com.billetera.api.models.request.LoginRequest;
import ar.com.billetera.api.models.request.RegistrationRequest;
import ar.com.billetera.api.models.response.LoginResponse;
import ar.com.billetera.api.models.response.RegistrationResponse;
import ar.com.billetera.api.security.jwt.JWTTokenUtil;
import ar.com.billetera.api.services.JWTUserDetailsService;
import ar.com.billetera.api.services.UsuarioService;

@RestController
public class AuthController {

    @Autowired
    private JWTTokenUtil JWTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req) {

        RegistrationResponse r = new RegistrationResponse();
        
        Usuario usuario = usuarioService.creaUsuario(req.nombre, req.country, req.identificationType, req.identification, req.birthDate, req.username, req.email, req.password);

        r.isOk = true;
        r.message = "Te registraste con exito!!!!";
        r.userId = usuario.getUsuarioId();
        return ResponseEntity.ok(r);

    }

 

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);

        Map<String,Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        String token = JWTokenUtil.generateToken(userDetails, claims);

        //Cambio para que devuelva el full perfil
        Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUsuarioId();
        r.billeteraId = u.getPersona().getBilletera().getBilleteraId();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);
    } 



    
}
