package com.windtower.client.OS;

import com.windtower.client.Interfaces.IWindTowerCommonBus;
import com.windtower.client.Interfaces.IWindTowerCommonBusClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: windtower
 * @description: 内部通信总线，在操作系统、服务对象之间传递消息
 * @author: wangtengke
 * @create: 2018-11-26
 **/
@Component
@Lazy
@Slf4j
public class WindTowerCommonBus implements IWindTowerCommonBus{
    /**
     * 名字到总线对象映射
     */
    protected Map<String,IWindTowerCommonBusClient> id2client;

    public WindTowerCommonBus(){
        id2client = new HashMap<>();
    }
    @Override
    public int register(IWindTowerCommonBusClient client) throws Exception {
        String name = client.getRegisterName();
        //如果该名字已有，返回1
        if(id2client.containsKey(name)){
            id2client.put(name, client);
            return 1;
        }
        //改名字没有找到，返回0
        else{
            id2client.put(name, client);
        }
        return 0;
    }

    @Override
    public void send(String src, String des, WindTowerCommonBusMsg msg) throws Exception {
        msg.src = src;
        msg.des = des;
        //如果找到目的地，发送到目的地
        if(id2client.containsKey(des)){
            IWindTowerCommonBusClient client = id2client.get(des);
            //同一个对象的消息处理函数不可重入
            synchronized (client) {
                client.onMessage(msg);
            }

        } else {
            log.info("send|can not find the receiver");
        }
    }
}
