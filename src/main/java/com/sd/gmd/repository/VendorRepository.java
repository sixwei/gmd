package com.sd.gmd.repository;

import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VendorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Products> productsRowMapper = new BeanPropertyRowMapper<>(Products.class);


    //根据用户uid查询厂商的产品
    public List<Products> getAllProducts(Integer uid){
        final String sql = "select * from products where uid =?";

        List<Products> productsList = jdbcTemplate.query(sql, new Object[]{uid}, productsRowMapper);
        return  productsList;

    }

    //添加产品  brand   的bid有唯一的标识

    public int addProduct(Products p){

        final String sql = "insert into products(bid,pname,price,pdescribe,total,sellnumber,residue,uid) value(?,?,?,?,?,?,?,?)";

        int update = jdbcTemplate.update(sql, new Object[]{p.getBid(), p.getPname(), p.getPrice(), p.getPdescribe(), p.getTotal(), p.getSellnumber(), p.getResidue(),p.getUid()});
        return update;
    }


    //产品编辑 pid
    public int editProduct(Products p){

        final String sql = "update products set";
        return 2;
    }

    //产品的删除

    public int deleteProduct(Integer pid){
        final String sql ="delete from products where pid=?";
        int update = jdbcTemplate.update(sql, pid);
        return update;
    }


    //查询uid中的所有的品牌

    RowMapper<Brand> mapperBrand = new BeanPropertyRowMapper<>(Brand.class);

    public List<Brand> getAllBrand(Integer uid){
        final String sql = "select * from brand where uid=?";

        List<Brand> query = jdbcTemplate.query(sql, new Object[]{uid}, mapperBrand);

        return query;

    }


}
