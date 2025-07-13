package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificado, Long> {
    List<Certificado> findByAnuladoFalseOrAnuladoIsNull();
}
