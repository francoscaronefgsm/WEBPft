package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificado, Long> {
}
