package com.yang.biz.service;

import com.yang.biz.entity.HouseEntity;
import com.yang.biz.entity.StudentEntity;
import com.yang.biz.repository.HouseRepository;
import com.yang.biz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    public HouseEntity add(HttpServletRequest request){
        return null;
    }

    public List<HouseEntity> getAll(){
        return houseRepository.findAll();
    }
}
