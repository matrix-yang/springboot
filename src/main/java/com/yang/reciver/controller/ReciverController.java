package com.yang.reciver.controller;

import com.yang.reciver.service.ReciverSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public Object verify(HttpServletRequest request) {
        try {

            return reciverSerivce.verify(request);
        } catch (Exception e) {
            return "error";
        }
    }
}