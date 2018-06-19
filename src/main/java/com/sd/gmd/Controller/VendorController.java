package com.sd.gmd.Controller;


import com.sd.gmd.Service.VendorService;
import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/vendor/products")
    public String getAllProducts(HttpSession session, Model model){
        Integer uid = (Integer) session.getAttribute("uid");

        List<Products> allProducts = vendorService.getAllProducts(uid);

            model.addAttribute("pros",allProducts);
            return "vendor/product";

    }


    //去添加页面
    @GetMapping("/vendor/toProductAddPage")
    public String addProduct(HttpSession session,Products product,Model model){
        Integer uid = (Integer) session.getAttribute("uid");

        List<Brand> allBrand = vendorService.getAllBrand(uid);

        if (allBrand==null||allBrand.size()==0){
            return "vendor/addbrand";
        }else{
            model.addAttribute("brand",allBrand);
            return "vendor/addproduct";
        }
    }


    //删除产品
    @GetMapping("/vendor/delete/{pid}")
    public String deleteProduct(@PathVariable("pid") Integer pid){

        vendorService.deleteProduct(pid);

        return "redirect:/vendor/products";

    }

    //添加产品
    @PostMapping("/vendor/addproduct")
    public String addProduct(Products product,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        product.setUid(uid);
        vendorService.addProduct(product);

        return "redirect:/vendor/products";


    }

}
