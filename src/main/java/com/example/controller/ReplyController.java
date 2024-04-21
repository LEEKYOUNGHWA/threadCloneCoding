package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Reply;
import com.example.service.ReplyService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	ReplyService replyService;

	@Operation(summary = "답글 작성", description = "답글 작성하여 저장합니다.")
	@PostMapping()
	public ResponseEntity<Object> insertReply(
			@Parameter(name = "reply", description = "답글 정보", in = ParameterIn.PATH) 
			@RequestBody Reply reply
			, @AuthenticationPrincipal Authentication auth) {
		try {
			replyService.insertReply(reply,auth);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
	
	@Operation(summary = "답글 삭제", description = "답글 삭제하여 저장합니다.")
	@PostMapping(value ="/delete")
	public ResponseEntity<Object> deleteReply(
			@Parameter(name = "reply", description = "답글 정보") 
			@RequestBody Reply reply,
			@AuthenticationPrincipal Authentication auth) {
		try {
			replyService.deleteReply(reply, auth);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}

	@Operation(summary = "답글 수정 정보 가져오기", description = "내가 답글 조회합니다")
	@GetMapping(value ="/load")
	public ResponseEntity<Object> loadedit(
			@Parameter(name = "reply", description = "답글 정보", in = ParameterIn.PATH) 
			@RequestParam Integer board) {
		try {
			List<Reply> resreply = replyService.findByBoardnoOrderByReplynoDesc(board);
			return new ResponseEntity<Object>(resreply, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
}
