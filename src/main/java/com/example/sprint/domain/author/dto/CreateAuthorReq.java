package com.example.sprint.domain.author.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorReq {

    @Size(min=1, max=255,message = "성함은 필수값이며, 255자내로 작성 해주세요")
    @NotBlank
    private String name;

    @NotBlank
    private String birth;

}
