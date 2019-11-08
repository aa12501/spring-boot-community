package com.aa12501.community.entities;

import lombok.Data;

@Data
public class UserEntity {
    private Integer userId; //用户id
    private String email;   //用户个人邮箱
    private Integer accountType;    //用户类型   0--本站用户  1--github用户
    private String accountId;   //第三方用户账号
    private String password;    //用户密码
    private String name;        //用户名称
    private String token;       //登录验证信息
    private Long gmtCreate;     //创建时间
    private Long gmtModified;   //最近修改时间
    private Long gmtLastLogin;  //最近登录时间
    private String avatarUrl;   //用户头像
}
