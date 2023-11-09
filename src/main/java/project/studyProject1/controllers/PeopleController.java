package project.studyProject1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.studyProject1.dao.PersonDao;
import project.studyProject1.models.Person;
import project.studyProject1.util.PersonValidator;

@Controller
@RequestMapping("people")
public class PeopleController {

    private PersonValidator validator;
    private PersonDao dao;

    public PeopleController(PersonValidator validator, @Autowired PersonDao dao) {
        this.validator = validator;
        this.dao = dao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", dao.showAll());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        var person = dao.show(id);
        if (!person.isPresent()){
            throw new RuntimeException("People controller. Show. No such person");
        }


        model.addAttribute("person", person.get());
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "/people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

        validator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }

        dao.save(person);

        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){

        var person = dao.show(id);
        if (!person.isPresent()){
            throw new RuntimeException("People controller. Show. No such person");
        }

        model.addAttribute("person", person.get());
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String patchPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "/people/edit";
        }
        dao.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        dao.delete(id);
        return "redirect:/people";
    }

}
