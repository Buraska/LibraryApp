package project.studyProject1.dao;


import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.studyProject1.models.BasicEntity;
import project.studyProject1.models.Book;
import project.studyProject1.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class PersonDao extends BasicDao<Person>{

    public PersonDao(@Autowired SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class getClazz() {
        return Person.class;
    }

    @Override
    protected String getTableName() {
        return "Person";
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> show(Long id){
        var session = sessionFactory.getCurrentSession();
        var result = Optional.ofNullable(session.get(Person.class, id));
        result.ifPresent(person -> Hibernate.initialize(person.getBooks()));
        return Optional.ofNullable(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(String name) {
        var session = sessionFactory.getCurrentSession();
        var query = session.createQuery("select p from Person p left join fetch p.books where p.name = :name", Person.class);
        query.setParameter("name", name);

        return query.uniqueResultOptional();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Person> showAll(){
        var session = sessionFactory.getCurrentSession();
        return session.createQuery("select i from Person i", Person.class).getResultList();
    }

}
