package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.model.Board;
import com.example.model.User;
import com.example.repository.BoardRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService{

	@Autowired
	BoardRepository repo;

	public List findAll() {
		return (List) repo.findAll();
	}

	public List<Board> findAllByOrderByNoDesc(Pageable pageable) {
		return repo.findAllByOrderByNoDesc(pageable);
	}

	public List<Board> findByUserid(Authentication auth) {
		List<Board> myboards = repo.findByUseridOrderByNoDesc(auth.getName());
		return myboards;
	}

	public void insertBoard(Board board, Authentication auth) {

		if(board.getNo()!=null && repo.findByUseridAndNo(auth.getName(), board.getNo()).isEmpty()) {
			throw new IllegalArgumentException("권한 없음");
		}

		Board boardd = new Board(board.getNo(), auth.getName(), board.getBoard());
		repo.save(boardd);
	}

	@Transactional
	public void deleteBoard(Board board, Authentication auth) {
		repo.deleteByUseridAndNo(auth.getName(), board.getNo());
	}

	public Board findByUseridAndNo(Board board, Authentication auth) {

		Optional<Board> optionalBoard = repo.findByUseridAndNo(auth.getName(), board.getNo());
		if (optionalBoard.isEmpty()){
            // throw new IllegalArgumentException("권한 없음");
        }

		return optionalBoard.get();
	}

	public Board findByNo(Integer board) {
		Optional<Board> optionalBoard = repo.findByNo(board);
		if (optionalBoard.isEmpty()){
            throw new IllegalArgumentException("권한 없음");
        }

		return optionalBoard.get();
	}
}
