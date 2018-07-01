package com.yang.biz.controller;

import com.yang.biz.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("prototype")
@Controller
@RequestMapping("/Friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @RequestMapping("/add")
    public void add(){
        friendService.create();
    }
}
