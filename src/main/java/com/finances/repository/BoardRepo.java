package com.finances.repository;

import com.finances.model.Board;
import com.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String boardName);

    List<Board> findAllByUser(User currentUser);
}
