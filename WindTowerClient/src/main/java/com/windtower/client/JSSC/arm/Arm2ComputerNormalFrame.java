package com.windtower.client.JSSC.arm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * @program: windtower
 * @description: Arm到PC的正常数据帧
 * @author: wangtengke
 * @create: 2018-11-21
 **/
@Slf4j
@Data
public class Arm2ComputerNormalFrame {
    //字节数据
    public byte [] bytes;
    //数据头 正确值 111
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
    //数据长度
    private int DataSize;
    public Arm2ComputerNormalFrame(){}
    public Arm2ComputerNormalFrame(byte[] content) {
        bytes = new byte[28];
        for(int i=0;i<28;i++){
            bytes[i] = content[i];
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bais);
            head        = dis.readInt();
            WindFarmId  = dis.readInt();
            TowerId     = dis.readInt();
            SensorId    = dis.readInt();
            AngleX      = dis.readInt();
            AngleY      = dis.readInt();
            DataSize    = dis.readInt();
            log.info(String.format("head: %d|WindFarmId: %d|TowerId: %d|SensorId: %d|AngleX: %d|AngleY: %d|DataSize: %d",
                    head,WindFarmId,TowerId,SensorId,AngleX,AngleY,DataSize));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
