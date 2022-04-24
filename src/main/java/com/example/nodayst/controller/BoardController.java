package com.example.nodayst.controller;

import com.example.nodayst.controller.dto.ChangeBoardRequestDto;
import com.example.nodayst.controller.dto.RegisterBoardRequestDto;
import com.example.nodayst.controller.dto.ResponseMessage;
import com.example.nodayst.domain.Board;
import com.example.nodayst.service.BoardService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity findBoard(@PathVariable long boardId){
        return new ResponseEntity<Board>(boardService.findBoard(boardId),HttpStatus.OK);
    }
    @PostMapping("/board")
    public ResponseEntity registerBoard(@Valid @RequestBody RegisterBoardRequestDto registerBoardRequestDto){
        boardService.registerBoard(registerBoardRequestDto);
        return new ResponseEntity<>(ResponseMessage.REGISTER_BOARD_SUCCESS_MESSAGE,HttpStatus.CREATED);
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity changeBoard(@PathVariable long boardId,@Valid @RequestBody ChangeBoardRequestDto changeBoardRequestDto){
        boardService.changeBoard(boardId,changeBoardRequestDto);
        return new ResponseEntity<>(ResponseMessage.CHANGE_BOARD_SUCCESS_MESSAGE,HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity removeBoard(@PathVariable long boardId){
        boardService.removeBoard(boardId);
        return new ResponseEntity<>(ResponseMessage.REMOVE_BOARD_SUCCESS_MESSAGE,HttpStatus.OK);
    }

}
