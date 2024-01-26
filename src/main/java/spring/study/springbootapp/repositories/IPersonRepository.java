package spring.study.springbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.study.springbootapp.models.Person;

import java.util.Optional;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {

    Person findByName(String name);

    Optional<Person> findByUsername(String username);
}
