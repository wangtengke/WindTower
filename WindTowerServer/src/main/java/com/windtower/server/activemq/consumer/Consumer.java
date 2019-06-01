package com.windtower.server.activemq.consumer;

import com.alibaba.fastjson.JSON;
import com.windtower.server.Entity.ServerFrame;
import com.windtower.server.Repository.FrameServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: springboot-all
 * @description: 消息消费者
 * @author: wangtengke
 * @create: 2018-12-03
 **/
@Component
@Slf4j
public class Consumer {
    @Resource
    FrameServerRepository frameServerRepository;
    public ExecutorService frame2DB = Executors.newSingleThreadExecutor();
    @JmsListener(destination = "sample.queue")
    @SendTo("callback")
    public String receiveQueue(String text, Session session) throws JMSException {
        try {
            ServerFrame frame = JSON.parseObject(text, ServerFrame.class);
            frame2DB.submit(()->{
                ServerFrame frame1 = new ServerFrame();
                frame1.setTime(frame.getTime());
                frame1.setAngleX(frame.getAngleX());
                frame1.setAngleY(frame.getAngleY());
//                frame1.setBytes(frame.getBytes());
//                frame1.setDataSize(frame.getDataSize());
                frame1.setHead(frame.getHead());
                frame1.setSensorId(frame.getSensorId());
//                frame1.setTowerId(frame.getTowerId());
//                frame1.setWindFarmId(frame.getWindFarmId());
                frameServerRepository.saveAndFlush(frame1);
            });
            log.info(frame.toString());

            return "callback" + text;
        }
        catch (Exception e){
            session.recover();
            return "error";
        }

    }

//    @JmsListener(destination = "sample.queue")
    public void receiveQueue2(String text) {
        String text2 = text + "1";
        System.out.println(text + " 2");
    }

    @JmsListener(destination = "topic",containerFactory = "topicContainerFactory1")
    @SendTo("callback")
    public String receiveTopic(String text) {
        System.out.println(text + " 1");
        return "callback topic 1";
    }

    @JmsListener(destination = "topic",containerFactory = "topicContainerFactory2")
    @SendTo("callback")
    public String receiveTopic2(String text) {
        System.out.println(text + " 2");
        return "callback topic 2";
    }
}
