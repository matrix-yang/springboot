package com.yang.biz.controller;

import com.yang.biz.service.ReciverSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 监测数据表配置表 controller
 */
@Scope("prototype")
@Controller
@RequestMapping("/reciver")
public class ReciverController {

    @Autowired
    private ReciverSerivce reciverSerivce;

    @RequestMapping("/verify")
    public String saas(HttpServletRequest request) throws Exception{
        return "redirect:http://"+reciverSerivce.getCode(request);
    }
}