package spring.study.springbootapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.models.SearchParams;
import spring.study.springbootapp.security.PersonalDetails;
import spring.study.springbootapp.services.PersonService;
import spring.study.springbootapp.util.PersonValidator;

import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("people")
@EnableMethodSecurity
public class PeopleController {

    private PersonValidator validator;
    private PersonService service;

    public PeopleController(PersonValidator validator, @Autowired PersonService service) {
        this.validator = validator;
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index(Model model, @ModelAttribute("searchParams") SearchParams searchParams, @RequestParam(name = "sort_by", required = false) String sortBy, @RequestParam(name = "page_num", required = false) Integer pageNum){
        if (pageNum == null){
            pageNum = 1;
        }
        var page = service.findPage(pageNum - 1);
        searchParams.setPageNum(pageNum);
        model.addAttribute("people", page.getContent());
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("pagesTotal", page.getTotalPages());
        return "/people/index";
    }

    @PreAuthorize("#id == authentication.principal.person.id || hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        var person = service.findOne(id);
        if (person == null){
            throw new RuntimeException("People controller. Show. No such person");
        }
        model.addAttribute("person", person);
        return "/people/show";
    }

    @GetMapping("/new") //TODO Depricated
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

        person.setCreatedAt(new Date());

        service.save(person);

        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("#id == authentication.principal.person.id || hasRole('ROLE_ADMIN')")
    public String editPerson(@PathVariable("id") Long id, Model model){

        var person = service.findOne(id);
        if (person == null){
            throw new RuntimeException("People controller. Show. No such person");
        }

        model.addAttribute("person", person);
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.person.id || hasRole('ROLE_ADMIN')")
    public String patchPerson(@PathVariable("id")Long id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        validator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "/people/edit";
        }
        service.update(person);


        if (isThatCurrentUserId(person.getId())){
            return "redirect:/people/"+ person.getId();
        }
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.person.id || hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") long id){
        service.delete(id);
        if (isThatCurrentUserId(id)){
            return "redirect:/auth/logout";
        }
        return "redirect:/people";
    }

    private boolean isThatCurrentUserId(long id){
        var curUserId = ((PersonalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();
        return curUserId.equals(id);
    }

}
