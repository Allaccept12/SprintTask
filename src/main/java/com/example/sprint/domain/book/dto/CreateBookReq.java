package com.example.sprint.domain.book.dto;


import com.example.sprint.domain.book.domain.Currency;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.ISBN;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookReq {

    @NotEmpty(message = "저자 id값은 필수 값입니다.")
    @ApiModelProperty(required = true)
    private List<Long> authorIds;

    @Size(min=1, max=255,message = "제목은 필수값이며, 255자내로 작성 해주세요")
    @ApiModelProperty(required = true)
    private String name;

    @NotNull(message = "책의 총 페이지 수를 입력 해주세요.")
    @ApiModelProperty(required = true)
    private Integer totalPages;

    @NotEmpty(message = "책의 발간연도를 입력해주세요.")
    @ApiModelProperty(required = true)
    private String publicationOfYear;

    @ISBN(type = ISBN.Type.ISBN_13, message = "isbn 패턴을 맞춰서 입력해주세요")
    @ApiModelProperty(required = true)
    private String isbn;

    @Pattern(regexp="(^\\d+$)|(^\\d+\\.\\d{1,2}$)",
            message = "가격은 소수점 2자리수까지만 입력 가능합니다.")
    private String price;

    private Currency currency;


}
