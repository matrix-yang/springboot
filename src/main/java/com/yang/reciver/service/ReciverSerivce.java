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
        if (content!=null&&content.equals("1")){
            String result=replyMsg(baseMessage,"在线监测地址为：\nhttp://wap.ewateryun.com/ewater-mobile/");
            return result;
        }
        if (content!=null&&content.equals("2")){
            String result=replyMsg(baseMessage,"此处应该调用其他服务器接口开始查询，然后返回结果");
            return result;
        }
        if (content!=null&&content.equals("3")){
            String result=replyMsg(baseMessage,"刚拿到驾照，从公司到家熄了五次火，最后一次，怎么也启动不了车了，正着急呢，看见一位大爷怒冲冲的拍我的车窗，然后耐心的教我启车！\n" +
                    "在大爷的耐心指导下，终于把车启动了，正准备跟他道谢呢，只见他跑车前，捂着腿躺地上叫着，哎哟，腿断了，你赔。。。");
            return result;
        }
        if (baseMessage.getEvent()!=null&&baseMessage.getEventKey().equals("SELFSERVIVE")){
            String result=replyMsg(baseMessage,"回复以下数字进入菜单：\n" + "1.在线监测\n2.查询违章车辆\n3.讲个笑话");
            return result;
        }else if (baseMessage.getEvent()!=null&&baseMessage.getEventKey().equals("SOMEJOKE")){
            String result=replyMsg(baseMessage,"刚拿到驾照，从公司到家熄了五次火，最后一次，怎么也启动不了车了，正着急呢，看见一位大爷怒冲冲的拍我的车窗，然后耐心的教我启车！\n" +
                    "在大爷的耐心指导下，终于把车启动了，正准备跟他道谢呢，只见他跑车前，捂着腿躺地上叫着，哎哟，腿断了，你赔。。。");
            return result;
        }else {
            String result=replyMsg(baseMessage,"回复以下数字进入菜单：\n" + "1.在线监测\n2.查询违章车辆\n3.讲个笑话");
            return result;
        }
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
        if (!baseMessage.getMsgType().equals("image")){
            baseMessage.setMsgType("text");
        }
        if (content!=null&&!content.equals("")){
            baseMessage.setContent(content);
        }
        resulet = XmlManager.buildXml(baseMessage);
        return resulet;
    }
}
