package project.studyProject1.controllers;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.studyProject1.dao.BooksDao;
import project.studyProject1.dao.PersonDao;
import project.studyProject1.models.Book;

@Controller
@RequestMapping("books")
public class BooksController {

    private BooksDao dao;
    private PersonDao personDao;

    public BooksController(BooksDao dao, PersonDao personDao) {
        this.dao = dao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", dao.showAll());
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        var book = dao.show(id);
        if (!book.isPresent()){
            throw new RuntimeException("Book controller.show() exception. No such book");
        }

        Long ownerId = 0L;
        model.addAttribute("people", personDao.showAll());
        model.addAttribute("book", book.get());
        model.addAttribute("ownerId", ownerId);
        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/new";
        }

        dao.save(book);

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") long id, Model model){

        var book = dao.show(id);
        if (!book.isPresent()){
            throw new RuntimeException("Book controller.editBook() exception. No such book");
        }

        model.addAttribute("book", book.get());
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String patchBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/books/edit";
        }
        dao.update(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/updateOwner")
    public String updateOwner(@PathVariable("id") Long id, @ModelAttribute("book") Book book){
        dao.updateOwner(id, book.getBookOwnerId());
        return "redirect:/books/"+id;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        dao.delete(id);
        return "redirect:/books";
    }

}
