package com.finances.controller;

import com.finances.dto.response.ApiResponse;
import com.finances.service.BoardService;
import org.springframework.boot.rsocket.context.RSocketPortInfoApplicationContextInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/user/board")
    public ResponseEntity<ApiResponse> createBoard(@RequestBody String boardName, Principal principal) {
        return ResponseEntity.ok(
                new ApiResponse(
                        boardService.create(boardName, principal), true
                )
        );
    }

    @GetMapping("/user/boards")
    public ResponseEntity<ApiResponse> userBoards(Principal principal) {
        return ResponseEntity.ok(
                new ApiResponse(
                        boardService.findAll(principal), true
                )
        );
    }
}
