package umi.fs.hopital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umi.fs.hopital.entities.Patient;

public interface PateintRepository extends JpaRepository<Patient,Long> {
    //pageable: pour transmetrre num page te size lors de la paginagtion
    Page<Patient> findByNomContains(String keyword, Pageable pageable);
}
