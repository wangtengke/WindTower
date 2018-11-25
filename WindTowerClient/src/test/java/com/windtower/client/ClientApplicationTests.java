package com.windtower.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

public class ClientApplicationTests {

    public static void main(String[] args){
        Map map = new HashMap();
        map.put(1,"wtk");
        map.put(2,"wtk1");
        map.get(1);
        map.put(1,"wtk2");
    }
}
