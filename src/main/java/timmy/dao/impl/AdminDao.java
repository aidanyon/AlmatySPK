package timmy.dao.impl;

import timmy.dao.AbstractDao;
import timmy.entity.standart.Admin;
import timmy.utils.Const;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao extends AbstractDao<Admin> {

    public boolean isAdmin(long chatId) {
        sql = "SELECT count(*) FROM ADMIN WHERE USER_ID = ?";
        int count  = getJdbcTemplate().queryForObject(sql, setParam(chatId), Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean isMainAdmin(long chatId) {
        sql = "SELECT count(*) FROM ADMIN WHERE USER_ID = ?";
        int count  = getJdbcTemplate().queryForObject(sql, setParam(chatId), Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public List<Long> getAll() {
        sql = "SELECT USER_ID FROM ADMIN ORDER BY ID";
        return getJdbcTemplate().queryForList(sql, Long.class);
    }

    public void addAssistant(long chatId, String comment) {
        sql = "INSERT INTO ADMIN VALUES (DEFAULT, ?,?)";
        getJdbcTemplate().update(sql, chatId, comment);
    }

    public void delete(long chatId) {
        sql = "DELETE FROM ADMIN WHERE USER_ID = ?";
        getJdbcTemplate().update(sql, chatId);
    }

    @Override
    protected Admin mapper(ResultSet rs, int index) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt(1));
        admin.setUserId(rs.getLong(2));
        admin.setComment(rs.getString(3));
        return admin;
    }
}
