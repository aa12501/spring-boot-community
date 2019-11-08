package com.aa12501.community.inter.service;

import com.aa12501.community.entities.dto.UserDTO;
import com.aa12501.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserMapper userMapper;

    public UserDTO selectAll(UserDTO userDTO){
        return userMapper.selectSelective(userDTO);
    }

    public UserDTO selectWithoutPwd(UserDTO userDTO){
        return userMapper.selectSelectiveWithoutPwd(userDTO);
    }

    public void create(UserDTO userDTO){
        userMapper.insertSelective(userDTO);
    }

    public void updateUserState(UserDTO userDTO) {
        userMapper.updateSelectiveByPrimaryKey(userDTO);
    }
}
