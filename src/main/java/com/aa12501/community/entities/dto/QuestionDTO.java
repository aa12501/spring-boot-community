package com.aa12501.community.entities.dto;

import com.aa12501.community.entities.QuestionEntity;
import com.aa12501.community.entities.UserEntity;
import lombok.Data;

@Data
public class QuestionDTO extends QuestionEntity {
    private UserEntity userEntity;
}
