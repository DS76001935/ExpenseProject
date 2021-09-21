package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.CategoryBean;
import com.bean.Expense;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.microsoft.sqlserver.jdbc.dataclassification.Label;

@Repository
public class labelDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
		
	public boolean createLabels(LabelBean label, LoginBean user) {
		
			List<LabelBean> list = jdbcTemplate.query("select labelName from labels where labelName = ? and userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class),label.getLabelName(),user.getUserId());
			if(list.size() == 0) {
				int result = jdbcTemplate.update("insert into labels(labelName, userId) values (?, ?)",label.getLabelName(),user.getUserId());
				if(result == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
	}
	public List<LabelBean> getAllLabels(LoginBean loginuser){
		List<LabelBean> list = jdbcTemplate.query("select * from labels where userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class),loginuser.getUserId());
		return list;
	}
	public List<LabelBean> getAllUserLabels(){
		List<LabelBean> list = jdbcTemplate.query("select l.labelId, l.labelName, u.firstName from labels l inner join users u on l.userId = u.userId", new UserRowMapper());
		return list;
	}
	
	class UserRowMapper implements RowMapper<LabelBean>{

		public LabelBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			LabelBean lBean = new LabelBean();
			LoginBean user = new LoginBean();
			lBean.setLabelId(rs.getInt(1));
			lBean.setLabelName(rs.getString(2));
			user.setFirstName(rs.getString(3));
			lBean.setUser(user);
			return lBean;
		}
	}

	public boolean deleteLabel(int labelId, LoginBean user) {
		System.out.println("Label Id before delete query => "+labelId);
		List<Expense> list = jdbcTemplate.query("select * from expense where lId=? and userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),labelId,user.getUserId());
		
		if(list.size() > 0) {
			
			int row1 = jdbcTemplate.update("update expense set lId=? where lId=? and userId=?", null, labelId, user.getUserId());
			if(row1 == 1) {
				int row2 = jdbcTemplate.update("delete from labels where labelId=? and userId=?",labelId,user.getUserId());
				if(row2 == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
			
		}else{
			int row = jdbcTemplate.update("delete from labels where labelId=? and userId=?",labelId,user.getUserId());
			if(row == 1) {
				return true;
			}else {
				return false;
			}
		}
	}
	public LabelBean getAllLabelsById(int labelId) {
		LabelBean label = jdbcTemplate.queryForObject("select * from labels where labelId=?", new Object[] {labelId},new BeanPropertyRowMapper<LabelBean>(LabelBean.class));
		return label;
	}
	public boolean updateLabel(LabelBean label, LoginBean user) {
		int row = jdbcTemplate.update("update labels set labelName = ? where labelId=?",label.getLabelName(),label.getLabelId());
		
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}

}
