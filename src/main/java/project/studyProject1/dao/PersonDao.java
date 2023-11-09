package project.studyProject1.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import project.studyProject1.models.Book;
import project.studyProject1.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class PersonDao extends BasicDao<Person>{


    public PersonDao(@Autowired JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
    public Optional<Person> show(Long id){
        var sql = "select * from PERSON where id = ?";
        return template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> show(String name) {
        var sql = "select * from PERSON where name = ?";
        return template.query(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }
    @Override
    public List<Person> showAll(){
        var sql = "select * from PERSON left join BOOK on PERSON.id = BOOK.person_id";
        var res =  new PersonRowHandler();
        template.query(sql, res);
        return res.getPeople();
    }



    @Override
    public void update(Person person){
        var sql = "update person set name = ?, birth_year = ? where id = ?";
        template.update(sql, person.getName(), person.getBirthYear(), person.getId());
    }


    private class PersonRowHandler implements RowCallbackHandler {
        private Map<Long, Person> personMap = new HashMap<>();

        public List<Person> getPeople() {
            return new ArrayList<>(personMap.values());
        }
        @Override
        public void processRow(ResultSet rs) throws SQLException {

            Long id = rs.getLong("id");
            if (!personMap.containsKey(id)){
                String pName = rs.getString("name");
                Integer birthYear = rs.getInt("birth_year");
                var person = new Person(id, pName, birthYear);
                personMap.put(person.getId(), person);
            }

            if (rs.getString("person_id") != null){
                var book = new Book();
                book.setId(rs.getLong("id"));
                book.setBookName(rs.getString("book_name"));
                book.setAuthorName(rs.getString("author_name"));
                book.setPublicationYear(rs.getInt("publication_year"));
                personMap.get(id).addBook(book);
            }
        }



    }
}
