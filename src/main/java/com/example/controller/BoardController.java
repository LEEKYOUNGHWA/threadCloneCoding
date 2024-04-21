package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import com.example.model.Board;
import com.example.service.BoardService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Operation(summary = "모든 게시글 조회", description = "모든 회원 정보를 조회 합니다.")
	@GetMapping()
	public ResponseEntity<List<Board>> getAllMembers(Pageable pageable) {
		return new ResponseEntity<>(boardService.findAllByOrderByNoDesc(pageable), HttpStatus.OK);
	}

	@Operation(summary = "게시글 작성", description = "게시글 작성하여 저장합니다.")
	@PostMapping()
	public ResponseEntity<Object> insertBoard(@Parameter(name = "board", description = "보드 정보", in = ParameterIn.PATH) @RequestBody Board board
			, @AuthenticationPrincipal Authentication auth) {
		try {
			boardService.insertBoard(board,auth);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
	
	@Operation(summary = "게시글 삭제", description = "게시글 삭제하여 저장합니다.")
	@PostMapping(value ="/delete")
	public ResponseEntity<Object> deleteBoard(
			@RequestBody Board board,
			@AuthenticationPrincipal Authentication auth) {
		try {
			boardService.deleteBoard(board, auth);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
	
	@Operation(summary = "내가쓴 게시글 보기", description = "내가쓴 게시글 만 봅니다.")
	@PostMapping(value ="/my")
	public ResponseEntity<List<Board>> myBoard(@AuthenticationPrincipal Authentication auth) {
		return new ResponseEntity<>(boardService.findByUserid(auth), HttpStatus.OK);
	}

	@Operation(summary = "게시글 수정 정보 가져오기", description = "내가 쓴 글만 수정 하도록 확인 및 조회합니다")
	@PostMapping(value ="/loadedit")
	public ResponseEntity<Object> loadedit(
			@Parameter(name = "board", description = "보드 정보", in = ParameterIn.PATH) @RequestBody Board board,
			@AuthenticationPrincipal Authentication auth) {
		try {
			Board resboard = boardService.findByUseridAndNo(board,auth);
			return new ResponseEntity<Object>(resboard, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
	@Operation(summary = "게시글 정보 가져오기", description = "게시글 조회합니다")
	@GetMapping(value ="/board")
	public ResponseEntity<Object> loadboard(
			@Parameter(name = "board", description = "보드 정보")@RequestParam Integer board) {
		try {
			Board resboard = boardService.findByNo(board);
			return new ResponseEntity<Object>(resboard, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
