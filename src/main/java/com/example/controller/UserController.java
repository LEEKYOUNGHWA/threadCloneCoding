package com.example.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Board;
import com.example.model.Message;
import com.example.model.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@Operation(summary = "로그인", description = "로그인 합니다.")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user, HttpServletResponse response) {
    	Message message = new Message();
		try {
			userService.findByUseridAndPassword(user, response);
    		return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			message.setMessage(e.getMessage());
			return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
			return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
		}
    }
	
	@Operation(summary = "내 정보 조회", description = "내 정보를 조회 합니다.")
	@PostMapping(value = "/myname")
	public ResponseEntity<Object> getmyname(@AuthenticationPrincipal Authentication auth) { //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Message message = new Message();
		message.setMessage(auth.getName());
		return new ResponseEntity<Object>(message, HttpStatus.OK);
	}
	
	@Operation(summary = "회원가입", description = "회원가입 합니다.")
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody User user, HttpServletResponse response) {
		Message message = new Message();

		try {
    		userService.join(user, response);
    		return ResponseEntity.status(HttpStatus.OK).body(message);
    		// return new ResponseEntity<Object>(HttpStatus.OK);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }
}
