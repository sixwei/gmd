package com.sd.gmd.Service;


import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Products;
import com.sd.gmd.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    //查询出所有的产品
    public List<Products> getAllProducts(Integer uid){
        List<Products> allProducts = vendorRepository.getAllProducts(uid);
        return allProducts;
    }

    //添加某个产品
    public void addProduct(Products product){
        product.setSellnumber(0);
        product.setResidue(product.getTotal());
        vendorRepository.addProduct(product);
    }

    //修改某个产品


    //删除某个产品

    public void deleteProduct(Integer pid){
        vendorRepository.deleteProduct(pid);
    }

    //查询uid中的所有的品牌

    public List<Brand> getAllBrand(Integer uid){
        List<Brand> allBrand = vendorRepository.getAllBrand(uid);
        return allBrand;
    }
}
