package com.windtower.client.JSSC.arm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * @program: windtower
 * @description: 超声数据帧
 * @author: wangtengke
 * @create: 2019-05-31
 **/
@Slf4j
@Data
public class Arm2ComputerUltrasoundFrame {
    public byte[] bytes;

    public Arm2ComputerUltrasoundFrame(){}

    public Arm2ComputerUltrasoundFrame(byte[] content){
        bytes = new byte[4096];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = content[i];
        }
    }

}
