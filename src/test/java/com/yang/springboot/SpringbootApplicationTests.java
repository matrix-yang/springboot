package com.yang.springboot;

import com.yang.reciver.model.BaseMessage;
import com.yang.reciver.service.ReciverSerivce;
import com.yang.reciver.service.SearchProject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() {
		//SearchProject searchProject=new SearchProject();
		//searchProject.getDrainageRange("天河");
		//searchProject.getProjectInfo("天河立交桥底");
		//searchProject.getProjectName("沙河涌流域");

		ReciverSerivce reciverSerivce=new ReciverSerivce();
		try {
			reciverSerivce.dealSearchRoad("s天河",null);
		}catch (Exception e){
			e.printStackTrace();
		}
		try {
			reciverSerivce.dealSearchDrain("d1",null);
		}catch (Exception e){
			e.printStackTrace();
		}
		try {
			reciverSerivce.dealSearchProject("p1",null);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
