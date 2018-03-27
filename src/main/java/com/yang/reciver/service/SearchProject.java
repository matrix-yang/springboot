package com.yang.reciver.service;


import com.yang.reciver.model.BaseMessage;
import com.yang.reciver.model.Project;
import com.yang.util.HttpClientUtil;
import com.yang.util.SignUtil;
import com.yang.util.XmlManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchProject {
    //获得道路上的流域
    public List<String> getDrainageRange(String road){
        List<String> drainageRanges =new ArrayList<>();
        String result=HttpClientUtil.sendGet("http://139.159.246.230:20802/searchDrainageRange/searchDrainageRangeByRoad?road="+road);
        JSONObject jsonObject =JSONObject.fromObject(result);
        JSONArray records=jsonObject.getJSONObject("data").getJSONArray("records");
        for (int i=0;i<records.size();i++){
            JSONObject obj= (JSONObject) records.get(i);
            JSONObject drainageRange=obj.getJSONObject("drainageRange");
            JSONArray jsonArray2=drainageRange.getJSONArray("data");
            for (int j=0;j<jsonArray2.size();j++){
                JSONObject drainageRangeName= (JSONObject) jsonArray2.get(j);
                noRepeatAdd(drainageRanges,drainageRangeName.getString("name"));
            }
        }
        System.out.println(drainageRanges.size());
        return drainageRanges;
    }

    public List<String> getProjectName(String DrainageRange){
        List<String> getProjectNames =new ArrayList<>();
        String result=HttpClientUtil.sendGet("http://139.159.246.230:20802/projectPlan/getProjectNamesByDrainageRange?name="+DrainageRange);
        JSONObject jsonObject =JSONObject.fromObject(result);
        JSONArray data=jsonObject.getJSONArray("data");
        for (int i=0;i<data.size();i++){
            JSONObject obj= (JSONObject) data.get(i);
            noRepeatAdd(getProjectNames,obj.getString("projectName"));
        }
        return getProjectNames;
    }

    public Project getProjectInfo(String projectName){
        String result=HttpClientUtil.sendGet("http://139.159.246.230:20802/projectPlan/getProjectInfo?projectName="+projectName);
        JSONObject jsonObject =JSONObject.fromObject(result);
        JSONObject data=jsonObject.getJSONObject("data");
        Project project = (Project) JSONObject.toBean(data,Project.class);
        System.out.println(project.getDrainageRangeName());
        return project;
    }

    private void noRepeatAdd(List<String> list,String param){
        if (list.size()<=0){
            list.add(param);
        }else {
            boolean notIn=true;
            for (String s:list){
                if (s.equals(param)){
                    notIn=false;
                }
            }
            if (notIn) list.add(param);
        }
    }
}
