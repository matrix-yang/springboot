package com.yang.reciver.service;


import com.yang.reciver.model.BaseMessage;
import com.yang.util.SignUtil;
import com.yang.util.XmlManager;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

        boolean staus = SignUtil.checkSignature(signature, timestamp, nonce);
        System.out.println("验证结果--------------->" + staus);
        if (staus) {
            return echostr;
        }
        return "";
    }

    /**
     * 自动回复收到的消息
     * @param request
     * @return
     */
    public String autoReply(HttpServletRequest request){
        BaseMessage baseMessage=reciveMsg(request);
        String content=baseMessage.getContent();
        if (content!=null&&content.equals("在线监测")){
            String result=replyMsg(baseMessage,"在线监测地址为：\nhttp://wap.ewateryun.com/ewater-mobile/");
            return result;
        }
        String result=replyMsg(baseMessage,"");
        return result;
    }

    /**
     * 把收到的XML消息转换成bean
     * @param request
     * @return
     */
    public BaseMessage reciveMsg(HttpServletRequest request) {
        BaseMessage baseMessage = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            baseMessage = (BaseMessage) XmlManager.parserXml(inputStream, BaseMessage.class);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseMessage;
    }

    /**
     * 根据收到的bean构建回复消息的XML
     * @param baseMessage
     * @param content  回复消息的内容,null或者“”将恢复用户发出的消息
     * @return
     */
    public String replyMsg(BaseMessage baseMessage,String content) {
        String resulet = "";
        String toUserName = baseMessage.getToUserName();
        String fromUserName = baseMessage.getFromUserName();
        baseMessage.setToUserName(fromUserName);
        baseMessage.setFromUserName(toUserName);
        if (content!=null&&content.equals("")){
            baseMessage.setContent(content);
        }
        resulet = XmlManager.buildXml(baseMessage);
        return resulet;
    }
}
