package com.windtower.client.OS;

import com.alibaba.fastjson.JSON;
import com.windtower.client.Entity.Frame;
import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import com.windtower.client.JSSC.arm.Arm2ComputerUltrasoundFrame;
import com.windtower.client.Model.WindTowerArmFeedbackState;
import com.windtower.client.Repository.FrameRepository;
import com.windtower.client.UI.WindTowerModel;
import javafx.util.converter.DateTimeStringConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.windtower.client.activemq.producer.*;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @program: windtower
 * @description: 操作系统上下文，用来保存多个服务间的上下文信息
 * @author: wangtengke
 * @create: 2018-11-25
 **/
@Component
@Slf4j
@Data
public class WindTowerOSContext {
    @Resource
    FrameRepository frameRepository;
    @Resource
    Producer producer;
//    @Resource
//    Frame frame1;
    private WindTowerModel model = new WindTowerModel();
    //数据帧队列
    public static BlockingQueue<Arm2ComputerNormalFrame> arm2ComputerNormalFrames = new LinkedBlockingQueue<Arm2ComputerNormalFrame>();
    //超声数据帧队列
    public static BlockingQueue<Arm2ComputerUltrasoundFrame> arm2ComputerUltrasoundFrames = new LinkedBlockingQueue<Arm2ComputerUltrasoundFrame>();
    public ExecutorService frame2DB = Executors.newSingleThreadExecutor();
    public void updateArmState(Arm2ComputerNormalFrame frame) {
        log.info("updateArmState|called");
        //UI刷新

        WindTowerArmFeedbackState dspFeedbackState = model.armFeedbackState;
        dspFeedbackState.head = frame.head;
        dspFeedbackState.AngleX = frame.AngleX;
        dspFeedbackState.AngleY = frame.AngleY;
        dspFeedbackState.SensorId = frame.SensorId;
        dspFeedbackState.ultraframe = frame.ultraframe;
//        dspFeedbackState. = frame.TowerId;
//        dspFeedbackState.WindFarmId = frame.WindFarmId;
        //存储数据库
        Frame frame1 = new Frame();
        frame2DB.submit(()->{
            frame1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS") .format(new Date()));
            frame1.setAngleX(frame.getAngleX());
            frame1.setAngleY(frame.getAngleY());
            frame1.setHead(frame.getHead());
            frame1.setSensorId(frame.getSensorId());
            frame1.setUltraframe(frame.getUltraframe());
//            frame1.setTowerId(frame.getTowerId());
//            frame1.setWindFarmId(frame.getWindFarmId());
            frameRepository.saveAndFlush(frame1);
        });
//        frame.toString();
        // 向server端发送
//        frame1(null);
        String objectToJson = JSON.toJSONString(frame1);
        producer.send(objectToJson);
    }
}
