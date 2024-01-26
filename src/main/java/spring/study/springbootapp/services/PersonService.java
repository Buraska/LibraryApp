package spring.study.springbootapp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.repositories.IPersonRepository;
import spring.study.springbootapp.repositories.IRoleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private IPersonRepository personRepository;
    private IRoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public PersonService(IPersonRepository personRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void register(Person person){
        person.setCreatedAt(new Date());
        person.getAuthorities().add(roleRepository.findByAuthority("ROLE_USER").get());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public boolean ifPersonCanHaveMoreBooks(String username){
        var person = personRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        return person.getBooksLimit() > person.getBooks().size();
    }


    public Page<Person> findPage(int pageNum){
        return  personRepository.findAll(PageRequest.of(pageNum, 10));
    }
    public Page<Person> findPage(int pageNum, String sortBy){
        if (sortBy == null){
            return findPage(pageNum);
        }
        return  personRepository.findAll(PageRequest.of(pageNum, 10, Sort.by(sortBy)));
    }

    public Person findOne(Long id){
        var person = personRepository.findById(id).orElse(null);
        if (person != null){
            Hibernate.initialize(person.getBooks());
        }
        return personRepository.findById(id).orElse(null);
    }

    public Person findOne(String name){
        return personRepository.findByName(name);
    }
    public Optional<Person> findByUsername(String username){
        return personRepository.findByUsername(username);
    }

    @Transactional
    public void update(Person person){
        var dbPerson = personRepository.findById(person.getId());
        if (dbPerson.isEmpty()){
            return;
        }

        dbPerson.get().setName(person.getName());
        dbPerson.get().setDateOfBirth(person.getDateOfBirth());
    }

    @Transactional
    public void delete(Long id){
        personRepository.deleteById(id);
    }
}
