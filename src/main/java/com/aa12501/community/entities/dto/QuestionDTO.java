package com.aa12501.community.entities.dto;

import com.aa12501.community.entities.QuestionEntity;
import com.aa12501.community.entities.UserEntity;

public class QuestionDTO extends QuestionEntity {
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
