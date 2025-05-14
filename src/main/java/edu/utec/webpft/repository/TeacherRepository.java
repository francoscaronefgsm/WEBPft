package edu.utec.webpft.repository;

import edu.utec.webpft.entidades.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Tutor, Long> {

}