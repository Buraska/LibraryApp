package project.studyProject1.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.studyProject1.models.Book;
import project.studyProject1.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BooksDao extends BasicDao<Book>{
    public BooksDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected Class<Book> getClazz() {
        return Book.class;
    }

    @Override
    protected String getTableName() {
        return "Book";
    }


    @Override
    public void update(Book book) {

        var sql = "update BOOK set BOOK_NAME = ?, AUTHOR_NAME = ?, PUBLICATION_YEAR = ?, PERSON_ID = ? where id = ?";
        template.update(sql, book.getBookName(), book.getAuthorName(), book.getPublicationYear(), book.getBookOwnerId(), book.getId());
    }

    public void updateOwner(Long bookId, Long bookOwnerId){

        var sql = "update BOOK set PERSON_ID = ? where id = ?";
        template.update(sql, bookOwnerId, bookId);
    }

    @Override
    public Optional<Book> show(Long id) {
        var sql = "select * from BOOK left join PERSON on PERSON.ID = BOOK.PERSON_ID where BOOK.id = ?";
        var handler = new BookRowMapper();
        return template.query(sql, handler, id).stream().findAny();

    }

    private class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var book = new Book();

            book.setId(rs.getLong("BOOK.id"));
            book.setBookName(rs.getString("book_name"));
            book.setAuthorName(rs.getString("author_name"));
            book.setPublicationYear(rs.getInt("publication_year"));


            if (rs.getString("PERSON.name") != null) {
                var person = new Person();

                person.setId(rs.getLong("PERSON.ID"));
                person.setName(rs.getString("name"));
                person.setBirthYear(rs.getInt("birth_year"));

                book.setBookOwner(person);
            }
            return book;
        }
    }
}
