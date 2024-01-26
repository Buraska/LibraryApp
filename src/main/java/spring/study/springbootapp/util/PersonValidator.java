package spring.study.springbootapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.services.PersonService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private PersonService service;

    public PersonValidator(@Autowired PersonService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        var personFromDb = service.findByUsername(person.getUsername());
        if (personFromDb.isEmpty()){
            return;
        }
        if (!personFromDb.get().getId().equals((person.getId()))){
            errors.rejectValue("username", "", "That username is already taken");
        }
    }
}
