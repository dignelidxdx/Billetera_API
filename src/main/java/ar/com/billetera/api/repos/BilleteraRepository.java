package ar.com.billetera.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.billetera.api.entities.Billetera;

public interface BilleteraRepository extends JpaRepository<Billetera, Integer> {
    
}
