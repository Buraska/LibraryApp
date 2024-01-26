package spring.study.springbootapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.services.PersonService;
import spring.study.springbootapp.util.PersonValidator;

@Controller
@RequestMapping("auth")
public class AuthController {

    private final PersonService personService;
    private final PersonValidator validator;

    public AuthController(PersonService personService, PersonValidator validator) {
        this.personService = personService;
        this.validator = validator;
    }

    @GetMapping("login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("registration")
    public String registration(@ModelAttribute Person person){
        return "auth/registration";
    }

    @PostMapping("registration")
    public String performRegistration(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "auth/registration";
        }

        personService.register(person);
        return "auth/login";
    }
}
