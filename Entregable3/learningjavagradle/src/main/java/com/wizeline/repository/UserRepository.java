package com.wizeline.repository;

public interface UserRepository {

    String createUser(String user, String password);

    String login(String user, String password);

}
