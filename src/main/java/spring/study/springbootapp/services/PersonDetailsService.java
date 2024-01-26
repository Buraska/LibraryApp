package spring.study.springbootapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.study.springbootapp.repositories.IPersonRepository;
import spring.study.springbootapp.security.PersonalDetails;

@Service
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    private final IPersonRepository repository;

    public PersonDetailsService(IPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new PersonalDetails(person);
    }


}
