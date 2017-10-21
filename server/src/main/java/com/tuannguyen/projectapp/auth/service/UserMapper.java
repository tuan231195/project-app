package com.tuannguyen.projectapp.auth.service;

import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.auth.model.UserForm;
import org.modelmapper.ModelMapper;

public class UserMapper {
    public static ModelMapper modelMapper = new ModelMapper();

    public static User map(UserForm userForm) {
        return modelMapper.map(userForm, User.class);
    }

    public static UserForm map(User user) {
        return modelMapper.map(user, UserForm.class);
    }
}
