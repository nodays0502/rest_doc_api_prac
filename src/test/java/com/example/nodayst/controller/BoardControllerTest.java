package com.example.nodayst.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.nodayst.controller.dto.ResponseMessage;
import com.example.nodayst.domain.Board;
import com.example.nodayst.repository.BoardRepositorySpringDataJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers = BoardController.class)
@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
class BoardControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BoardRepositorySpringDataJpa boardRepository;

    @BeforeEach
    void setUp(){
        long boardId = 1L;
        String title = "제목";
        String desc = "내용";
        Board board = new Board(boardId,title,desc);
        boardRepository.save(board);
    }

    @Test
    @DisplayName("게시글 한개 조회 테스트")
    public void findBoardSuccess() throws Exception {
        long boardId = 1L;
        String title = "제목";
        String desc = "내용";
        Board board = new Board(boardId,title,desc);

        mvc.perform(RestDocumentationRequestBuilders.get("/api/board/{boardId}",boardId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(boardId))
            .andExpect(jsonPath("$.title").value(title))
            .andExpect(jsonPath("$.desc").value(desc))
            .andDo(document("find_board",
                pathParameters(
                    parameterWithName("boardId").description("board Id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("board Id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("board 제목"),
                    fieldWithPath("desc").type(JsonFieldType.STRING).description("board 내용")
                )));

    }
    @Test
    @DisplayName("게시글 등록 테스트")
    public void registerBoard()
        throws Exception {

        long boardId = 2L;
        String title = "새로운 제목";
        String desc = "새로운 내용";
        mvc.perform(post("/api/board")
                .content("{\"title\": \""+title+"\",\"desc\": \""+desc+"\"}")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(content().string(ResponseMessage.REGISTER_BOARD_SUCCESS_MESSAGE))
            .andDo(document("register_board",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("board 제목"),
                    fieldWithPath("desc").type(JsonFieldType.STRING).description("board 내용")
                )));
    }

    @Test
    @DisplayName("게시글 변경 테스트")
    public void changeBoard()
        throws Exception {

        long boardId = 1L;
        String title = "바뀐 제목";
        String desc = "바뀐 내용";

        mvc.perform(RestDocumentationRequestBuilders.patch("/api/board/{boardId}",boardId)
                .content("{\"title\": \""+title+"\",\"desc\": \""+desc+"\"}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(ResponseMessage.CHANGE_BOARD_SUCCESS_MESSAGE))
            .andDo(document("patch_board",
                pathParameters(
                    parameterWithName("boardId").description("board Id")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("board 새로운 제목"),
                    fieldWithPath("desc").type(JsonFieldType.STRING).description("board 새로운 내용")
                )));
    }

    @Test
    @DisplayName("게시글 제거 테스트")
    public void removeBoard() throws Exception {
        long boardId = 1L;
        mvc.perform(RestDocumentationRequestBuilders.delete("/api/board/{boardId}",boardId))
            .andExpect(status().isOk())
            .andExpect(content().string(ResponseMessage.REMOVE_BOARD_SUCCESS_MESSAGE))
            .andDo(document("delete_board",
                pathParameters(
                    parameterWithName("boardId").description("board Id")
                )));
    }
}