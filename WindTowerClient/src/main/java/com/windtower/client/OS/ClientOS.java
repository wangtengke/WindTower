package com.windtower.client.OS;

import com.windtower.client.Controller.ClientBootController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: windtower
 *
 * @description: 客户端os启动，这个类将启动串口，通信等服务
 *
 * @author: wangtengke
 *
 * @create: 2018-11-21
 **/
@Component
public class ClientOS {
    @Autowired
    ClientBootController clientBootController;
    public void load(){

    }
    public void loadBasicAndInit(String windtowerID){

    }
}
