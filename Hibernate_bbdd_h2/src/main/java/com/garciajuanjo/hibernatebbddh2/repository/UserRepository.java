package com.garciajuanjo.hibernatebbddh2.repository;

import com.garciajuanjo.hibernatebbddh2.entity.User;

import java.util.List;

public interface UserRepository {

    User saveUser(User user);

    boolean deleteUserById(int idUser);

    boolean deleteUserByName(String name);

    List<User> getAllUsers();

    User getUserById(int id);

    User updateUser(User user);
}
