package com.controller;

import java.util.List;
import java.util.Random;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.EmailBean;
import com.bean.ForgotUserBean;
import com.bean.LoginBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.dao.UserDao;
import com.ser.MailSend;

@Controller
class UserController {
	
	@Autowired
	UserDao dao;
	
	@GetMapping("/checkemail/{email}")
	@ResponseBody
	public boolean checkEmail(@PathVariable("email") String email) {
		boolean result = dao.checkEmailDuplication(email);
		return result;
	}
	@GetMapping("/checkphone/{phone}")
	@ResponseBody
	public boolean checkPhone(@PathVariable("phone") String phone) {
		boolean result = dao.checkPhoneDuplication(phone);
		return result;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("validate", new LoginBean());
//		List<RoleBean> listRole = roleDao.getRoles();
//		System.out.println(listRole.get(1).getRoleName());
//		model.addAttribute("roles", listRole);
		return "login";
	}

	@GetMapping("/adminLogout")
	public String adminSignout(HttpSession session, @Valid @ModelAttribute("validate") LoginBean user,
			BindingResult result) {
		session.invalidate();
		System.out.println("Session Destroyed!");
		System.out.println("Your are logged out from our site!");
		return "login";
	}
	@GetMapping("/loginPage")
	public String moveToLoginPage(Model model, HttpSession session) {
		model.addAttribute("validate", new LoginBean());
		session.invalidate();
		return "login";
	}
	

	@GetMapping("/userLogout")
	public String userSignout(HttpSession session, @Valid @ModelAttribute("validate") LoginBean user,
			BindingResult result) {
		session.invalidate();
		System.out.println("Session Destroyed!");
		System.out.println("Your are logged out from our site!");
		return "login";
	}

	@RequestMapping(value = "viewData")
	public String getUserData(Model model) {
		model.addAttribute("showdata", dao.showData());
		return "userData";
	}

	@GetMapping("/moveToRegister")
	public String signUp(Model model) {
		model.addAttribute("validate3", new LoginBean());
		return "UserSignup";
	}

	@PostMapping("/saveUser")
	public String signup(@Valid @ModelAttribute("validate3") UserBean user, BindingResult result, RoleBean role, Model model, String message, HttpSession session) {
		System.out.println("Binding Result=> " + result);
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "UserSignup";
		} else {
			// String filename= service.fileUpload(file);
			// user.setProfilePic(filename);
			role.setRoleId(2);
			user.setRole(role);
			user.setFlag(true);
			int rId = user.getRole().getRoleId();
			boolean flag = user.getFlag();
			System.out.println("Flag Value while inserting an user => " +flag);
			System.out.println("Role ID:" + rId);
			List<UserBean> list = dao.checkExistingUserData(user);
			System.out.println(list.size());
			if(list.size()==1) {
				model.addAttribute("user", user);
				model.addAttribute("validate", user);
				message = "Invalid Action!";
				model.addAttribute("message", message);
				return "UserSignup";
			}else {
					model.addAttribute("user", user);
					model.addAttribute("validate", user);
					dao.insert(user);
					return "login";
			}
		}
	}

//	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.GET)
//	public String deleteUser(@PathVariable("userId") int userId, Model model) {
//		int i = dao.delete(userId);
//		if (i != 0) {
//			model.addAttribute("showdata", dao.showData());
//			return "userData";
//		}
//		return "userData";
//
//	}

	@GetMapping(value = "/editUser/{userId}")
	public String updateUser(@PathVariable("userId") int userId, Model model) {

		UserBean bean = dao.getDataById(userId);
		model.addAttribute("user", bean);
		return "UpdateUser";
	}

	@PostMapping("/updateData")
	public String EditUser(UserBean user, Model model) {
//		System.out.println(user.getEmail());
		boolean flag = dao.updateUser(user);
		System.out.println(flag);
		if(flag) {
			System.out.println("User flag has updated successfully..."+user.getFlag());
			String message = "User has updated successfully...";
			model.addAttribute("message", message);
			return "UpdateUser";
		}else {
			System.out.println("User flag has not updated successfully..."+user.getFlag());
			String message = "Something Went Wrong!";
			model.addAttribute("message", message);
			return "UpdateUser";
		}
	}

	@GetMapping("/forgotpassword")
	public String forgot(Model model){
		model.addAttribute("emailValidate", new LoginBean());
		return "Email";
	}

	@GetMapping("/submit")
	public String demoSubmit(@RequestParam String username) {
		System.out.println("User Name:" + username);
		return "redirect:/adminHome";
	}

	@PostMapping("/verifyEmail")
	public String email(@Valid @ModelAttribute("emailValidate") EmailBean user, BindingResult result, @RequestParam("email") String email, Model model, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "Email";
		}else {
			model.addAttribute("user", user);
			boolean flag = dao.verifyEmail(email);
			if(flag) {
				String message = "Your email has been verified successfully...";
				model.addAttribute("message", message);
				String otp = generateOTP();
				MailSend.mail(email, otp);
				session.setAttribute("email", email);
				session.setAttribute("GOtp", otp);
				return "otp";
			}else {
				String message = "Invalid Email!";
				model.addAttribute("message", message);
				return "Email";
			}
		}
	}

	public static String generateOTP() {
		int lengthOtp = 6;
		char OTPValue[] = new char[lengthOtp];
		String allDigits = "1234567890";
		Random randomObj = new Random();
		for (int i = 0; i < lengthOtp; i++) {
			int randomNo = randomObj.nextInt(allDigits.length());
			OTPValue[i] = allDigits.charAt(randomNo);
		}
		String otp = new String(OTPValue);
		return otp;
	}

	@PostMapping("/otpCheck")
	public String OtpCheck(@RequestParam("otp") String otp, Model model, HttpSession session) {
		
		String GOtp = (String) session.getAttribute("GOtp");
		if (GOtp.equals(otp)) {
			model.addAttribute("forgotValidate", new ForgotUserBean());
			String message = "You entered correct OTP...Verifed Successfull.";
			model.addAttribute("message", message);
			return "forgot";
		}else {
			String message = "Invalid OTP!";
			model.addAttribute("message", message);
			return "otp";
		}
	}
	
	@PostMapping("/forgotPass")
	public String forgotPass(@Valid @ModelAttribute("forgotValidate") ForgotUserBean fuser, BindingResult result, Model model,HttpSession session) {
		if(result.hasErrors()) {
			model.addAttribute("forgotbean", fuser);
			return "forgot";
		}else{
			model.addAttribute("forgotbean", fuser);
			if(fuser.getPassword().equals(fuser.getCpassword())) {
				String user_email = (String)session.getAttribute("email");
				boolean flag = dao.updatePassword(fuser,user_email);
				if(flag) {
					String message = "Your password has successfully updated...";
					model.addAttribute("message", message);
					model.addAttribute("validate", new LoginBean());
					return "login";
				}else {
					String message = "Something went wrong!";
					model.addAttribute("message", message);
					return "forgot";
				}
			}else {
				String message = "Your password and confirm-password combination might be wrong!";
				model.addAttribute("message", message);
				return "forgot";
			}
		}
	}
	@GetMapping("/manageUsers")
	public String ManageUsers(Model model) {
		model.addAttribute("ViewUsers", dao.showData());
		return "User Management";
	}
}