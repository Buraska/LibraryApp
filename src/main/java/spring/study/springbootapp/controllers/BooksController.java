package spring.study.springbootapp.controllers;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import spring.study.springbootapp.models.Book;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.models.SearchParams;
import spring.study.springbootapp.security.PersonalDetails;
import spring.study.springbootapp.services.BookService;
import spring.study.springbootapp.services.PersonService;

import java.awt.print.Pageable;

@Controller
@RequestMapping("books")
public class BooksController {

    private BookService bookService;
    private PersonService personService;

    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(@ModelAttribute("searchParams") SearchParams searchParams, @RequestParam(name = "page_num", required = false) Integer pageNum, @RequestParam(name = "query", required = false) String  query, @RequestParam(name = "sort_by", required = false) String sortBy, Model model){
        if (pageNum == null ){
            pageNum = 1;
        }
        searchParams.pageNum = pageNum;
//        if (query != null){
//            searchParams.setQuery(query);
//        }
//        if (sortBy != null){
//            searchParams.setSortBy(sortBy);
//        }
        searchParams.setQuery(query);
        searchParams.setSortBy(sortBy);
        searchParams.setPageNum(pageNum);

        Page<Book> page=bookService.findPage(pageNum - 1, searchParams.getSortBy(), searchParams.getQuery());


        model.addAttribute("pagesTotal", page.getTotalPages());
        model.addAttribute("books", page.getContent());
        model.addAttribute("searchParams", searchParams);
        return "/books/index";
    }

    @GetMapping("/{id}")

    public String show(@PathVariable("id") Long id, Model model){
        var book = bookService.findOne(id);

        var curUser= SecurityContextHolder.getContext().getAuthentication();
        if (curUser.isAuthenticated()){
            model.addAttribute("canTakeMore", personService.ifPersonCanHaveMoreBooks(curUser.getName()));
        }
        else {
            model.addAttribute("canTakeMore", false);
        };

        model.addAttribute("people", personService.findAll());
        model.addAttribute("book", book);
        return "/books/show";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/new";
        }

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBook(@PathVariable("id") long id, Model model){

        var book = bookService.findOne(id);
        if (book == null){
            throw new RuntimeException("Book controller.editBook() exception. No such book");
        }

        model.addAttribute("book", book);
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String patchBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/books/edit";
        }
        bookService.update(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/updateOwner")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String assignToOwner(@PathVariable("id") Long id, @RequestParam("id") Long ownerId){
        bookService.updateBookOwner(id, ownerId);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign_to_current_user")
    @PreAuthorize("isAuthenticated()")
    public String assignToCurrentUser(@PathVariable("id") Long id){
        var currentPerson = ((PersonalDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
        if (!personService.ifPersonCanHaveMoreBooks(currentPerson.getUsername())){
            return "/books/show";
        }
        bookService.updateBookOwner(id, currentPerson.getId());
        return "redirect:/people/"+currentPerson.getId();
    }

    @PatchMapping("/{id}/removeOwner")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeOwner(@PathVariable("id") Long id){
        bookService.updateBookOwner(id, null);
        return "redirect:/books/"+id;
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") long id){
        bookService.delete(id);
        return "redirect:/books";
    }

}
