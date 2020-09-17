package ar.com.billetera.api.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.billetera.api.entities.Billetera;
import ar.com.billetera.api.entities.Cuenta;
import ar.com.billetera.api.entities.Persona;
import ar.com.billetera.api.entities.Usuario;
import ar.com.billetera.api.repos.UsuarioRepository;
import ar.com.billetera.api.security.Crypto;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PersonaService personaService;

    @Autowired
    BilleteraService billeteraService;

    public Usuario login(String username, String password) {

        /**
         * Metodo IniciarSesion recibe usuario y contraseña validar usuario y contraseña
         */
    
        Usuario usuario = buscarPorUsername(username);
    
        if (usuario == null || !usuario.getPassword().equals(Crypto.encrypt(password, usuario.getUsername()))) {    
          throw new BadCredentialsException("Usuario o contraseña invalida");
        }
    
        return usuario;
      }
 
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario creaUsuario(String name, Integer country, Integer identificationType, String identification, Date birthDate, String username, String email, String password) {
        
        Persona persona = new Persona();
        persona.setNombre(name);
        persona.setPaisId(country);
        persona.setTipoDocumentoId(identificationType);
        persona.setDocumento(identification);
        persona.setFechaNacimiento(birthDate);
        
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword(password);

        persona.setUsuario(usuario);

        personaService.grabar(persona);

        Billetera billetera = new Billetera();

        Cuenta pesos = new Cuenta();
        
        pesos.setSaldo(new BigDecimal(0));
        pesos.setMoneda(("ARS"));

        Cuenta dolares = new Cuenta();

        dolares.setSaldo(new BigDecimal(0));
        dolares.setMoneda("USD");

        billetera.agregarCuenta(pesos);
        billetera.agregarCuenta(dolares);

        persona.setBilletera(billetera);

        billeteraService.grabar(billetera);
        
        billeteraService.cargarSaldo(new BigDecimal(500), "ARS", billetera.getBilleteraId(), "regalo", "Bienvenida por creacion de usuario");        
        
        billeteraService.grabar(billetera);

        return usuario;


    }



    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
    
        claims.put("billeteraId", usuario.getPersona().getBilletera().getBilleteraId());
    
        return claims;
      }
    
    public UserDetails getUserAsUserDetail(Usuario usuario) {
    UserDetails uDetails;

    uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

    return uDetails;
    }

    private Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    Integer billeteraId = usuario.getPersona().getBilletera().getBilleteraId();

    authorities.add(new SimpleGrantedAuthority("CLAIM_billeteraId_" + billeteraId));
    return authorities;


    }



}
