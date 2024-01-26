package spring.study.springbootapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.study.springbootapp.models.Book;

import java.awt.print.Pageable;
import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByBookNameContaining(String bookName, PageRequest pageRequest);
}
