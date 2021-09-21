package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.bean.AccountBean;
import com.bean.CategoryBean;
import com.bean.Expense;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.bean.SubcategoryBean;

@Repository
public class ExpenseDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean createExpense(Expense ex, CategoryBean category, SubcategoryBean subCategory, PayeeBean payee, LabelBean label, LoginBean loginuser, AccountBean account) {
		
		category = jdbcTemplate.queryForObject("select categoryId from category where categoryName=? and userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),loginuser.getUserId());
		int cat_Id = category.getCategoryId();
		
		subCategory = jdbcTemplate.queryForObject("select subCatId from subCategory where subCatName = ? and userId = ?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class),subCategory.getSubCatName(),loginuser.getUserId());
		int subCat_Id = subCategory.getSubCatId();
		
		payee = jdbcTemplate.queryForObject("select payeeId from payees where payeeName = ? and userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class), payee.getPayeeName(),loginuser.getUserId());
		int p_Id = payee.getPayeeId();
		
		label = jdbcTemplate.queryForObject("select labelId from labels where labelName = ? and userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class), label.getLabelName(),loginuser.getUserId());
		int l_Id = label.getLabelId();
		
		account = jdbcTemplate.queryForObject("select accountBalance from account where accountName = ? and userId = ?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),ex.getAccName(),loginuser.getUserId());
		float acc_balance = account.getAccountBalance();
		
		int u_Id = loginuser.getUserId();
		
		System.out.println(ex.getExpDateTime());
		int result1 = jdbcTemplate.update("insert into expense (amount, ExpDateTime, status, description, accName, paymentMethod, catId, subCatId, pId, lId, userId) values (?,?,?,?,?,?,?,?,?,?,?)",ex.getAmount(),ex.getExpDateTime(),ex.getStatus(),ex.getDescription(),ex.getAccName(),ex.getPaymentMethod(),cat_Id,subCat_Id,p_Id,l_Id,u_Id);
		if(result1 == 1) {
			float e_amount = ex.getAmount();
			System.out.println("Account Balance => "+acc_balance+", Expense Amount => "+e_amount);
			acc_balance = acc_balance - e_amount;
			System.out.println("Account Balance => "+acc_balance+", Expense Amount => "+e_amount);
			int result2 = jdbcTemplate.update("update account set accountBalance = ? where accountName = ? and userId = ?",acc_balance,ex.getAccName(),u_Id);
			if(result2 == 1) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public List<Expense> getAllExpenses(LoginBean user){
		List<Expense> list = jdbcTemplate.query("select e.expenseId,e.paymentMethod,e.accName,e.amount,e.ExpDateTime,e.status,e.description,c.categoryId,c.categoryName,sub.subCatId,sub.subCatName,p.payeeId,p.payeeName,l.labelId,l.labelName  from expense as e inner join category as c on e.catId = c.categoryId inner join subCategory sub on e.subCatId = sub.subCatId inner join payees as p on e.pId = p.payeeId inner join labels as l on e.lId = l.labelId where e.userId=?", new UserRowMapper(),user.getUserId());
		if(list.size() > 0) {
			System.out.println("Expense List Size : "+list.size());
			System.out.println("Expense List = "+list);
			return list;
		}else {
			System.out.println("Expense List Size : "+list.size());
			System.out.println("Expense List = "+list);
			return list;
		}
	}
	public List<Expense> getAllUserExpenses(){
		List<Expense> list = jdbcTemplate.query("select e.expenseId,e.paymentMethod,e.accName,e.amount,e.ExpDateTime,e.status,e.description,c.categoryId,c.categoryName,sub.subCatId,sub.subCatName,p.payeeId,p.payeeName,l.labelId,l.labelName  from expense as e inner join category as c on e.catId = c.categoryId inner join subCategory sub on e.subCatId = sub.subCatId inner join payees as p on e.pId = p.payeeId inner join labels as l on e.lId = l.labelId", new UserRowMapper());
		System.out.println("Expense List Size : "+list.size());
		System.out.println("Expense List = "+list);
		return list;
	}
	
	public Expense getAllExpensesById(LoginBean user, int expenseId){
		Expense ex = jdbcTemplate.queryForObject("select e.expenseId,e.paymentMethod,e.accName,e.amount,e.ExpDateTime,e.status,e.description,c.categoryId,c.categoryName,sub.subCatId,sub.subCatName,p.payeeId,p.payeeName,l.labelId,l.labelName from expense as e inner join category as c on e.catId = c.categoryId inner join subCategory sub on e.subCatId = sub.subCatId inner join payees as p on e.pId = p.payeeId inner join labels as l on e.lId = l.labelId where e.expenseId = ?", new UserRowMapper(),expenseId);
		return ex;
	}
	
	class UserRowMapper implements RowMapper<Expense>{

		public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Expense ex = new Expense();
			CategoryBean category = new CategoryBean();
			SubcategoryBean subCat = new SubcategoryBean();
			PayeeBean payee = new PayeeBean();
			LabelBean label = new LabelBean();
			
			ex.setExpenseId(rs.getInt(1));
			ex.setPaymentMethod(rs.getString(2));
			ex.setAccName(rs.getString(3));
			ex.setAmount(rs.getLong(4));
			ex.setExpDateTime(rs.getDate(5));
			ex.setStatus(rs.getString(6));
			ex.setDescription(rs.getString(7));
			category.setCategoryId(rs.getInt(8));
			category.setCategoryName(rs.getString(9));
			ex.setCategory(category);
			subCat.setSubCatId(rs.getInt(10));
			subCat.setSubCatName(rs.getString(11));
			ex.setSubCategory(subCat);
			payee.setPayeeId(rs.getInt(12));
			payee.setPayeeName(rs.getString(13));
			ex.setPayee(payee);
			label.setLabelId(rs.getInt(14));
			label.setLabelName(rs.getString(15));
			ex.setLabel(label);
			
			return ex;
		}
	}
	
	public List<Expense> getExpensesByMonth(LoginBean user){
//		List<Expense> ex = jdbcTemplate.query("select ExpDateTime, amount from expense where userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),user.getUserId());
		List<Expense> list = jdbcTemplate.query("SELECT DATEPART(MONTH, ExpDateTime) as months,sum(amount) as amount from expense where userId=? group by DATEPART(MONTH, ExpDateTime),DATEPART(YEAR, ExpDateTime)",new BeanPropertyRowMapper<Expense>(Expense.class),user.getUserId());
		System.out.println("no of months along with expense amount => "+list.size());
		if(list.size() != 0) {
			System.out.println(list);
			return list;
		}else {
			System.out.println(list);
			return null;
		}
	}
	public List<Expense> getExpensesByYear(LoginBean user){
//		List<Expense> ex = jdbcTemplate.query("select ExpDateTime, amount from expense where userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),user.getUserId());
		List<Expense> list = jdbcTemplate.query("SELECT DATEPART(YEAR, ExpDateTime) as years,sum(amount) as amount from expense where userId=? group by DATEPART(YEAR, ExpDateTime)",new BeanPropertyRowMapper<Expense>(Expense.class),user.getUserId());
		System.out.println("no of years along with expense amount => "+list.size());
		if(list.size() != 0) {
			System.out.println(list);
			return list;
		}else {
			System.out.println(list);
			return null;
		}
	}
//	public list<Expense> getAmountByYear(LoginBean user){
//		
//	}
//	
//	public list<Expense> getAmountByMonth(LoginBean user){
//		
//	}

	public boolean deleteExpenseById(int expenseId, long ex_amt) {
		
		AccountBean account = jdbcTemplate.queryForObject("select acc.accountId,acc.accountBalance from account acc inner join expense e on acc.accountName =e.accountName where e.expenseId = ?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),expenseId);
		long acc_balance = account.getAccountBalance();
		System.out.println("Account balance and expense amount before calculation =>"+acc_balance+" - "+ex_amt);
		
		
		acc_balance = acc_balance + ex_amt;
		
		System.out.println("Account balance and expense amount after calculation =>"+acc_balance+" - "+ex_amt);
		
		int result = jdbcTemplate.update("delete from expense where expenseId = ?", expenseId);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
		
	}

	public boolean updateExpense1(Expense ex, long currExAmount,CategoryBean category, SubcategoryBean subCategory, LabelBean label,PayeeBean payee,AccountBean account,LoginBean user) {
		
		category = jdbcTemplate.queryForObject("select categoryId from category where categoryName = ? and userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),user.getUserId());
		int cId = category.getCategoryId();
		subCategory = jdbcTemplate.queryForObject("select subCatId from subCategory where subCatName = ? and userId = ?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class),subCategory.getSubCatName(),user.getUserId());
		int subCatId = subCategory.getSubCatId();
		payee = jdbcTemplate.queryForObject("select payeeId from payees where payeeName = ? and userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),payee.getPayeeName(),user.getUserId());
		int pId = payee.getPayeeId();
		label = jdbcTemplate.queryForObject("select labelId from labels where labelName = ? and userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class),label.getLabelName(),user.getUserId());
		int lId = label.getLabelId();
		
		account = jdbcTemplate.queryForObject("select acc.accountId,acc.accountBalance from account acc inner join expense e on acc.accountName =e.accountName where e.expenseId = ?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),ex.getExpenseId());
		
		long acc_balance = account.getAccountBalance();
		acc_balance = acc_balance+currExAmount;
		
		int result = jdbcTemplate.update("update account set accountBalance=? where accountId=?",acc_balance,account.getAccountId());
		if(result == 1) {
			int result1 = jdbcTemplate.update("update expense set amount=?, ExpDateTime=?, status=?, description=?, catId=?, subCatId=?, pId=?, lId=? where expenseId=?",ex.getAmount(),ex.getExpDateTime(),ex.getStatus(),ex.getDescription(),cId,subCatId,pId,lId,ex.getExpenseId());
			if(result1 == 1) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	public boolean updateExpense2(Expense ex, long currExAmount,CategoryBean category, SubcategoryBean subCategory, LabelBean label,PayeeBean payee,AccountBean account,LoginBean user) {
		
		category = jdbcTemplate.queryForObject("select categoryId from category where categoryName = ? and userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),user.getUserId());
		int cId = category.getCategoryId();
		subCategory = jdbcTemplate.queryForObject("select subCatId from subCategory where subCatName = ? and userId = ?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class),subCategory.getSubCatName(),user.getUserId());
		int subCatId = subCategory.getSubCatId();
		payee = jdbcTemplate.queryForObject("select payeeId from payees where payeeName = ? and userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),payee.getPayeeName(),user.getUserId());
		int pId = payee.getPayeeId();
		label = jdbcTemplate.queryForObject("select labelId from labels where labelName = ? and userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class),label.getLabelName(),user.getUserId());
		int lId = label.getLabelId();
		
		account = jdbcTemplate.queryForObject("select acc.accountId,acc.accountBalance from account acc inner join expense e on acc.accountName =e.accountName where e.expenseId = ?", new BeanPropertyRowMapper<AccountBean>(AccountBean.class),ex.getExpenseId());
		
		long acc_balance = account.getAccountBalance();
		acc_balance = acc_balance-currExAmount;
		
		int result = jdbcTemplate.update("update account set accountBalance=? where accountId=?",acc_balance,account.getAccountId());
		if(result == 1) {
			int result1 = jdbcTemplate.update("update expense set amount=?, ExpDateTime=?, status=?, description=?, catId=?, subCatId=?, pId=?, lId=? where expenseId=?",ex.getAmount(),ex.getExpDateTime(),ex.getStatus(),ex.getDescription(),cId,subCatId,pId,lId,ex.getExpenseId());
			if(result1 == 1) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	public boolean updateExpense3(Expense ex, long currExAmount,CategoryBean category, SubcategoryBean subCategory, LabelBean label,PayeeBean payee,AccountBean account,LoginBean user) {
		
		category = jdbcTemplate.queryForObject("select categoryId from category where categoryName = ? and userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),user.getUserId());
		int cId = category.getCategoryId();
		subCategory = jdbcTemplate.queryForObject("select subCatId from subCategory where subCatName = ? and userId = ?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class),subCategory.getSubCatName(),user.getUserId());
		int subCatId = subCategory.getSubCatId();
		payee = jdbcTemplate.queryForObject("select payeeId from payees where payeeName = ? and userId = ?", new BeanPropertyRowMapper<PayeeBean>(PayeeBean.class),payee.getPayeeName(),user.getUserId());
		int pId = payee.getPayeeId();
		label = jdbcTemplate.queryForObject("select labelId from labels where labelName = ? and userId = ?", new BeanPropertyRowMapper<LabelBean>(LabelBean.class),label.getLabelName(),user.getUserId());
		int lId = label.getLabelId();
		
		System.out.println("CID => "+cId + "PID => "+pId + "LID => "+lId);
		
		int result1 = jdbcTemplate.update("update expense set amount=?, ExpDateTime=?, status=?, description=?, catId=?, subCatId=?, pId=?, lId=? where expenseId=?",ex.getAmount(),ex.getExpDateTime(),ex.getStatus(),ex.getDescription(),cId,subCatId,pId,lId,ex.getExpenseId());
		if(result1 == 1) {
			return true;
		}else {
			return false;
		}
	}
}
