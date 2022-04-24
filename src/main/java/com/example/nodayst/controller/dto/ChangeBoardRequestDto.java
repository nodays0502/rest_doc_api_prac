package com.example.nodayst.controller.dto;

import com.example.nodayst.domain.Board;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ChangeBoardRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String desc;

    public ChangeBoardRequestDto(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

}
