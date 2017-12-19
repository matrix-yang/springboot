package com.yang.biz.controller;

import com.yang.biz.entity.StudentEntity;
import com.yang.biz.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/house")
public class HouseController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(path = "/add")
    @ResponseBody
    public StudentEntity add(HttpServletRequest request){
        return studentService.add(request);
    }

    @RequestMapping(path = "/getAll")
    @ResponseBody
    public List<StudentEntity> getAll(){
        return studentService.getAll();
    }

    @RequestMapping(path = "/getAllSize")
    @ResponseBody
    public String getAllSize(HttpServletRequest request){
        System.out.println(request);
        return "mamaipi";
    }

}
