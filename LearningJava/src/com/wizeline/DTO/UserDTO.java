package com.wizeline.DTO;

import java.util.Map;


public class UserDTO {

    private final String user;
    private final String password;
    private final String date;

    private UserDTO(UserBuilder builder) {
        this.user = builder.user;
        this.password = builder.password;
        this.date = builder.date;
    }

    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }


    public static final class UserBuilder {
        private String user;
        private String password;
        private String date;

        public UserBuilder() {
        }

        public UserBuilder user(String user) {
            this.user = user;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder date(String date) {
            this.date = date;
            return this;
        }
        public UserDTO build() {
            return new UserDTO(this);
        }
    }

}
