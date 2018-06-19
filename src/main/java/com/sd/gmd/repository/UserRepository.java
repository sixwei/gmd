package com.sd.gmd.repository;


import com.sd.gmd.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Users> rowMapper = new BeanPropertyRowMapper<>(Users.class);



    public int insertUser(Users user){

        final String sql = "insert into users (uid,rid, username,password,email,phone) values (?,?,?,?,?,?)";

        int insert = jdbcTemplate.update(sql, new Object[]{user.getUid(),user.getRid(),user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone()});

        return insert;
    }

    public List<Users> checkUser(String username,Integer rid){
        final String sql = "select *from users where username = ? AND rid = ?";

        final List<Users> users = jdbcTemplate.query(sql,new Object[]{username,rid}, rowMapper);
        return users;
    }

    //得到登录用户的id
    public Users getUserId(String username){
        final String sql = "select uid from users where username = ?";
        Users user = jdbcTemplate.queryForObject(sql, new Object[]{username}, rowMapper);
        return user;
    }






}
