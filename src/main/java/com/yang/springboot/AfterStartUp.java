package com.yang.springboot;
import com.yang.biz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AfterStartUp implements CommandLineRunner {
    public void run(String... strings) throws Exception {
        System.out.println("-------------------we in--------------------");
    }
}
