package com.aa12501.community.entities;

import lombok.Data;

@Data
public class UserEntity {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
