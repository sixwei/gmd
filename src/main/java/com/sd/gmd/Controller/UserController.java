package com.sd.gmd.Controller;


import com.sd.gmd.Service.UserService;
import com.sd.gmd.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public String checkUser(Users user, Map<String,Object> map, HttpSession session){

        boolean result = userService.checkUser(user);
        if (result){
            session.setAttribute("loginUser",user.getUsername());
            int uid = userService.getUserId(user.getUsername());
            session.setAttribute("uid",uid);

            //得到权限
            Integer roll = user.getRid();
            if (roll.equals(1)){
                return "redirect:/system.html";
            }else if (roll.equals(2)){
                return "redirect:/seller.html";
            }else if (roll.equals(3)){
                return "redirect:/vendor.html";
            }else {
                return "index";
            }

        }
        else{
            map.put("msg","用户名或密码错误");
            return "index";
        }

    }


    @RequestMapping("/user/register")
    public String Register(){
        return "registers";
    }


    //进行用户的注册   先进行user查询是否有重名的情况

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public String addUser(Users user,Map<String,Object> map) {

        boolean checkUser = userService.checkUser(user);
        if (checkUser) {
            map.put("success", "用户名重复");
            return "registers";
        } else {
            boolean b = userService.addUser(user);
            return "redirect:/index.html";


        }
    }


    //退出登录
    @RequestMapping("/user/back")
    public String back(){
        return "index";
    }





}
