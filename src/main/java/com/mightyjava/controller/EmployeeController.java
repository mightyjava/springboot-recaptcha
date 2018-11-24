package com.mightyjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.mightyjava.modal.Employee;
import com.mightyjava.modal.ReCaptchaResponse;
import com.mightyjava.service.EmployeeService;

@Controller
public class EmployeeController {
	
	private String message;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/")
	public String add(Model model) {
		model.addAttribute("message", message);
		model.addAttribute("employee", new Employee());
		return "add";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("employee") Employee employee, @RequestParam(name="g-recaptcha-response") String captchaResponse) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String params = "?secret=6Lcc0HwUAAAAAE95NcYpjnL1eQ-EuIpViecOpWRQ&response="+captchaResponse;
		
		ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
		if(reCaptchaResponse.isSuccess()) {
			employeeService.add(employee);
			return "redirect:/list";
		} else {
			message = "Please verify captcha";
			return "redirect:/";
		}
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		message = null;
		model.addAttribute("employees", employeeService.employees());
		return "list";
	}
}
