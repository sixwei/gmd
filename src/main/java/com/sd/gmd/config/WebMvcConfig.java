package com.sd.gmd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



    @Autowired
    private LoginHandleInterceptor loginHandleInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");



        //注册页面的跳转
        registry.addViewController("/user/register").setViewName("registers");

        //注册成功之后的重定向
        registry.addViewController("/success.html").setViewName("index");

        //管理员
        registry.addViewController("/system.html").setViewName("system");

        //对于借卖方的跳转
        registry.addViewController("/seller.html").setViewName("seller");
        //品牌商
        registry.addViewController("/vendor.html").setViewName("vendor");



    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/user/login","/user/register","/user/register");

    }








}
