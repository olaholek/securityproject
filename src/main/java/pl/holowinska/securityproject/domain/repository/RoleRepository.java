package pl.holowinska.securityproject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.model.User;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r from Role r where r.name = :roleName")
    Optional<Role> findRoleByName(String roleName);

    @Query(value = "select u from Role u where u.id = :roleId")
    Optional<Role> findRoleById(Long roleId);

    boolean existsByName(String name);
}
