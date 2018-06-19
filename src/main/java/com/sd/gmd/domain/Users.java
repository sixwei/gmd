package com.sd.gmd.domain;


import lombok.Data;

@Data
public class Users {

    private Integer uid;

    private Integer rid;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;
}
