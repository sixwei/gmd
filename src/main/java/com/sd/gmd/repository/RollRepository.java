package com.sd.gmd.repository;


import com.sd.gmd.domain.Rolls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RollRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Rolls> rowMapper = new BeanPropertyRowMapper<>(Rolls.class);

    public List<Rolls> getRolls(){
        final String sql = "select * form rolls";

        List<Rolls> rolls = jdbcTemplate.query(sql, rowMapper);
        return rolls;
    }

}
