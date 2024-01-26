package spring.study.springbootapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.study.springbootapp.models.Book;
import spring.study.springbootapp.models.Person;
import spring.study.springbootapp.repositories.IBookRepository;
import spring.study.springbootapp.repositories.IPersonRepository;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class BookService {
    private IBookRepository bookRepository;
    private IPersonRepository personRepository;

    @Autowired
    public BookService(IBookRepository bookRepository, IPersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    public Page<Book> findPage(int pageNum){
            return bookRepository.findAll(PageRequest.of(pageNum, 10));
    }

    public Page<Book> findPage(int pageNum, String sortBy){
        if (sortBy == null || sortBy.isEmpty()){
            return findPage(pageNum);
        }
        return  bookRepository.findAll(PageRequest.of(pageNum, 10, Sort.by(sortBy)));
    }

    public Page<Book> findPage(int pageNum, String sortBy, String query){

        if (query == null || query.isEmpty()){
            return findPage(pageNum, sortBy);
        }

        if (sortBy == null || sortBy.isEmpty()){
            return bookRepository.findByBookNameContaining(query, PageRequest.of(pageNum, 10));
        }
        return  bookRepository.findByBookNameContaining(query, PageRequest.of(pageNum, 10, Sort.by(sortBy)));
    }






    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findOne(Long id){
        return bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void update(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void updateBookOwner(Long bookId, Long ownerId){
        var book = bookRepository.findById(bookId).orElseThrow(IllegalArgumentException::new);

        if (book.getOwner() != null){
            book.getOwner().getBooks().remove(book);
        }

        if (ownerId == null){
            book.setOwner(null);
            book.setWasTakenAt(null);
        } else {
            var owner = personRepository.findById(ownerId).orElseThrow(IllegalArgumentException::new);
            book.setWasTakenAt(Calendar.getInstance().getTime());
            book.setOwner(owner);
            owner.getBooks().add(book);
        }

        bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
