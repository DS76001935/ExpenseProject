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
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.AccountBean;
import com.bean.CategoryBean;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.dao.payeeDao;

@Controller
public class PayeeController {
	@Autowired
	payeeDao pDao;
	
	@PostMapping("/payeeInsertion")
	public String PayeeInsertion(@Valid @ModelAttribute("validate10") PayeeBean payee,BindingResult result,String message,Model model, HttpSession session) {
		if(result.hasErrors()) {
			message = "Invalid Input!";
			model.addAttribute("message", message);
			model.addAttribute("payee", payee);
			model.addAttribute("validate5", new AccountBean());
			model.addAttribute("validate6", new CategoryBean());
			model.addAttribute("validate9", new LabelBean());
			model.addAttribute("validate10", new PayeeBean());
			return "UserHome";
		}else {
			
			model.addAttribute("payee", payee);
			System.out.println("PayeeName = "+payee.getPayeeName());
			LoginBean user = (LoginBean) session.getAttribute("user");
			System.out.println("Session User ID From controller when insert the payee => "+user.getUserId());
			try {
				boolean flag = pDao.createPayees(payee,user);
				if(flag) {
					message = "One payee has inserted successfully...";
					model.addAttribute("message", message);
				}else {
					message = "Something is wrong...";
					model.addAttribute("message", message);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("validate5", new AccountBean());
			model.addAttribute("validate6", new CategoryBean());
			model.addAttribute("validate9", new LabelBean());
			model.addAttribute("validate10", new PayeeBean());
			return "UserHome";
		}
	}
	@GetMapping("/managePayees")
	public String ManageLabels(Model model, HttpSession session) {
		LoginBean user = (LoginBean)session.getAttribute("user");
		List<PayeeBean> list = pDao.getAllPayees(user);
		model.addAttribute("getAllPayees", list);
		
		return "ShowPayees";
	}
	
	@GetMapping("/addPayees")
	public String AddPayeesFromOutside(Model model) {
		model.addAttribute("validate10", new PayeeBean());
		return "addLabels";
	}
	
	@GetMapping("/viewUserPayees")
	public String viewUserLabels(Model model,HttpSession session) {
		List<PayeeBean> list = pDao.getAllUserPayees();
		model.addAttribute("getAllUserPayees", list);
		return "ViewUserPayees";
	}
	
	@RequestMapping("/editPayee/{payeeId}")
	public String EditCategory(@PathVariable("payeeId") int payeeId,Model model, HttpSession session) {
		System.out.println("Payee Id when edit button has clicked => "+payeeId);
		PayeeBean payee = pDao.getAllPayesById(payeeId);
		System.out.println("Payee id, name after getAllPayeesById() called => "+payee.getPayeeId()+payee.getPayeeName());
		model.addAttribute("payee", payee);
		return "UpdatePayee";
	}
	@GetMapping("/modifyPayee")
	public String ModifyPayee(PayeeBean payee, Model model,String message,HttpSession session) {
		System.out.println("Payee id and name from modifyPayee mapping =>"+payee.getPayeeId()+" - "+payee.getPayeeName());
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag= pDao.updatePayees(payee);
		System.out.println("Result after updatePayees() function called => "+flag);
		if(flag) {
			message = "Payee Updation Successfull...";
			System.out.println("flag true");
			model.addAttribute("message", message);
			System.out.println(message);
			List<PayeeBean> list = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list);
			return "ShowPayees";
		}else {
			System.out.println("flag false");
			message = "Payee Updation Unsuccessfull...";
			System.out.println(message);
			model.addAttribute("message", message);
			List<PayeeBean> list = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list);
			return "ShowPayees";
		}
	}
	@GetMapping("/deletePayee/{payeeId}")
	public String DeletePayee(@PathVariable("payeeId") int payeeId,Model model,String message,HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag = pDao.deletePayeeById(payeeId,user);
		if(flag) {
			message = "Payee Deletion Successfull...";
			model.addAttribute("message", message);
			List<PayeeBean> list = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list);
			return "ShowPayees";
		}else {
			message = "Payee Deletion Unsuccessfull...";
			model.addAttribute("message", message);
			List<PayeeBean> list = pDao.getAllPayees(user);
			model.addAttribute("getAllPayees", list);
			return "ShowPayees";
		}
	}
}
