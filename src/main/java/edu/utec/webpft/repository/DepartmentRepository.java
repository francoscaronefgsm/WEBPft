package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Departamento, Long> {
}
