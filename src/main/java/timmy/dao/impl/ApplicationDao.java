package timmy.dao.impl;

import timmy.dao.AbstractDao;
import timmy.entity.custom.Application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ApplicationDao extends AbstractDao<Application> {
    public void             insert(Application application) {
        sql = "INSERT INTO APPLICATION(FULL_NAME, PHONE_NUMBER, TEXT, COMMENT, EMAIL, COMPANY,  DEPARTMENT, LEVEL_ID) VALUES (?,?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, setParam(application.getFullName(), application.getPhoneNumber(), application.getComment(), application.getRequest(), application.getEmail(), application.getCompany(), application.getDepartment(), application.getLevelId()));
    }
    public List<Application> getAll1(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=1";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    public List<Application> getAll2(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=2";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    public List<Application> getAll3(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=3";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    public List<Application> getAll4(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=4";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    public List<Application> getAll5(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=5";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    public List<Application> getAll6(){
        sql = "SELECT * FROM APPLICATION WHERE LEVEL_ID=6";
        return getJdbcTemplate().query(sql, this::mapper);
    }

    @Override
    protected Application mapper(ResultSet rs, int index) throws SQLException {
        Application entity = new Application();
        entity.setId(rs.getInt(1));
        entity.setFullName(rs.getString(2));
        entity.setPhoneNumber(rs.getString(3));
        entity.setRequest(rs.getString(4));
        entity.setComment(rs.getString(5));
        entity.setEmail(rs.getString(6));
        entity.setCompany(rs.getString(7));
        entity.setDepartment(rs.getString(8));
        entity.setLevelId(rs.getInt(9));
        return entity;
    }
}