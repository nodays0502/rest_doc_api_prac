package com.example.nodayst.controller.dto;

import com.example.nodayst.domain.Board;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterBoardRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String desc;

    public RegisterBoardRequestDto(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
    public Board mapToBoard(){
        return new Board(title,desc);
    }
}
