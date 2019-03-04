package by.krukouski.restfulldemo.services;

import by.krukouski.restfulldemo.models.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserBuId(long id);

    List<User> findAllUsers();

    void deleteAllUsers();

    boolean isUserExist(User user);

}
