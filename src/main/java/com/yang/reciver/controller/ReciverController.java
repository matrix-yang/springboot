package com.yang.reciver.controller;

import com.yang.reciver.model.BaseMessage;
import com.yang.reciver.service.ReciverSerivce;
import com.yang.util.XmlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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
    public String saas(HttpServletRequest request) throws Exception{
        String echostr = request.getParameter("echostr");
        if (echostr!=null) return reciverSerivce.verify(request);

        return dealMessage(request);
    }

    public String dealMessage(HttpServletRequest request){
        return reciverSerivce.autoReply(request);
    }
}