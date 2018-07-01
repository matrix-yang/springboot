package com.yang.biz.service;


import com.yang.biz.SignUtil;
import com.yang.util.web.HttpClientUtil;
import net.sf.json.JSONObject;
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

        String code = request.getParameter("code");

        boolean staus= SignUtil.checkSignature(signature,timestamp,nonce);
        System.out.println("验证结果--------------->"+staus);
        if (staus){
            return code;
        }
        return code;
    }

    public String getCode(HttpServletRequest request) {

        String code = request.getParameter("code");
        String url= request.getParameter("state");
        String openid=getFreshToken(code);
        return url+"?openid="+openid;
    }

    public String getFreshToken(String code){
        // 刷新token

        String r = HttpClientUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx890cd6bd6b2e7c8d&secret=e336c8ea549e6cffc4789f0ffeae20dd&code="+code+"&grant_type=authorization_code","utf-8","utf-8");
        JSONObject object = JSONObject.fromObject(r);
        String web_access_token = (String) object.get("access_token");
        String openid=(String) object.get("openid");
        return openid;
    }
}
