package timmy.dao.impl;

import timmy.dao.AbstractDao;
import timmy.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertiesDao extends AbstractDao<String> {

    public String getPropertiesValue(int id) {
        sql = "SELECT VALUE_1 FROM PROPERTIES WHERE ID = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(id), String.class);
    }

    @Override
    protected String mapper(ResultSet rs, int index) throws SQLException {
        return rs.getString(1);
    }
}