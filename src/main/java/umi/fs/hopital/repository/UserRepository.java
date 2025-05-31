package umi.fs.hopital.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import umi.fs.hopital.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}