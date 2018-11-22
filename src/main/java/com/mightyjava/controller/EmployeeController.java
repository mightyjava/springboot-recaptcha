package com.mightyjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mightyjava.modal.Employee;
import com.mightyjava.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String add(Model model) {
		model.addAttribute("employee", new Employee());
		return "add";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("employee") Employee employee) {
		employeeService.add(employee);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("employees", employeeService.employees());
		return "list";
	}
}
