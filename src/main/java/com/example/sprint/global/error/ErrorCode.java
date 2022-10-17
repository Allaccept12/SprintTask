package com.example.sprint.global.error;


import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCOUNT_NOT_FOUND("유저를 찾을 수 없습니다."),
    BOOK_NOT_FOUND("찾고자하는 책을 찾을 수 없습니다."),
    NAME_IS_REQUIRED_VALUE("비밀번호는 필수 값 입니다."),
    BIRTH_IS_REQUIRED_VALUE("이메일은 필수 값 입니다."),
//    TOKEN_NOT_FOUND("토큰이 없거나 이미 로그아웃 하셨습니다."),
    INPUT_VALUE_INVALID("입력값이 올바르지 않습니다.");


    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }


}
