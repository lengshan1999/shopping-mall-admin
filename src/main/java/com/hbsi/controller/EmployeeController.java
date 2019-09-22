package com.hbsi.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hbsi.dao.DepartmentDao;
import com.hbsi.dao.EmployeeDao;
import com.hbsi.entities.Department;
import com.hbsi.entities.Employee;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	DepartmentDao departmentDao;

	private Map<String , Object> map = new HashMap<>();

	/**
	 * 查询
	 * @param model
	 * @return
	 */
	@GetMapping("/emps")
	public String getEmp(Model model) {
		map.put("msg","查询员工成功");
		return  getAllEmps(model);
	}

	public String getAllEmps(Model model){
		Collection<Employee> employees = employeeDao.getAll();
		map.put("emps",employees);
		model.addAllAttributes(map);
		return "/emp/list.html";
	}
	
	/**
	 * 添加功能
	 * @param model
	 * @return
	 */
	@GetMapping("/emp")
	public String addPage(Model model) {
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		return "/emp/add.html";
	}
	
	/**
	 * 保存功能
	 * @param employee
	 * @return
	 */
	@PostMapping("/emp")
	public String saveEmp(Employee employee,Model model) {
		employeeDao.save(employee);
		map.put("msg","添加员工成功");
		return  getAllEmps(model);
	}
	
	/**
	 * 修改回显
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/emp/{id}")
	public String lookEmp(@PathVariable("id")Integer id,Model model) {
		Employee emp = employeeDao.get(id);//通过id查询是哪个员工
		model.addAttribute("emp",emp);
		//查询所有部门
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		return "/emp/seeDetailed.html";
	}
	
	/**
	 *修改功能
	 * @param employee
	 * @return
	 */
	@PostMapping("/empUpdate")
	public String updateEmp(Employee employee, Model model) {
        employeeDao.save(employee);
		map.put("msg","修改员工成功");
		return  getAllEmps(model);
	}
	
	@GetMapping("/empDelete/{id}")
	public String deleteEmp(@PathVariable("id")Integer id,Model model) {
		employeeDao.delete(id);
		map.put("msg","删除员工成功");
		return  getAllEmps(model);
	}
	
}
