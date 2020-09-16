package ar.com.billetera.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.billetera.api.entities.Persona;
import ar.com.billetera.api.repos.PersonaRepository;
import ar.com.billetera.api.repos.UsuarioRepository;

@Service
public class PersonaService {

    @Autowired
    UsuarioRepository usuarioRepo;
    
    @Autowired
    PersonaRepository repo;

    public void grabar(Persona persona){
        repo.save(persona);
    }
}
