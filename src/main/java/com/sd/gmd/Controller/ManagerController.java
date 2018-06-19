package com.sd.gmd.Controller;

import com.sd.gmd.Service.ManagerService;
import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ManagerController {


    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/man/brands")
    public String getProduct(Model model){
        List<Brand> brands = managerService.getBrands();
        model.addAttribute("brands",brands);
        return "manager/brandlist";
    }

    @GetMapping(value = "/man/users")
    public String getUsers(Model model){
        List<Users> users = managerService.getUsers();
        model.addAttribute("users",users);
        return "manager/userlist";
    }
}
