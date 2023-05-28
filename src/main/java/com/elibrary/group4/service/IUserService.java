package com.elibrary.group4.service;

import com.elibrary.group4.model.User;
import com.elibrary.group4.model.request.UserRequest;

import java.util.List;

public interface IUserService {
    List <User> list() throws Exception;
    User Create(UserRequest userRequest) throws Exception;
    User get(String userId) throws Exception;
    void update(UserRequest userRequest, String userId) throws Exception;
    void delete(String userId) throws Exception;
}
