package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.model.Board;
import com.example.model.User;

public interface BoardRepository extends CrudRepository<Board, Integer>{
	List<Board> findAll();
	List<Board> findAllByOrderByNoDesc(Pageable pageable);
	Board save(Board board);
	List<Board> deleteByUseridAndNo(String userid, Integer no);
	List<Board> findByUseridOrderByNoDesc(String userid);
	Optional<Board> findByUseridAndNo(String userid, Integer no);
	Optional<Board> findByNo(Integer no);
}
