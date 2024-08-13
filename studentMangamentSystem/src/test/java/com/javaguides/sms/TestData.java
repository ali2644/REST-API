package com.javaguides.sms;

import com.javaguides.sms.domain.StudentEntity;

public class TestData {
    public static StudentEntity stdTest(){
        return StudentEntity.builder()
                .id(1)
                .firstName("Ali")
                .lastName("Ali Mahmoud")
                .email("ali@mail.com")
                .build();
    }
}
