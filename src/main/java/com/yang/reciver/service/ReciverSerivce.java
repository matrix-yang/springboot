package com.yang.reciver.service;


import com.yang.reciver.SignUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReciverSerivce {
    public String verify(HttpServletRequest request) {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");


        boolean staus= SignUtil.checkSignature(signature,timestamp,nonce);
        System.out.println("验证结果--------------->"+staus);
        return echostr;
    }
}
