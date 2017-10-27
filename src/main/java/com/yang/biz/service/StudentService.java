package com.yang.biz.service;

import com.yang.biz.entity.StudentEntity;
import com.yang.biz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentEntity add(HttpServletRequest request){
        StudentEntity studentEntity=new StudentEntity();
        studentEntity.setName(request.getParameter("name"));
        studentEntity.setSex(Integer.parseInt(request.getParameter("sex")));
        studentEntity.setTeacher(request.getParameter("teacher"));
        studentRepository.save(studentEntity);
        return studentEntity;
    }

    public List<StudentEntity> getAll(){
        return studentRepository.findAll();
    }
}
