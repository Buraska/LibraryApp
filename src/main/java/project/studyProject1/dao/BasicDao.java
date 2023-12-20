package project.studyProject1.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import project.studyProject1.models.BasicEntity;

import java.util.*;

public abstract class BasicDao<T>{
    protected SessionFactory sessionFactory;

    public BasicDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    protected abstract Class<T> getClazz();
    protected abstract String getTableName();

    @Transactional
    public T save(T obj) {
        var session = sessionFactory.getCurrentSession();
        session.persist(obj);
        return obj;
    }

    @Transactional(readOnly = true)
    public Optional<T> show(Long id) {
        var session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(getClazz(), id));
    }

    @Transactional
    public List<T> showAll() {
        var session = sessionFactory.getCurrentSession();
        String sql = "select p from %s p".formatted(getTableName());

        return session.createQuery(sql ,getClazz()).getResultList();
    }


    @Transactional
    public void delete(Long id) {
        var session = sessionFactory.getCurrentSession();
        session.remove(session.get(getClazz(), id));
    }

    @Transactional
    public void update(T obj) {
        var session = sessionFactory.getCurrentSession();
        session.merge(obj);
    }


}

