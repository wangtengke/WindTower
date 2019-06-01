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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String time;
    //数据头 正确值 111
    @Column(nullable = false)
    private int head;
    //传感器编号
    @Column(nullable = false)
    private int SensorId;
    //X轴数据
    @Column(nullable = false)
    private float AngleX;
    //Y轴数据
    @Column(nullable = false)
    private float AngleY;
    @Column(length = 100000)
    private String ultraframe;
}
