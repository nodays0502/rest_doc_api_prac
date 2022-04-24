package com.example.nodayst.service;

import com.example.nodayst.controller.dto.ChangeBoardRequestDto;
import com.example.nodayst.controller.dto.RegisterBoardRequestDto;
import com.example.nodayst.domain.Board;

public interface BoardService {
    public void registerBoard(RegisterBoardRequestDto registerBoardRequestDto);
    public Board findBoard(long boardId);
    public void changeBoard(long boardId, ChangeBoardRequestDto changeBoardRequestDto);
    public void removeBoard(long boardId);
}
