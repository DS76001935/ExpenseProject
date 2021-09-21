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
import com.dao.labelDao.UserRowMapper;

@Repository
public class payeeDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
		
	public boolean createPayees(PayeeBean payee, LoginBean user) {
		int uId = user.getUserId();
		System.out.println("UserId from payees table => "+uId);
		
		if(uId == 1) {
			List<PayeeBean> list = jdbcTemplate.query("select payeeName from payees where payeeName = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),payee.getPayeeName());
			
			if(list.size() == 0){
				int result = jdbcTemplate.update("insert into payees(payeeName, userId) values (?, ?)",payee.getPayeeName(),uId);
				if(result == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else {
			List<PayeeBean> list1 = jdbcTemplate.query("select payeeName from payees where payeeName = ? and userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),payee.getPayeeName(),user.getUserId());
			if(list1.size() == 0) {
				int result = jdbcTemplate.update("insert into payees(payeeName, userId) values (?, ?)",payee.getPayeeName(),uId);
				if(result == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
	}
	
	public List<PayeeBean> getAllPayees(LoginBean loginuser){
		int userId = loginuser.getUserId();
		List<PayeeBean> list = jdbcTemplate.query("select * from payees where userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),userId);
		return list;
	}
	public List<PayeeBean> getAllUserPayees(){
		List<PayeeBean> list = jdbcTemplate.query("select p.payeeId, p.payeeName, u.firstName from payees p inner join users u on p.userId = u.userId", new UserRowMapper());
		return list;
	}
	
	class UserRowMapper implements RowMapper<PayeeBean>{

		public PayeeBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			PayeeBean pBean = new PayeeBean();
			LoginBean user = new LoginBean();
			pBean.setPayeeId(rs.getInt(1));
			pBean.setPayeeName(rs.getString(2));
			user.setFirstName(rs.getString(3));
			pBean.setUser(user);
			return pBean;
		}
		
	}

	public PayeeBean getAllPayesById(int payeeId) {
		return  jdbcTemplate.queryForObject("select * from payees where payeeId = ?",new Object[]{payeeId},new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class));
	}

	public boolean updatePayees(PayeeBean payee) {
		int row = jdbcTemplate.update("update payees set payeeName = ? where payeeId = ?",payee.getPayeeName(),payee.getPayeeId());
		System.out.println("Update query fired for payee => "+row);
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean deletePayeeById(int payeeId, LoginBean user) {
		System.out.println("Payee Id before delete query => "+payeeId);
		List<Expense> list = jdbcTemplate.query("select * from expense where pId=? and userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),payeeId,user.getUserId());
		
//		System.out.println("Category Id from expense list when I tries to delete category by its id => "+list.get(0));
		
		if(list.size()>0) {
			
			int row1 = jdbcTemplate.update("update expense set pId=? where pId=? and userId=?",null,payeeId,user.getUserId());
			if(row1 == 1) {
				int row2 = jdbcTemplate.update("delete from payees where payeeId = ? and userId = ?",payeeId,user.getUserId());
				System.out.println("Payee Id after delete query => "+payeeId);
				System.out.println("Delete query fired for payee =>"+row2);
				if(row2 == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		else {
			int row = jdbcTemplate.update("delete from payees where payeeId = ? and userId = ?",payeeId,user.getUserId());
			System.out.println("Payee Id after delete query => "+payeeId);
			System.out.println("Delete query fired for payee =>"+row);
			if(row == 1) {
				return true;
			}else {
				return false;
			}
		}
	}
}
