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

import com.bean.AccountBean;
import com.bean.CategoryBean;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.dao.labelDao;

@Controller
public class LabelController {
	@Autowired
	labelDao lDao;
	
	@PostMapping("/labelInsertion")
	public String LabelInsertion(@Valid @ModelAttribute("validate9") LabelBean label,BindingResult result,String message,CategoryBean cbean, Model model, HttpSession session) {
		if(result.hasErrors()) {
			model.addAttribute("label", label);
			message = "Invalid Input!";
			model.addAttribute("message", message);
			model.addAttribute("validate5", new AccountBean());
			model.addAttribute("validate6", new CategoryBean());
			model.addAttribute("validate9", new LabelBean());
			model.addAttribute("validate10", new PayeeBean());
			return "UserHome";
		}else {
			
			model.addAttribute("label", label);
			LoginBean user = (LoginBean) session.getAttribute("user");
			System.out.println("Session User ID From controller when insert the label => "+user.getUserId());
			try {
				boolean flag = lDao.createLabels(label,user);
				if(flag) {
					message = "One label has inserted successfully...";
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
	
	@GetMapping("/manageLabels")
	public String ManageLabels(Model model, HttpSession session) {
		LoginBean user = (LoginBean)session.getAttribute("user");
		List<LabelBean> list = lDao.getAllLabels(user);
		model.addAttribute("getAllLabels", list);
		
		return "ShowLabels";
	}
	@GetMapping("/addLabels")
	public String AddLabelsFromOutside(Model model) {
		model.addAttribute("validate9", new LabelBean());
		return "addLabels";
	}
	@GetMapping("/viewUserLabels")
	public String viewUserLabels(Model model,HttpSession session) {
		List<LabelBean> list = lDao.getAllUserLabels();
		model.addAttribute("getAllUserLabels", list);
		return "ViewUserLabels";
	}
	@GetMapping("/deletelabel/{labelId}")
	public String DeleteLabel(@PathVariable("labelId") int labelId, Model model, HttpSession session) {
		LoginBean user = (LoginBean)session.getAttribute("user");
		boolean flag = lDao.deleteLabel(labelId,user);
		if(flag) {
			String message = "Label Deletion Successful...";
			model.addAttribute("message", message);
			return "ShowLabels";
		}else{
			String message = "Something Wrong!";
			model.addAttribute("message", message);
			return "ShowLabels";
		}
	}
	@GetMapping("/editLabel/{labelId}")
	public String EditLabel(@PathVariable("labelId") int labelId, LabelBean label, Model model, HttpSession session) {
		label = lDao.getAllLabelsById(labelId);
		if(label != null) {
			model.addAttribute("label", label);
			return "UpdateLabel";
		}else {
			String message = "Somethign went wrong!";
			model.addAttribute("message", message);
			return "ShowLabels";
		}	
	}
	@GetMapping("/modifyLabel")
	public String ModifyLabel(LabelBean label, Model model, HttpSession session) {
		LoginBean user = (LoginBean)session.getAttribute("user");
		boolean flag = lDao.updateLabel(label,user);
		if(flag) {
			String message = "Label Updation Successful...";
			model.addAttribute("message", message);
			return "ShowLabels";
		}else {
			String message = "Something went wrong!";
			model.addAttribute("message", message);
			return "ShowLabels";
		}
	}
}
