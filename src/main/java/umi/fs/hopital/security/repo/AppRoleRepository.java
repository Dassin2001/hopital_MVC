package umi.fs.hopital.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import umi.fs.hopital.security.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {

}
