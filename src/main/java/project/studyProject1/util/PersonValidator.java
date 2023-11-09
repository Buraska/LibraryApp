package project.studyProject1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.studyProject1.dao.PersonDao;
import project.studyProject1.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private PersonDao dao;

    public PersonValidator(@Autowired PersonDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromDb = dao.show(person.getName());
        if (personFromDb.isPresent() && !personFromDb.get().getId().equals((person.getId()))){
            errors.rejectValue("name", "", "That name is already taken");
        }
    }
}
