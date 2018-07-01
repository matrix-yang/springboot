package com.yang.biz.service;

import com.yang.biz.model.Friend;
import com.yang.biz.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;
    public void create(){
        Friend friend=new Friend();
        friend.setName("yang");
        friend.setPhoneNum("123");
        friend.setSex("男");
        friendRepository.save(friend);
    }
}
