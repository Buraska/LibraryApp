package spring.study.springbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.study.springbootapp.models.Role;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
