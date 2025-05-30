package umi.fs.hopital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umi.fs.hopital.entities.Patient;

public interface PateintRepository extends JpaRepository<Patient,Long> {
}
