package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.AccountBean;
import com.bean.AccountType;
import com.bean.CategoryBean;
import com.bean.Expense;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.bean.SubcategoryBean;
import com.bean.UserBean;
import com.dao.AccountDao;
import com.dao.CategoryDao;
import com.dao.ExpenseDao;
import com.dao.UserDao;
import com.dao.labelDao;
import com.dao.payeeDao;

@Controller
public class ExpenseController {
	
	@Autowired
	AccountDao acc;
	
	@Autowired
	CategoryDao cDao;
	
	@Autowired
	labelDao lDao;
	
	@Autowired
	payeeDao pDao;
	
	@Autowired
	ExpenseDao eDao;
	
	@Autowired
	UserDao dao;
	
	@PostMapping("/ExpenceTracking")
	public String ExpenseTracking(@Valid @ModelAttribute("validate8") Expense ex, BindingResult result, Model model, CategoryBean category, SubcategoryBean subCategory,LabelBean label,PayeeBean payee,AccountBean account, HttpSession session,String message) {
		if(result.hasErrors()) {
			LoginBean user = (LoginBean) session.getAttribute("user");
			model.addAttribute("expense", ex);
			
			List<AccountType> list = acc.getAccTypes();
			model.addAttribute("getAccTypes", list);
			
			List<LabelBean> list4 = lDao.getAllLabels(user);
			model.addAttribute("getAllLabels", list4);
			
			List<PayeeBean> list3 = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list3);
			
			List<AccountBean> list5 = acc.getAccounts(user);
			model.addAttribute("getAccounts", list5);
			
			List<CategoryBean> list2 = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list2);
			
