package com.sd.gmd.repository;


import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Products;
import com.sd.gmd.domain.Users;
import com.sun.rowset.internal.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Users> mapperUsers =  new BeanPropertyRowMapper<>(Users.class);
    RowMapper<Brand> mapperBrands = new BeanPropertyRowMapper<>(Brand.class);

    public List<Users> getAllUser(){
        final String sql = "select * from users";

        List<Users> listUsers = jdbcTemplate.query(sql, mapperUsers);
        return listUsers;
    }

    public List<Brand> getAllBrand(){
        final String sql = "select * from brand";
        List<Brand> listBrand = jdbcTemplate.query(sql, mapperBrands);
        return listBrand;
    }


}
