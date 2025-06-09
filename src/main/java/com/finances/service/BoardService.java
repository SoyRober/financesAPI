package com.finances.service;

import com.finances.controller.BoardController;
import com.finances.exception.AlreadyExistentEntityException;
import com.finances.exception.NonExistentEntityException;
import com.finances.model.Board;
import com.finances.model.User;
import com.finances.repository.BoardRepo;
import com.finances.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepo boardRepo;
    private final UserRepo userRepo;

    public BoardService(BoardRepo boardRepo, UserRepo userRepo) {
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
    }

    public String create(String boardName, Principal principal) {
        if (boardRepo.findByName(boardName).isPresent()) {
            throw new AlreadyExistentEntityException("A board with this name already exists");
        }

        User currentUser = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new NonExistentEntityException("User not found"));

        System.out.println("currentUser = " + currentUser.getUsername());

        Board newBoard = new Board();
        newBoard.setName(boardName);
        newBoard.setUser(currentUser);
        boardRepo.save(newBoard);

        return "Board created successfully";
    }

    public List<String> findAll(Principal principal) {
        User currentUser = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new NonExistentEntityException("User not found"));

        return boardRepo.findAllByUser(currentUser).stream()
                .map(Board::getName)
                .toList();
    }
}
