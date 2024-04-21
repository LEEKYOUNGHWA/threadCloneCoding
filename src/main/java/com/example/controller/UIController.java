package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {

	@RequestMapping("/")
	public String viewPage() {
		return "index";
	}
	
	@RequestMapping("/write")
	public String viewWrite() {
		return "write";
	}
	
	@RequestMapping("/write/{boardno}")
	public String viewWrite(@PathVariable String boardno) {
		return "write";
	}
	
	@RequestMapping("/myPage")
	public String viewMyPage() {
		return "myPage";
	}
	
	@RequestMapping("/login")
	public String viewLogin() {
		return "login";
	}
	
	@RequestMapping("/reply/{boardno}")
	public String viewReply(@PathVariable String boardno) {
		return "reply";
	}
}

