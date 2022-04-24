package com.example.nodayst.service;

import com.example.nodayst.controller.dto.ChangeBoardRequestDto;
import com.example.nodayst.controller.dto.RegisterBoardRequestDto;
import com.example.nodayst.domain.Board;
import com.example.nodayst.exception.DuplicateTitleException;
import com.example.nodayst.exception.NotFoundBoardIdException;
import com.example.nodayst.repository.BoardRepositorySpringDataJpa;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService {

    private final BoardRepositorySpringDataJpa boardRepository;

    @Override
    public void registerBoard(RegisterBoardRequestDto registerBoardRequestDto) {
        Board board = registerBoardRequestDto.mapToBoard();
        if (boardRepository.existsByTitle(board.getTitle())) {
            throw new DuplicateTitleException();
        }
        boardRepository.save(board);
    }

    @Override
    public Board findBoard(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new NotFoundBoardIdException();
        });
        return board;
    }

    @Override
    public void changeBoard(long boardId, ChangeBoardRequestDto changeBoardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new NotFoundBoardIdException();
        });
        board.changeTitle(changeBoardRequestDto.getTitle());
        board.changeDesc(changeBoardRequestDto.getDesc());
    }

    @Override
    public void removeBoard(long boardId) {
        if(!boardRepository.existsById(boardId)){
            throw new NotFoundBoardIdException();
        }
        boardRepository.deleteById(boardId);
    }
}
