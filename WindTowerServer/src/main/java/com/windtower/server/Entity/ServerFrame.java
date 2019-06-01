package com.windtower.server.Entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: windtower
 * @description: 帧数据实体类
 * @author: wangtengke
 * @create: 2018-12-22
 **/
@Entity
@Data
@Table(name="serverframe")
public class ServerFrame {
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
}
