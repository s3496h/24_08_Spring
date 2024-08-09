package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	int count = -1;
	
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return "안녕하세요";
	}

	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return "잘가";
	}

	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public int showMain3() {
		int a = 1;
		int b = 2;
		return a + b;
	}
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public int showMain4() {
		
		count++;
		
		return count;
		
	}


}
