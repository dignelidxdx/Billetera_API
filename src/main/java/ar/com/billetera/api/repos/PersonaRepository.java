package ar.com.billetera.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.billetera.api.entities.Persona;
//<Persona, Integer>Maneja la clase Persona y el Integer es de la PrimaryKey
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
}
