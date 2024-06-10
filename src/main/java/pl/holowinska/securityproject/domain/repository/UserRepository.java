package pl.holowinska.securityproject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.holowinska.securityproject.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.username = :username")
    Optional<User> findUserByUsername(String username);

    @Query(value = "select u from User u where u.id = :userId")
    Optional<User> findUserById(Long userId);

    boolean existsByUsername(String username);
}
