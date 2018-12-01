package com.windtower.client.Model;

import java.io.Serializable;

/**
 * @program: windtower
 * @description: 帧数据的最终状态，这里完成数据的转换
 * @author: wangtengke
 * @create: 2018-12-01
 **/
public class WindTowerArmFeedbackState implements Serializable{
    //数据头 oxaa
    private int head;
    //风场编号
    private int WindFarmId;
    //塔筒编号
    private int TowerId;
    //传感器编号
    private int SensorId;
    //X轴数据
    private int AngleX;
    //Y轴数据
    private int AngleY;
}
