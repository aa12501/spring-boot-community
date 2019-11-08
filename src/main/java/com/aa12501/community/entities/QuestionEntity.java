package com.aa12501.community.entities;

import lombok.Data;

@Data
public class QuestionEntity {
    private Integer questionId;     //问题id
    private String title;           //问题标题
    private String description;     //问题描述
    private String tag;             //问题标签
    private Integer creatorId;      //问题发起人id
    private Long gmtCreate;         //创建时间
    private Long gmtModified;       //最近修改时间
    private Integer commentCount;   //评论数
    private Integer viewCount;      //浏览数
    private Integer likeCount;      //点赞数
}
