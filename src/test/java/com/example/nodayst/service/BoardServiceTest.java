package com.example.nodayst.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.nodayst.controller.dto.ChangeBoardRequestDto;
import com.example.nodayst.controller.dto.RegisterBoardRequestDto;
import com.example.nodayst.domain.Board;
import com.example.nodayst.exception.DuplicateTitleException;
import com.example.nodayst.exception.NotFoundBoardIdException;
import com.example.nodayst.repository.BoardRepositorySpringDataJpa;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class BoardServiceTest {
        @Autowired
        private BoardService boardService;

        @Autowired
        private BoardRepositorySpringDataJpa boardRepository;

        @Test
        @DisplayName("게시글 등록 성공 테스트")
        public void registerBoardSuccess(){
                long boardId = 1L;
                String title = "제목";
                String desc = "내용";
                Board board = new Board(boardId,title,desc);
                RegisterBoardRequestDto registerBoardRequestDto = new RegisterBoardRequestDto(title,desc);
                boardService.registerBoard(registerBoardRequestDto);
                Board byId = boardRepository.findByTitle(title).get();
                assertEquals(byId.getTitle(),board.getTitle());
                assertEquals(byId.getDesc(),board.getDesc());
        }
        @Test
        @DisplayName("게시글 등록 실패 테스트 - DuplicateTitleException")
        public void registerBoardFail(){
                String title = "제목";
                String desc = "내용";
                RegisterBoardRequestDto registerBoardRequestDto = new RegisterBoardRequestDto(title,desc);
                boardService.registerBoard(registerBoardRequestDto);
                assertThrows(DuplicateTitleException.class,() -> boardService.registerBoard(registerBoardRequestDto));
        }
        @Test
        @DisplayName("게시글 한개 찾기 성공 테스트")
        public void findBoardSuccess(){
                String title = "제목";
                String desc = "내용";
                Board board = new Board(title,desc);
                boardRepository.save(board);
                board = boardRepository.findByTitle(title).get();
                Board findById = boardService.findBoard(board.getId());
                assertEquals(board.getId(),findById.getId());
                assertEquals(board.getTitle(),findById.getTitle());
                assertEquals(board.getDesc(),findById.getDesc());
        }
        @Test
        @DisplayName("게시글 한개 찾기 실패 테스트 - NotFoundBoardIdException")
        public void findBoardFail(){
                assertThrows(NotFoundBoardIdException.class,
                    () -> boardService.findBoard(1L));
        }
        @Test
        @DisplayName("게시글 변경 성공 테스트")
        public void changeBoardSuccess(){
                String title = "제목";
                String desc = "내용";
                Board board = new Board(title,desc);
                boardRepository.save(board);
                Board byTitle = boardRepository.findByTitle(title).get();
                assertEquals(board.getTitle(),byTitle.getTitle());
                assertEquals(board.getDesc(),byTitle.getDesc());
                String changedTitle = "바뀐 제목";
                String changedDesc = "바뀐 내용";
                ChangeBoardRequestDto changeBoardRequestDto = new ChangeBoardRequestDto(changedTitle,changedDesc);
                boardService.changeBoard(byTitle.getId(),changeBoardRequestDto);
                board = boardService.findBoard(byTitle.getId());
                assertEquals(board.getTitle(),changedTitle);
                assertEquals(board.getDesc(),changedDesc);
        }
        @Test
        @DisplayName("게시글 제거 테스트 - NotFoundBoardIdException")
        public void removeBoardSuccess(){
                String title = "제목";
                String desc = "내용";
                Board board = new Board(title,desc);
                boardRepository.save(board);
                Board findByTitle = boardRepository.findByTitle(title).get();
                assertEquals(board.getTitle(),findByTitle.getTitle());
                assertEquals(board.getDesc(),findByTitle.getDesc());
                boardService.removeBoard(board.getId());
                assertThrows(NotFoundBoardIdException.class,() -> boardService.findBoard(board.getId()));
        }
}