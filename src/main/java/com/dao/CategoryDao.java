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
import com.bean.LoginBean;
import com.bean.SubcategoryBean;

@Repository
public class CategoryDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public CategoryBean createCategory(CategoryBean category, LoginBean user) throws Exception{
		
		List<CategoryBean> list = jdbcTemplate.query("select categoryName from category where categoryName=? and  userId=?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),user.getUserId());
		if(list.size() == 0) {
			jdbcTemplate.update("insert into category (categoryName, userId) values (?,?)",category.getCategoryName(),user.getUserId());
		}
		category = jdbcTemplate.queryForObject("select categoryId from category where categoryName=? and userId=?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),category.getCategoryName(),user.getUserId());
		System.out.println("CategoryId is "+category.getCategoryId());
		return category;
	}

	public boolean createSubCategory(SubcategoryBean subCategory, CategoryBean category,LoginBean user) {
		
		List<SubcategoryBean> list = jdbcTemplate.query("select subCatName from subCategory where subCatName = ? and userId=?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class), subCategory.getSubCatName(),user.getUserId());
			
		if(list.size() == 0) {
			int result = jdbcTemplate.update("insert into subCategory (subCatName,categoryId,userId) values (?,?,?)",subCategory.getSubCatName(),category.getCategoryId(),user.getUserId());
			if(result == 1) {
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}
		
	public List<CategoryBean> getAllCategories(LoginBean loginuser){
		int userId = loginuser.getUserId();
		System.out.println("UserId from listAllCategories Fucntion => "+userId);
		
		List<CategoryBean> list = jdbcTemplate.query("select * from category where userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),loginuser.getUserId());
		return list;
	}
	
	public CategoryBean getAllCategoriesById(int categoryId) {
		return  jdbcTemplate.queryForObject("select * from category where categoryId = ?",new Object[]{categoryId},new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class));
	}
	public boolean deleteCategoryById(int catId, LoginBean loginuser) {
		System.out.println("Category Id before delete query => "+catId);
		List<Expense> list = jdbcTemplate.query("select * from expense where catId=? and userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),catId,loginuser.getUserId());
		
//		System.out.println("Category Id from expense list when I tries to delete category by its id => "+list.get(0));
		
		if(list.size()>0) {
			int row1 = jdbcTemplate.update("update subCategory set categoryId=? where categoryId=? and userId=?",null,catId,loginuser.getUserId());
			if(row1 == 1) {
				System.out.println("ROW1 : "+row1);
				int row2 = jdbcTemplate.update("update expense set catId=? where catId=? and userId=?",null,catId,loginuser.getUserId());
				if(row2 == 1) {
					System.out.println("ROW2 : "+row2);
					int row3 = jdbcTemplate.update("delete from category where categoryId = ? and userId = ?",catId,loginuser.getUserId());
					System.out.println("Category Id after delete query => "+catId);
					System.out.println("Delete query fired for category =>"+row2);
					if(row3 == 1) {
						System.out.println("ROW3 : "+row3);
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		else {
			int row1 = jdbcTemplate.update("update subCategory set categoryId=? where categoryId=? and userId=?",null,catId,loginuser.getUserId());
			if(row1 == 1) {
				int row = jdbcTemplate.update("delete from category where categoryId = ? and userId = ?",catId,loginuser.getUserId());
				System.out.println("Category Id after delete query => "+catId);
				System.out.println("Delete query fired for category =>"+row);
				if(row == 1) {
					return true;
				}else {
					return false;
				}
			}
			else {
				return false;
			}
		}
	}
	public boolean updateCategories(CategoryBean category) {
		int row = jdbcTemplate.update("update category set categoryName = ? where categoryId = ?",category.getCategoryName(),category.getCategoryId());
		System.out.println("Update query fired for category => "+row);
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<CategoryBean> getUserCategories(LoginBean user) {
		List<CategoryBean> list = jdbcTemplate.query("select c.categoryId, c.categoryName, u.firstName from category c inner join users u on c.userId = u.userId", new userRowMapper());
		System.out.println("User Categories => "+list);
		return list;
	}
	class userRowMapper implements RowMapper<CategoryBean>{

		public CategoryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			CategoryBean category = new CategoryBean();
			LoginBean lbean=new LoginBean();
			category.setCategoryId(rs.getInt(1));
			category.setCategoryName(rs.getString(2));
			lbean.setFirstName(rs.getString(3));
			category.setUser(lbean);
			return category;
		}
		
	}
	public List<SubcategoryBean> getAllSubCategories(LoginBean user) {
		List<SubcategoryBean> list = jdbcTemplate.query("select sub.subCatId, c.categoryName, sub.subCatName from subCategory sub inner join category c on sub.categoryId=c.categoryId where sub.userId = ?", new UserRowMapper2(),user.getUserId());
		return list;
	}
	class UserRowMapper2 implements RowMapper<SubcategoryBean>{

		public SubcategoryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubcategoryBean sub = new SubcategoryBean();
			CategoryBean category = new CategoryBean();
			sub.setSubCatId(rs.getInt(1));
			category.setCategoryName(rs.getString(2));
			sub.setCategory(category);
			sub.setSubCatName(rs.getString(3));
			return sub;
		}
	}
	
	public SubcategoryBean getAllSubCategoryById(int subCatId) {
		
		return jdbcTemplate.queryForObject("select sub.subCatId,sub.subCatName,c.categoryName from subCategory sub inner join category c on sub.categoryId = c.categoryId where subCatId = ?", new UserRowMapper4(),subCatId);	
	}
	
	class UserRowMapper4 implements RowMapper<SubcategoryBean>{
		
		public SubcategoryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubcategoryBean sub = new SubcategoryBean();
			CategoryBean category = new CategoryBean();
			sub.setSubCatId(rs.getInt(1));
			sub.setSubCatName(rs.getString(2));
			category.setCategoryName(rs.getString(3));
			sub.setCategory(category);
			return sub;
		}
	}
	
	public boolean updateSubCategory(SubcategoryBean subCategory) {
		System.out.println("Subcategory Name and Subcategory Id from category dao => "+subCategory.getSubCatId()+" - "+subCategory.getSubCatName());
		int row = jdbcTemplate.update("update subCategory set subCatName=? where subCatId=?",subCategory.getSubCatName(),subCategory.getSubCatId());
		if(row == 1) {
			return true;
		}else {
			return false;
		}
	}

	public List<SubcategoryBean> getUserSubCategories(LoginBean user) {
		List<SubcategoryBean> list = jdbcTemplate.query("select sub.subCatId,c.categoryName,sub.subCatName,u.firstName from subCategory sub inner join category c on sub.categoryId = c.categoryId inner join users u on u.userId=sub.userId", new UserRowMapper3());
		
		return list;
	}
	class UserRowMapper3 implements RowMapper<SubcategoryBean>{

		public SubcategoryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubcategoryBean sub = new SubcategoryBean();
			CategoryBean category = new CategoryBean();
			LoginBean user = new LoginBean();
			sub.setSubCatId(rs.getInt(1));
			category.setCategoryName(rs.getString(2));
			sub.setCategory(category);
			sub.setSubCatName(rs.getString(3));
			user.setFirstName(rs.getString(4));
			sub.setUser(user);
			return sub;
		}
	}
	public List<SubcategoryBean> trackCategory(String categoryName,CategoryBean category,LoginBean user,SubcategoryBean subCategory) {
		
		category = jdbcTemplate.queryForObject("select * from category where categoryName = ? and userId = ?", new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class),categoryName,user.getUserId());
		
		if(category != null) {
		
			System.out.println("Category Id, Name and User Id : "+category.getCategoryId()+" - "+category.getCategoryName()+" - "+user.getUserId());
			
			List<SubcategoryBean> subCategories = jdbcTemplate.query("select subCatName from subCategory where categoryId = ? and userId = ?", new BeanPropertyRowMapper<SubcategoryBean>(SubcategoryBean.class),category.getCategoryId(),user.getUserId());
			if(subCategories.size() != 0) {
				
				return subCategories;
			}else {
				return subCategories;
			}
		}else {
			return null;
		}
	}

	public boolean deleteSubCategory(int subCatId, LoginBean user) {
		System.out.println("Sub Category Id before delete query => "+subCatId);
		List<Expense> list = jdbcTemplate.query("select * from expense where subCatId=? and userId=?", new BeanPropertyRowMapper<Expense>(Expense.class),subCatId,user.getUserId());
		
		if(list.size() > 0) {
			int row1 = jdbcTemplate.update("update expense set subCatId=? where subCatId=? and userId=?",subCatId,user.getUserId());
			if(row1 == 1) {
				int row2 = jdbcTemplate.update("delete from subCategory where subCatId=? and userId = ?",subCatId,user.getUserId());
				if(row2 == 1) {
					return true;
				}else {
					return false;
				}
			}else{
				return false;
			}
		}else {
			int row = jdbcTemplate.update("delete from subCategory where subCatId=? and userId = ?",subCatId,user.getUserId());
			if(row == 1) {
				return true;
			}else {
				return false;
			}
		}
		
	}
}
