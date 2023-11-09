package project.studyProject1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import project.studyProject1.models.BasicEntity;
import java.util.*;

public abstract class BasicDao<T extends BasicEntity> {



    protected JdbcTemplate template;

    public BasicDao(@Autowired JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }


    protected abstract Class<T> getClazz();
    protected abstract String getTableName();
    public T save(T obj) {
        var data = new BeanPropertySqlParameterSource(obj);

        Number key = new SimpleJdbcInsert(template)
                .withTableName(getTableName())
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(data);
        obj.setId(key.longValue());

        return obj;
    }

    public Optional<T> show(Long id) {
        var sql = "select * from " + getTableName() + " where id = ?";
        return template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(getClazz())).stream().findAny();
    }


    public List<T> showAll() {
        var sql = "select * from " + getTableName();
        return template.query(sql, new BeanPropertyRowMapper<>(getClazz()));

    }


    public void delete(Long id) {
        var sql = "delete from " + getTableName() + " where id = ?";
        template.update(sql, id);
    }

    public void update(T obj) {
        throw new RuntimeException("Not implemented");
    }


}

