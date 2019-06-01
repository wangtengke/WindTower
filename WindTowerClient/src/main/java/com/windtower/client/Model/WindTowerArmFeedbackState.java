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
    public int head;
    //传感器编号
    public int SensorId;
    //X轴数据
    public int AngleX;
    //Y轴数据
    public int AngleY;
    //超声数据
    public String ultraframe;
}
