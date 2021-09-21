package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.print.attribute.standard.DateTimeAtCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.AccountBean;
import com.bean.AccountType;
import com.bean.CategoryBean;
import com.bean.Expense;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.dao.payeeDao.UserRowMapper;

@Repository
public class AccountDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean addAccType(AccountType acctype) {
		List<AccountType> list = jdbcTemplate.query("select * from AccountType where accTypeName = ?", new BeanPropertyRowMapper<AccountType>(AccountType.class),acctype.getAccTypeName());
		if(list.size() == 0) {
			int result = jdbcTemplate.update("insert into AccountType (accTypeName) values (?)",acctype.getAccTypeName());
			if(result == 1) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public List<AccountBean> checkAccCreation(LoginBean user)  {
		List<AccountBean> list = jdbcTemplate.query("select accountName from account where userId=?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),user.getUserId());
		return list;
	}

	public boolean createAccount(AccountBean account, int userId, String accTypeName) {
		
		Date date = new Date();
		account.setCreatedAt(date);
		List<AccountType> list1 = jdbcTemplate.query("select * from AccountType", new BeanPropertyRowMapper<AccountType>(AccountType.class));
		for(AccountType accType : list1) {
			if(accType.getAccTypeName().equalsIgnoreCase(accTypeName)) {
				account.setAccountType(accType);
			}
		}
		
		System.out.println("Date at account creation time => "+date);
		int result2 = jdbcTemplate.update("insert into account (accountName,accountBalance,userId,accTypeId,createdAt) values (?,?,?,?,?)",account.getAccountName(),account.getAccountBalance(),userId,account.getAccountType().getAccTypeId(),account.getCreatedAt());
		if(result2 == 1) {
			return true;
		}else {
			return false;
		}
	}
	public boolean createAccount1(AccountBean account, int userId, String accTypeName, String param_accountName) {
		Date date = new Date();
		account.setCreatedAt(date);
		System.out.println(param_accountName);
		List<AccountType> list1 = jdbcTemplate.query("select * from AccountType", new BeanPropertyRowMapper<AccountType>(AccountType.class));
		for(AccountType accType : list1) {
			if(accType.getAccTypeName().equalsIgnoreCase(accTypeName)) {
				account.setAccountType(accType);
			}
		}
		List<AccountBean> list2 = jdbcTemplate.query("select accountName from account where userId=?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),userId);
		
		System.out.println(list2);
		
		if(list2.size() == 0) {
			System.out.println("Date at account creation time => "+date);
			int result2 = jdbcTemplate.update("insert into account (accountName,accountBalance,userId,accTypeId,createdAt) values (?,?,?,?,?)",account.getAccountName(),account.getAccountBalance(),userId,account.getAccountType().getAccTypeId(),account.getCreatedAt());
			if(result2 == 1) {
				return true;
			}else {
				return false;
			}
		}else {
			for(int i=0;i<list2.size();i++) {
				System.out.println(list2.get(i)+" - "+param_accountName);
				if(!list2.get(i).getAccountName().equalsIgnoreCase(param_accountName)) {
					System.out.println(list2.get(i)+" - "+param_accountName);
					System.out.println("Date at account creation time => "+date);
					int result2 = jdbcTemplate.update("insert into account (accountName,accountBalance,userId,accTypeId,createdAt) values (?,?,?,?,?)",account.getAccountName(),account.getAccountBalance(),userId,account.getAccountType().getAccTypeId(),account.getCreatedAt());
					if(result2 == 1) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}
		}
		return false;
	}

	public List<AccountType> getAccTypes() {
		List<AccountType> list = jdbcTemplate.query("select * from AccountType", new BeanPropertyRowMapper<AccountType>(AccountType.class));
//		System.out.println("Account type list from account dao=> "+list);
		return list;
	}
	
	public List<AccountBean> getAccounts(LoginBean user) {
		List<AccountBean> list = jdbcTemplate.query("select acc.accountId,acc.accountName,acc.accountBalance,accType.accTypeName from account acc join AccountType accType on acc.accTypeId = accType.accTypeId where acc.userId=?", new UserRowMapper(),user.getUserId());
//		System.out.println("Account type list from account dao=> "+list);
		return list;
	}

	public List<AccountBean> geUsertAccounts(){
		List<AccountBean> list = jdbcTemplate.query("select acc.accountId,acc.accountName,acc.accountBalance,accType.accTypeName,u.firstName from account acc inner join users u on acc.userId = u.userId inner join AccountType accType on acc.accTypeId = accType.accTypeId", new UserRowMapper1());
		return list;
	}
	
	class UserRowMapper implements RowMapper<AccountBean>{
		
		public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountBean accBean = new AccountBean();
			AccountType accType = new AccountType();
			accBean.setAccountId(rs.getInt(1));
			accBean.setAccountName(rs.getString(2));
			accBean.setAccountBalance(rs.getLong(3));
			accType.setAccTypeName(rs.getString(4));
			return accBean;
		}	
	}
	
	class UserRowMapper1 implements RowMapper<AccountBean>{
		
		public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountBean accBean = new AccountBean();
			AccountType accType = new AccountType();
			LoginBean user = new LoginBean();
			accBean.setAccountId(rs.getInt(1));
			accBean.setAccountName(rs.getString(2));
			accBean.setAccountBalance(rs.getLong(3));
			accType.setAccTypeName(rs.getString(4));
			accBean.setAccountType(accType);
			user.setFirstName(rs.getString(5));
			accBean.setUser(user);
			return accBean;
		}	
	}

	public boolean updateAccount(AccountBean account, long input_amount) {
		
		account = jdbcTemplate.queryForObject("select * from account where accountId=?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),account.getAccountId());
		
		long acc_balance = account.getAccountBalance();
		System.out.println("Existing Account Balance:"+acc_balance);
		
		acc_balance = acc_balance + input_amount;
		
		System.out.println("Modified Account Balance :"+acc_balance);
		System.out.println(account.getAccountId());
		
		int row = jdbcTemplate.update("update account set accountBalance = ? where accountId = ?",acc_balance,account.getAccountId());
		System.out.println("Update query fired for account => "+row);
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean deleteAccountById(int accountId, LoginBean user) {
		System.out.println("Account Id before delete query => "+accountId);
		AccountBean account = jdbcTemplate.queryForObject("select accountName from account where accountId = ?",new Object[]{accountId},new BeanPropertyRowMapper<AccountBean>(AccountBean.class));
		List<Expense> list = jdbcTemplate.query("select * from expense where accName=? and userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),account.getAccountName(),user.getUserId());
		
//		System.out.println("Category Id from expense list when I tries to delete category by its id => "+list.get(0));
		
		if(list.size()>0) {
			
			int row1 = jdbcTemplate.update("delete from expense where accName=? and userId = ?",account.getAccountName(),user.getUserId());
			if(row1 == 1) {
				int row2 = jdbcTemplate.update("delete from account where accountId = ? and userId = ?",accountId,user.getUserId());
				System.out.println("Account Id after delete query => "+accountId);
				System.out.println("Delete query fired for account =>"+row2);
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
			int row = jdbcTemplate.update("delete from account where accountId = ? and userId = ?",accountId,user.getUserId());
			System.out.println("Account Id after delete query => "+accountId);
			System.out.println("Delete query fired for account =>"+row);
			if(row == 1) {
				return true;
			}else {
				return false;
			}
		}
	}

	public AccountBean getAllAccountsById(int accountId) {
		return  jdbcTemplate.queryForObject("select * from account where accountId = ?",new Object[]{accountId},new BeanPropertyRowMapper<AccountBean>(AccountBean.class));
	}

	public boolean updateAccount1(AccountBean account, long input_amount) {
		account = jdbcTemplate.queryForObject("select * from account where accountId=?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),account.getAccountId());
		
		long acc_balance = account.getAccountBalance();
		System.out.println("Existing Account Balance:"+acc_balance);
		
		acc_balance = acc_balance - input_amount;
		
		System.out.println("Modified Account Balance :"+acc_balance);
		System.out.println(account.getAccountId());
		
		int row = jdbcTemplate.update("update account set accountBalance = ? where accountId = ?",acc_balance,account.getAccountId());
		System.out.println("Update query fired for account => "+row);
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}

	public AccountType getAccountTypeFromAccountName(String accName, AccountBean account, AccountType accType, LoginBean user) {
		
		accType = jdbcTemplate.queryForObject("select accType.accTypeName from accountType accType inner join account acc on accType.accTypeId = acc.accTypeId where acc.accountName=? and acc.userId = ?", new BeanPropertyRowMapper<AccountType>(AccountType.class),accName,user.getUserId());
		return accType;
	}
}
