package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.EmailBean;
import com.bean.ForgotUserBean;
import com.bean.LoginBean;
import com.bean.RoleBean;
import com.bean.UserBean;

@Repository
public class UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	ArrayList<LoginBean> list = new ArrayList<LoginBean>();
	
	public LoginBean login(String email, String password) {
		
	 List<LoginBean> bean=  jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<LoginBean>(LoginBean.class));
	 for (LoginBean userBean : bean) {
		if(userBean.getEmail().equals(email) && userBean.getPassword().equals(password)) {
			return userBean;
		}
	 }
		return null;
	}

	public List<UserBean> showData() {
		List<UserBean> users = jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<UserBean>(UserBean.class));
		return users;
	}
	
	public int delete(int id) {
		
		int row = jdbcTemplate.update("delete from users where userId=?",id);
		System.out.println(row);
		return row;
	}
	
	public UserBean getDataById(int id) {
//		=  jdbcTemplate.update("select * from user where id=?",id);
		return jdbcTemplate.queryForObject("select u.userId,u.firstName,u.phone,u.gender,u.email,u.flag,r.roleName from users u inner join roles r on r.roleId = u.roleId where userId =?",new Object[] {id},new UserRowMapper());
	}
	
	class UserRowMapper implements RowMapper<UserBean>{

		public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserBean user = new UserBean();
			RoleBean role = new RoleBean();
			user.setUserId(rs.getInt(1));
			user.setFirstName(rs.getString(2));
			user.setPhone(rs.getString(3));
			user.setGender(rs.getString(4));
			user.setEmail(rs.getString(5));
			user.setFlag(rs.getBoolean(6));
			role.setRoleName(rs.getString(7));
			user.setRole(role);
			return user;
		}
	}
	
	public boolean updateUser(UserBean user) {
		int i= jdbcTemplate.update("update users set flag=? where userId=?", user.getFlag(),user.getUserId());
		if(i == 1) {
			return true;
		}else {
			return false;
		}
	} 
	
	public void insert(UserBean user) {
		System.out.println("Role Id from users table => "+user.getRole().getRoleId());
		jdbcTemplate.update("insert into users(firstName,phone,gender,email,password,roleId,flag) values (?,?,?,?,?,?,?)",user.getFirstName(),user.getPhone(),user.getGender(),user.getEmail(),user.getPassword(),user.getRole().getRoleId(),user.getFlag());
	}
	public List<UserBean> checkExistingUserData(UserBean user){
		List<UserBean> list = jdbcTemplate.query("select * from users where email=? and password =?",  new BeanPropertyRowMapper<UserBean>(UserBean.class),user.getEmail(),user.getPassword());
		return list;
	}

	public boolean checkEmailDuplication(String email) {
		List<LoginBean> list = jdbcTemplate.query("select * from users,roles where email like ? and roles.roleId = users.roleId", new BeanPropertyRowMapper<LoginBean>(LoginBean.class),email);
		System.out.println("Email From userBean => "+list.size());
		
		if(list.size() == 0) {
			return false;
		}else {
			return true;
		}
		
	}
	public boolean checkPhoneDuplication(String phone) {
		List<LoginBean> list = jdbcTemplate.query("select * from users, roles where phone like ? and roles.roleId = users.roleId", new BeanPropertyRowMapper<LoginBean>(LoginBean.class),phone);
		System.out.println("Phone From userBean => "+list.size());
		
		if(list.size() == 0) {
			return false;
		}else {
			return true;
		}
	}

	public boolean updatePassword(ForgotUserBean fuser,String user_email) {
		
		int result = jdbcTemplate.update("update users set password = ? where email = ?",fuser.getPassword(),user_email);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
		
	}

	public boolean verifyEmail(String email) {
		
		System.out.println("Input-Email for forgot password => "+email);
		List<EmailBean> list = jdbcTemplate.query("select * from users where email = ?", new BeanPropertyRowMapper<EmailBean>(EmailBean.class),email);
		if(list.size()>0) {
			return true;
		}else {
			return false;
		}
	}
}
