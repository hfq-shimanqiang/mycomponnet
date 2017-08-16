package com.huifenqi.jedi.api;

import com.huifenqi.jedi.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountTest {
    @Autowired
    Account account;


    @Test
    public void test1() throws Exception {
        final String test = account.test();
        System.out.println(test);
    }

}