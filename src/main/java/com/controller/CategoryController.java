package com.controller;

import java.util.*;

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
import com.bean.AccountType;
import com.bean.CategoryBean;
import com.bean.LabelBean;
import com.bean.LoginBean;
import com.bean.PayeeBean;
import com.bean.SubcategoryBean;
import com.dao.CategoryDao;

@Controller
public class CategoryController {

	@Autowired
	CategoryDao cDao;
	
	@PostMapping("/categoryInsertion")
	public String CategoryInsertion(@Valid @ModelAttribute("validate6") CategoryBean category,BindingResult result, SubcategoryBean subCategory,HttpSession session,String message,Model model) {
		
		if(result.hasErrors()) {
			LoginBean user = (LoginBean) session.getAttribute("user");
			if(user.getUserId() == 1) {
				model.addAttribute("category", category);
				model.addAttribute("validate5", new AccountBean());
				model.addAttribute("validate9", new LabelBean());
				model.addAttribute("validate10", new PayeeBean());
				model.addAttribute("validate12", new AccountType());
				
//				List<CategoryBean> list = cDao.getAllCategories(user);
//				model.addAttribute("getCategories", list);
				
				message = "Invalid input!";
				model.addAttribute("message", message);
				return "AdminHome";
			}else {
				model.addAttribute("category", category);
				model.addAttribute("validate5", new AccountBean());
				model.addAttribute("validate6", new CategoryBean());
				model.addAttribute("validate9", new LabelBean());
				model.addAttribute("validate10", new PayeeBean());
				model.addAttribute("validate12", new AccountType());
				
				message = "Invalid input!";
				model.addAttribute("message", message);
				return "UserHome";
			}
			
		}else {
			LoginBean user = (LoginBean) session.getAttribute("user");
			if(user.getUserId() == 1) {
				model.addAttribute("category", category);	
				model.addAttribute("validate5", new AccountBean());
				model.addAttribute("validate6", new CategoryBean());
				model.addAttribute("validate9", new LabelBean());
				model.addAttribute("validate10", new PayeeBean());
				model.addAttribute("validate12", new AccountType());
				
				System.out.println("Session user id:"+user.getUserId());
				System.out.println("Sub-category Name => "+subCategory.getSubCatName());
				try {
					category = cDao.createCategory(category,user);
					
					boolean flag2 = cDao.createSubCategory(subCategory,category,user);
					if(flag2) {
						System.out.println("Your action has done Successfully...");
						message = "Your action has done Successfully...";
						model.addAttribute("message", message);
					}else {
						System.out.println("Something is wrong...");
						message = "Something is wrong...";
						model.addAttribute("message", message);
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				return "AdminHome";
			}else {
				model.addAttribute("category", category);	
				model.addAttribute("validate5", new AccountBean());
				model.addAttribute("validate6", new CategoryBean());
				model.addAttribute("validate9", new LabelBean());
				model.addAttribute("validate10", new PayeeBean());
				model.addAttribute("validate12", new AccountType());
				
				System.out.println("Session user id:"+user.getUserId());
				System.out.println("Sub-category Name => "+subCategory.getSubCatName());
				try {
					category = cDao.createCategory(category,user);
					System.out.println(category.getCategoryId());
					boolean flag2 = cDao.createSubCategory(subCategory,category,user);
					if(flag2) {
						System.out.println("Your action has done Successfully...");
						message = "Your action has done Successfully...";
						model.addAttribute("message", message);
					}else {
						System.out.println("Something is wrong...");
						message = "Something is wrong...";
						model.addAttribute("message", message);
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				return "UserHome";
			}
		}
	}
	
	@GetMapping ("/manageCategories")
	public String ManageCategories (Model model, HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		List<CategoryBean> list = cDao.getAllCategories(user);
		model.addAttribute("getAllCategories", list);
		return "ManageCategories";
	}
	@GetMapping("/manageSubCategories")
	public String ManageSubCategories(Model model, HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		List<SubcategoryBean> list = cDao.getAllSubCategories(user);
		model.addAttribute("getAllSubCategories", list);
		return "ManageSubCategories";
	}
	
	@GetMapping("/userCategories")
	public String UserCategories(Model model, HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		List<CategoryBean> list = cDao.getUserCategories(user);
		model.addAttribute("getAllUserCategories", list);
		return "UserCategories";
	}
	
	@GetMapping("/userSubCategories")
	public String UserSubCategories(Model model, HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		List<SubcategoryBean> list = cDao.getUserSubCategories(user);
		model.addAttribute("getAllUserSubCategories", list);
		return "UserSubCategories";
	}
	
	
	
	@GetMapping("/editCategory/{categoryId}")
	public String EditCategory(@PathVariable("categoryId") int categoryId,Model model, HttpSession session) {
		System.out.println("Category Id when edit button has clicked => "+categoryId);
		CategoryBean category = cDao.getAllCategoriesById(categoryId);
		System.out.println("Category id, name after getAllCategoriesById() called => "+category.getCategoryId()+" - "+category.getCategoryName());
		model.addAttribute("Category", category);
		return "UpdateCategory";
	}
	@GetMapping("/modifyCategory")
	public String ModifyCategory(CategoryBean category, Model model,String message,HttpSession session) {
		System.out.println("Category id and name from modifyCategory mapping =>"+category.getCategoryId()+" - "+category.getCategoryName());
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag= cDao.updateCategories(category);
		System.out.println("Result after updateCategory() function called => "+flag);
		if(flag) {
			message = "Category has updated successfully...";
			System.out.println("flag true");
			model.addAttribute("message", message);
			System.out.println(message);
			List<CategoryBean> list = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list);
			return "ManageCategories";
		}else {
			System.out.println("flag false");
			message = "Something went wrong!";
			System.out.println(message);
			model.addAttribute("message", message);
			List<CategoryBean> list = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list);
			return "ManageCategories";
		}
	}
	@GetMapping("/deleteCategory/{categoryId}")
	public String DeleteCategory(@PathVariable("categoryId") int categoryId,Model model,String message,HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag = cDao.deleteCategoryById(categoryId,user);
		if(flag) {
			message = "Category Deletion Successfull...";
			model.addAttribute("message", message);
			List<CategoryBean> list = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list);
			return "ManageCategories";
		}else {
			message = "Category Deletion Unsuccessfull...";
			model.addAttribute("message", message);
			List<CategoryBean> list = cDao.getAllCategories(user);
			model.addAttribute("getAllCategories", list);
			return "ManageCategories";
		}
	}
	
	@GetMapping("/deleteSubCategory/{subCatId}")
	public String DeleteSubCategory(@PathVariable("subCatId") int subCatId, Model model,HttpSession session) {
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag = cDao.deleteSubCategory(subCatId,user);
		if(flag) {
			String message = "Sub-category deletion successful...";
			model.addAttribute("message", message);
			return "ManageSubCategories";
		}else {
			String message = "Something went wrong!";
			model.addAttribute("message", message);
			return "ManageSubCategories";
		}
	}
	
	@GetMapping("/editSubCategory/{subCatId}")
	public String EditSubCategory(@PathVariable("subCatId") int subCatId, Model model, HttpSession session) {
		
		SubcategoryBean subCategory = cDao.getAllSubCategoryById(subCatId);
		model.addAttribute("subCategory", subCategory);
		return "UpdateSubCategory";
	}
	
	@GetMapping("/modifySubCategory")
	public String ModifySubCategory(SubcategoryBean subcategory, Model model,HttpSession session) {
		System.out.println("Sub-Category id and name from modifySubCategory mapping =>"+subcategory.getSubCatId()+subcategory.getSubCatName());
		LoginBean user = (LoginBean) session.getAttribute("user");
		boolean flag= cDao.updateSubCategory(subcategory);
		System.out.println("Result after updateSubCategory() function called => "+flag);
		if(flag) {
			String message = "Sub Category has updated successfully..";
			System.out.println("flag true");
			model.addAttribute("message", message);
			System.out.println(message);
			List<SubcategoryBean> list = cDao.getAllSubCategories(user);
			model.addAttribute("getAllSubCategories", list);
			return "ManageSubCategories";
		}else {
			System.out.println("flag false");
			String message = "Something went wrong!";
			System.out.println(message);
			model.addAttribute("message", message);
			List<SubcategoryBean> list = cDao.getAllSubCategories(user);
			model.addAttribute("getAllSubCategories", list);
			return "ManageSubCategories";
		}
	}
}
