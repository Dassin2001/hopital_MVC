package umi.fs.hopital.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import umi.fs.hopital.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
