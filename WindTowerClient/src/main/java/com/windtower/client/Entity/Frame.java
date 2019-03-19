package com.windtower.client.Entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: windtower
 * @description: 帧数据实体类
 * @author: wangtengke
 * @create: 2018-12-22
 **/
@Component
@Entity
@Data
@Table(name="frame")
public class Frame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String time;
    @Column(nullable = true)
    public byte [] bytes;
    //数据头 正确值 111
    @Column(nullable = false)
    private int head;
    //风场编号
    @Column(nullable = false)
    private int WindFarmId;
    //塔筒编号
    @Column(nullable = false)
    private int TowerId;
    //传感器编号
    @Column(nullable = false)
    private int SensorId;
    //X轴数据
    @Column(nullable = false)
    private int AngleX;
    //Y轴数据
    @Column(nullable = false)
    private int AngleY;
    //数据长度
    @Column(nullable = true)
    private int DataSize;
}
