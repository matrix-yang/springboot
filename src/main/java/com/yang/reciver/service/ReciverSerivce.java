package com.yang.reciver.service;


import com.yang.reciver.model.BaseMessage;
import com.yang.reciver.model.Project;
import com.yang.util.SignUtil;
import com.yang.util.TableUtil;
import com.yang.util.XmlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class ReciverSerivce {
    private List<String> currentDrainageRange;
    private List<String> currentProjectName;

    private SearchProject searchProject = new SearchProject();

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
     *
     * @param request
     * @return
     */
    public String autoReply(HttpServletRequest request) {
        BaseMessage baseMessage = reciveMsg(request);
        String content = baseMessage.getContent();
        //监听输入的命令
        if (content.substring(0,1).equals("s")) {
            return dealSearchRoad(content, baseMessage);
        } else if (content.substring(0,1).equals("d")) {
            return dealSearchDrain(content, baseMessage);
        } else if (content.substring(0,1).equals("p")) {
            return dealSearchProject(content, baseMessage);
        } else {
            //监听输入的菜单
            if (content != null && content.equals("1")) {
                String result = replyMsg(baseMessage, "在线监测地址为：\nhttp://wap.ewateryun.com/ewater-mobile/");
                return result;
            }
            if (content != null && content.equals("2")) {
                String result = replyMsg(baseMessage, "请输入道路名：格式<s+道路名>\n例如：  s天河");
                return result;
            }
            if (content != null && content.equals("3")) {
                String result = replyMsg(baseMessage, "刚拿到驾照，从公司到家熄了五次火，最后一次，怎么也启动不了车了，正着急呢，看见一位大爷怒冲冲的拍我的车窗，然后耐心的教我启车！\n" +
                        "在大爷的耐心指导下，终于把车启动了，正准备跟他道谢呢，只见他跑车前，捂着腿躺地上叫着，哎哟，腿断了，你赔。。。");
                return result;
            }
            if (baseMessage.getEvent() != null && baseMessage.getEventKey().equals("SELFSERVIVE")) {
                String result = replyMsg(baseMessage, "回复以下数字进入菜单：\n" + "1.在线监测\n2.查询违章车辆\n3.讲个笑话");
                return result;
            } else if (baseMessage.getEvent() != null && baseMessage.getEventKey().equals("SOMEJOKE")) {
                String result = replyMsg(baseMessage, "刚拿到驾照，从公司到家熄了五次火，最后一次，怎么也启动不了车了，正着急呢，看见一位大爷怒冲冲的拍我的车窗，然后耐心的教我启车！\n" +
                        "在大爷的耐心指导下，终于把车启动了，正准备跟他道谢呢，只见他跑车前，捂着腿躺地上叫着，哎哟，腿断了，你赔。。。");
                return result;
            } else {
                //构建菜单
                String result = replyMsg(baseMessage, "回复以下数字进入菜单：\n" + "1.在线监测\n2.道路查询流域\n3.讲个笑话\n");
                return result;
            }
        }
    }

    /**
     * 把收到的XML消息转换成bean
     *
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
     *
     * @param baseMessage
     * @param content     回复消息的内容,null或者“”将恢复用户发出的消息
     * @return
     */
    public String replyMsg(BaseMessage baseMessage, String content) {
        String resulet = "";
        String toUserName = baseMessage.getToUserName();
        String fromUserName = baseMessage.getFromUserName();
        baseMessage.setToUserName(fromUserName);
        baseMessage.setFromUserName(toUserName);
        if (!baseMessage.getMsgType().equals("image")) {
            baseMessage.setMsgType("text");
        }
        if (content != null && !content.equals("")) {
            baseMessage.setContent(content);
        }
        resulet = XmlManager.buildXml(baseMessage);
        return resulet;
    }

    public String dealSearchRoad(String rex, BaseMessage baseMessage) {
        String road = rex.substring(1);
        this.currentDrainageRange = searchProject.getDrainageRange(road);
        //System.out.println(TableUtil.buildMenuFromList("回复以下编号查询流域","d",this.currentDrainageRange));
        String r = replyMsg(baseMessage, TableUtil.buildMenuFromList("回复以下编号查询流域", "d", this.currentDrainageRange));
        return r;
    }

    public String dealSearchDrain(String rex, BaseMessage baseMessage) {
        String drainId = rex.substring(1);
        String drain = this.currentDrainageRange.get(Integer.parseInt(drainId));
        this.currentProjectName = searchProject.getProjectName(drain);
        //System.out.println(TableUtil.buildMenuFromList("回复以下编号查询项目","p",this.currentProjectName));
        String r = replyMsg(baseMessage, TableUtil.buildMenuFromList("回复以下编号查询项目", "P", this.currentProjectName));
        return r;
    }

    public String dealSearchProject(String rex, BaseMessage baseMessage) {
        String pid = rex.substring(1);
        String pName = this.currentProjectName.get(Integer.parseInt(pid));
        Project project = searchProject.getProjectInfo(pName);
        String r = "";
        try {
            //System.out.println(TableUtil.buildMenuFromBean("查询结果为：",project));
            r = replyMsg(baseMessage, TableUtil.buildMenuFromBean("查询结果为：", project));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
