package ar.com.billetera.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.billetera.api.entities.Billetera;

@Repository
public interface BilleteraRepository extends JpaRepository<Billetera, Integer> {
    
    Billetera findByBilleteraId(Integer id);
    
}
