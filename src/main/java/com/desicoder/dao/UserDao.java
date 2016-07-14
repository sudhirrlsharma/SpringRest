package com.desicoder.dao;


import com.desicoder.model.User;

public interface UserDao {

	User findByUserName(String username);
}

