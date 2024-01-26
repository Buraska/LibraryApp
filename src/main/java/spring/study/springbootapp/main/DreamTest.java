package spring.study.springbootapp.main;//package project.studyProject1.main;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import project.studyProject1.configs.SpringConfig;
//import project.studyProject1.dao.BooksDao;
//import project.studyProject1.dao.PersonDao;
//import project.studyProject1.models.Book;
//import project.studyProject1.models.Person;
//
//public class DreamTest {
///** Remove @EnableMVC annotation in SpringConfig class to start testing. **/
//
//    public static void main(String[] args) {
//        var dao =  new AnnotationConfigApplicationContext(SpringConfig.class).getBean(PersonDao.class);
//
//
//        System.out.println(dao.showAll());
//        System.out.println(dao.show("John Doe"));
//        System.out.println(dao.show("John Doe").get().getBooks());
//    }
//
//
//    @Test
//    public void PersonDaoBasicFunctionality(){
//        PersonDao dao =  new AnnotationConfigApplicationContext(SpringConfig.class).getBean(PersonDao.class);
//
//        Person person = dao.save( new Person("Viktor", 12));
//
//        Assert.assertEquals(1, dao.showAll().size());
//        Assert.assertEquals(dao.show(person.getId()).get(), person);
//
//        person.setName("Viktoria");
//        dao.update(person);
//
//        Assert.assertEquals((dao.show(person.getId()).get().getName()), "Viktoria");
//
//        dao.delete(person.getId());
//
//        Assert.assertEquals(0, dao.showAll().size());
//    }
//
//    @Test
//    public void BookDaoBasicFunctionality(){
//        BooksDao dao =  new AnnotationConfigApplicationContext(SpringConfig.class).getBean(BooksDao.class);
//
//        var book = dao.save( new Book("Kniga", "Avtor", 1999));
//
//        Assert.assertEquals(1, dao.showAll().size());
//        Assert.assertEquals(dao.show(book.getId()).get(), book);
//
//        book.setBookName("Raamat");
//        dao.update(book);
//
//        Assert.assertEquals((dao.show(book.getId()).get().getBookName()), "Raamat");
//
//        dao.delete(book.getId());
//
//        Assert.assertEquals(0, dao.showAll().size());
//    }
//
//    @Test
//    public void BooksAdditional(){
//        BooksDao dao =  new AnnotationConfigApplicationContext(SpringConfig.class).getBean(BooksDao.class);
//
//
//        System.out.println(dao.show(3L).get());
//    }
//}
