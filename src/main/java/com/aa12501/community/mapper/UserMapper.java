package com.aa12501.community.mapper;

import com.aa12501.community.entities.dto.UserDTO;

public interface UserMapper {
    UserDTO selectSelective(UserDTO userDTO);

    void insertSelective(UserDTO userDTO);

    void updateSelectiveByPrimaryKey(UserDTO userDTO);

    void deleteSelectiveByPrimaryKey(UserDTO userDTO);
}
