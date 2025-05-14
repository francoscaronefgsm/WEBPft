package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Itr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItrRepository extends JpaRepository<Itr, Long> {
}
