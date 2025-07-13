package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Certificado;
import edu.utec.webpft.entidades.Itr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItrRepository extends JpaRepository<Itr, Long> {
    List<Itr> findByAnuladoFalseOrAnuladoIsNull();
}
