package project.studyProject1.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.studyProject1.models.Book;
import project.studyProject1.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BooksDao extends BasicDao<Book>{
    public BooksDao(@Autowired SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Book> getClazz() {
        return Book.class;
    }

    @Override
    protected String getTableName() {
        return "Book";
    }


    @Transactional
    public void updateOwner(Long bookId, Person newOwner){
        var session = sessionFactory.getCurrentSession();
        if (newOwner != null){
            newOwner = session.get(Person.class, newOwner.getId());
        }
        var book = session.get(Book.class, bookId);
        book.setOwner(newOwner);
    }



}
