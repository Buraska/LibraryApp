package spring.study.springbootapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.study.springbootapp.models.Person;

import java.util.Collection;
import java.util.HashSet;

public class PersonalDetails implements UserDetails {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public PersonalDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (person.getAuthorities() == null){
            person.setAuthorities(new HashSet<>());
        }
        return person.getAuthorities();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
