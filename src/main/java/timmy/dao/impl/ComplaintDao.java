package timmy.dao.impl;

import timmy.dao.AbstractDao;
import timmy.entity.custom.Complaint;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintDao extends AbstractDao<Complaint> {

    public  void             insert(Complaint complaint) {
        sql = "INSERT INTO COMPLAINT (FULL_NAME, PHONE, TEXT, EMAIL, COMPANY, DEPARTMENT) VALUES (?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, setParam(complaint.getFullName(), complaint.getContact(), complaint.getText(), complaint.getEmail(), complaint.getCompany(), complaint.getDepartment()));
    }


    protected Complaint mapper(ResultSet rs, int index) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt(1));
        complaint.setFullName(rs.getString(2));
        complaint.setContact(rs.getString(3));
        complaint.setText(rs.getString(4));
        complaint.setEmail((rs.getString(5)));
        complaint.setCompany(rs.getString(6));
        complaint.setDepartment(rs.getString(7));
        return complaint;
    }
}