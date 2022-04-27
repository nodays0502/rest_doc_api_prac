package com.example.nodayst.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.nodayst.controller.dto.ChangeBoardRequestDto;
import com.example.nodayst.controller.dto.RegisterBoardRequestDto;
import com.example.nodayst.domain.Board;
import com.example.nodayst.exception.DuplicateTitleException;
import com.example.nodayst.exception.NotFoundBoardIdException;
import com.example.nodayst.repository.BoardRepositorySpringDataJpa;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Transactional
class BoardServiceUnitTest {

        private BoardService boardService;

        @Mock
        private BoardRepositorySpringDataJpa boardRepository;

        @BeforeEach
        void setUp(){
                boardService = new BoardServiceImp(boardRepository);
        }

        @Test
        @DisplayName("게시글 등록 성공 테스트")
        public void registerBoardSuccess(){
                long boardId = 1L;
                String title = "제목";
                String desc = "내용";
                Board board = new Board(boardId,title,desc);
                when(boardRepository.findByTitle(title)).thenReturn(Optional.of(board));

                RegisterBoardRequestDto registerBoardRequestDto = new RegisterBoardRequestDto(title,desc);
                boardService.registerBoard(registerBoardRequestDto);

                Board byId = boardRepository.findByTitle(title).get();
                assertEquals(byId.getTitle(),board.getTitle());
                assertEquals(byId.getDesc(),board.getDesc());
        }
        @Test
        @DisplayName("게시글 중복 제목 등록 실패 테스트 - DuplicateTitleException")
        public void registerBoardFail(){
                String title = "제목";
                String desc = "내용";
                RegisterBoardRequestDto registerBoardRequestDto = new RegisterBoardRequestDto(title,desc);

                when(boardRepository.existsByTitle(any())).thenReturn(true);

                assertThrows(DuplicateTitleException.class,() -> boardService.registerBoard(registerBoardRequestDto));
        }
        @Test
        @DisplayName("게시글 한개 찾기 성공 테스트")
        public void findBoardSuccess(){
                long boardId = 1L;
                String title = "제목";
                String desc = "내용";
                Board board = new Board(boardId,title,desc);

                when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

                Board findById = boardService.findBoard(board.getId());
                assertEquals(board.getId(),findById.getId());
                assertEquals(board.getTitle(),findById.getTitle());
                assertEquals(board.getDesc(),findById.getDesc());
        }
        @Test
        @DisplayName("등록되지 않은 게시글 찾기 실패 테스트 - NotFoundBoardIdException")
        public void findBoardFail(){

                when(boardRepository.findById(any())).thenReturn(Optional.empty());

                assertThrows(NotFoundBoardIdException.class,
                    () -> boardService.findBoard(1L));
        }
        @Test
        @DisplayName("게시글 변경 성공 테스트")
        public void changeBoardSuccess(){
                long boardId = 1L;
                String title = "제목";
                String desc = "내용";
                Board board = new Board(boardId,title,desc);
                String changedTitle = "바뀐 제목";
                String changedDesc = "바뀐 내용";
                ChangeBoardRequestDto changeBoardRequestDto = new ChangeBoardRequestDto(changedTitle,changedDesc);

                when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

                boardService.changeBoard(boardId,changeBoardRequestDto);
                Board findById = boardService.findBoard(boardId);
                assertEquals(findById.getTitle(),changedTitle);
                assertEquals(findById.getDesc(),changedDesc);
        }
        @Test
        @DisplayName("게시글 제거 성공 테스트")
        public void removeBoardSuccess(){
                long boardId = 1L;
                String title = "제목";
                String desc = "내용";
                Board board = new Board(title,desc);

                when(boardRepository.existsById(any())).thenReturn(true);
                doNothing().when(boardRepository).deleteById(any());

                boardService.removeBoard(boardId);
        }
        @Test
        @DisplayName("존재하지 않는 게시글 제거 실패 테스트 - NotFoundBoardIdException")
        public void removeBoardFail(){

                when(boardRepository.existsById(any())).thenReturn(false);

                assertThrows(NotFoundBoardIdException.class,()-> boardService.removeBoard(1L));
        }
}