			return "ExpenseCreation";
		}else {
			System.out.println("Expense Method Called...");
			
			LoginBean user = (LoginBean) session.getAttribute("user");
			model.addAttribute("expense", ex);
			
			List<AccountType> list = acc.getAccTypes();
			model.addAttribute("getAccTypes", list);
			
			List<LabelBean> list4 = lDao.getAllLabels(user);
			model.addAttribute("getAllLabels", list4);
			
			List<PayeeBean> list3 = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list3);
			
			List<AccountBean> list5 = acc.getAccounts(user);
			model.addAttribute("getAccounts", list5);
			
			List<CategoryBean> list2 = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list2);
			
			System.out.println("Account name:"+ex.getAccName());
			System.out.println("Payment Method:"+ex.getPaymentMethod());
			System.out.println("Category Name:"+category.getCategoryName());
			System.out.println("Payee Name:"+payee.getPayeeName());
			System.out.println("Label Name:"+label.getLabelName());
			
			boolean flag = false;
			try {
				flag = eDao.createExpense(ex,category,subCategory,payee,label,user,account);
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(flag) {
				message = "Your expense has added successfully...";
				model.addAttribute("EMessage", message);
			}else {
				message = "Sorry try again later!";
				model.addAttribute("EMessage", message);
			}
			return "ExpenseCreation";
		}
	}
	
	@GetMapping("/PDFGeneration")
	public String PDFGeneration(Model model, HttpSession session,UserBean uBean) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		int uId = user.getUserId();
		uBean = dao.getDataById(uId);
		model.addAttribute("uBean", uBean);
		List<Expense> list1 = eDao.getExpensesByMonth(user);
		List<Expense> list2 = eDao.getExpensesByYear(user);
		model.addAttribute("getExpensesByMonth", list1);
		model.addAttribute("getExpensesByYear", list2);
		return "PDFGeneration";
	}
	@GetMapping("/moveToReportGeneration")
	public String MoveToReportGeneration() {
		return "PDFGeneration";
	}

	@GetMapping("/manageExpenses")  
	public String ManageExpenses(Model model,HttpSession session,String message,Expense ex,CategoryBean category,LabelBean label,PayeeBean payee){
		LoginBean user = (LoginBean) session.getAttribute("user");
		
		List<Expense> list = eDao.getAllExpenses(user);
		model.addAttribute("getAllExpenses", list);
		
//		List<Expense> list2 = eDao.getCategoryFromExpense(user);
//		System.out.println("Expense List With Category"+list2);
//		model.addAttribute("getCategoryFromExpense", list2);
//		expenseList.add(list2);
//		
//		List<Expense> list3 = eDao.getPayeeFromExpense(user);
//		System.out.println("Expense List With Payee"+list3);
//		/* model.addAttribute("getPayeeFromExpense", list3); */
//		expenseList.add(list3);
//		
//		List<Expense> list4 = eDao.getLabelFromExpense(user);
//		System.out.println("Expense List With Label"+list4);
		/* model.addAttribute("getLabelFromExpense", list4); */
		/* model.addAttribute("ExpenseList", expenseList); */
		
		
		return "ExpenseTracking";
	}
	@GetMapping("/viewUserExpenses")
	public String ViewUserExpenses(Model model) {
		List<Expense> list = eDao.getAllUserExpenses();
		System.out.println("Expense User List"+list);
		model.addAttribute("getAllUserExpenses", list);
		return "ViewUserExpenses";
	}
	
	@GetMapping("/deleteExpense/{expenseId}")
	public String DeleteExpense(@PathVariable("expenseId") int expenseId, Model model, HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		
		Expense ex = eDao.getAllExpensesById(user,expenseId);
		if(ex != null) {
			long ex_amt = ex.getAmount();
			boolean flag = eDao.deleteExpenseById(expenseId,ex_amt);
			if(flag) {
				String message = "Your expense has eliminated successfully...";
				model.addAttribute("message", message);
				List<Expense> list = eDao.getAllExpenses(user);
				model.addAttribute("getAllExpenses", list);
				return "ExpenseTracking";
			}else {
				String message = "Something went wrong!";
				model.addAttribute("message", message);
				List<Expense> list = eDao.getAllExpenses(user);
				model.addAttribute("getAllExpenses", list);
				return "ExpenseTracking";
			}
		}else {
			String message = "Invalid Action!";
			model.addAttribute("message", message);
			return "ExpenseTracking";
		}
	}
	@GetMapping("/editExpense/{expenseId}")
	public String EditExpense(@PathVariable("expenseId") int expenseId,Model model,HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		Expense ex = eDao.getAllExpensesById(user,expenseId);
		
		if(ex != null) {
			session.setAttribute("currExAmount", ex.getAmount());
			model.addAttribute("expense", ex);
			List<LabelBean> list1 = lDao.getAllLabels(user);
			List<PayeeBean> list2 = pDao.getAllPayees(user);
			List<CategoryBean> list3 = cDao.getAllCategories(user);
			
			model.addAttribute("getAllCategories", list3);
			model.addAttribute("getAllPayees", list2);
			model.addAttribute("getAllLabels", list1);
			return "UpdateExpense";
		}else {
			String message = "Something went wrong!";
			model.addAttribute("message", message);
			return "ExpenseTracking";
		}
	}
	@GetMapping("/modifyExpense")
	public String ModifyExpense(Expense ex,Model model, HttpSession session,CategoryBean category, SubcategoryBean subCategory, LabelBean label, PayeeBean payee,AccountBean account) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		long currExAmount = (Long)session.getAttribute("currExAmount");
		long updated_exAmount = ex.getAmount();
		System.out.println("Expense Amount before expense updation => "+currExAmount);
		System.out.println("Updated Expense Amount => "+updated_exAmount);
		
		List<LabelBean> list1 = lDao.getAllLabels(user);
		List<PayeeBean> list2 = pDao.getAllPayees(user);
		List<CategoryBean> list3 = cDao.getAllCategories(user);
		
		model.addAttribute("getAllCategories", list3);
		model.addAttribute("getAllPayees", list2);
		model.addAttribute("getAllLabels", list1);
		System.out.println("Category Object =>"+category);
		System.out.println("Payee Object =>"+payee);
		System.out.println("Label Object =>"+label);
		
		if(currExAmount>updated_exAmount) {
			currExAmount = currExAmount-updated_exAmount;
			boolean flag = eDao.updateExpense1(ex,currExAmount,category,subCategory,label,payee,account,user);
			if(flag) {
				String message = "Your Expense has updated successfully...";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
//				List<Expense> list = eDao.getAllExpenses(user);
//				model.addAttribute("getAllExpenses", list);
				return "UpdateExpense";
			}else {
				String message = "Something went wrong!";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
				return "UpdateExpense";
			}
		}else if(currExAmount<updated_exAmount){
			currExAmount = updated_exAmount-currExAmount;
			boolean flag = eDao.updateExpense2(ex,currExAmount,category,subCategory,label,payee,account,user);
			if(flag) {
				String message = "Your Expense has updated successfully...";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
//				List<Expense> list = eDao.getAllExpenses(user);
//				model.addAttribute("getAllExpenses", list);
				return "UpdateExpense";
			}else {
				String message = "Something went wrong!";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
				return "UpdateExpense";
			}
		}else {
			boolean flag = eDao.updateExpense3(ex,currExAmount,category,subCategory,label,payee,account,user);
			if(flag) {
				String message = "Your Expense has updated successfully...";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
//				List<Expense> list = eDao.getAllExpenses(user);
//				model.addAttribute("getAllExpenses", list);
				return "UpdateExpense";
			}else {
				String message = "Something went wrong!";
				model.addAttribute("message", message);
				model.addAttribute("getAllCategories", list3);
				model.addAttribute("getAllPayees", list2);
				model.addAttribute("getAllLabels", list1);
				return "UpdateExpense";
			}
		}
	}
	
	@GetMapping("/check/{categoryName}")
	@ResponseBody
	public List<SubcategoryBean> CheckCategory(@PathVariable("categoryName") String categoryName,CategoryBean category,SubcategoryBean subCategory,HttpSession session,Model model) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		List<SubcategoryBean> subCategories = cDao.trackCategory(categoryName,category,user,subCategory);
		if(subCategories.size() != 0) {
			System.out.println(subCategories);
			model.addAttribute("subCategories", subCategories);
			return subCategories;
		}else {
			return subCategories;
		}
	}
	@GetMapping("/checkAccName/{accName}")
	@ResponseBody
	public AccountType getAccType(@PathVariable("accName") String accName, AccountType accType, AccountBean account,Model model,HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		accType = acc.getAccountTypeFromAccountName(accName,account,accType,user);
		if(accType != null) {
			
			System.out.println(accType);
			model.addAttribute("AccountType", accType);
			return accType;
		}else {
			return accType;
		}
	}
	
}
