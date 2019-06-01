package com.windtower.client.JSSC.arm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Arrays;

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
    //标识符 正确值 111
    public byte head;
    //命令字
    public byte command;
    //传感器地址
    public byte SensorId;
    //X轴数据
    public int AngleX;
    //Y轴数据
    public int AngleY;
    //数据长度
    public byte DataSize;

    public byte Checksum;

    public String ultraframe;
    public Arm2ComputerNormalFrame(){}
    public Arm2ComputerNormalFrame(byte[] content) {
        if(content.length>17){
            head = 1;
            ultraframe = Arrays.toString(content);
        }
        else {
            bytes = new byte[17];
            for (int i = 0; i < 17; i++) {
                bytes[i] = content[i];
            }
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                DataInputStream dis = new DataInputStream(bais);
                head = dis.readByte();
                DataSize = dis.readByte();
                SensorId = dis.readByte();
                command = dis.readByte();
                String s = "";
                for (int i = 0; i < 4; i++) {
                    int temp = dis.readByte() & 0xff;
                    String hex = Integer.toHexString(temp);
                    if (hex.length() < 2) hex = "0" + hex;
                    s += hex;
                }
                if (s.charAt(0) == '1') {
                    AngleX = -Integer.valueOf(s.substring(1));
                } else {
                    AngleX = Integer.valueOf(s.substring(1));
                }
                s = "";
                for (int i = 0; i < 4; i++) {
                    int temp = dis.readByte() & 0xff;
                    String hex = Integer.toHexString(temp);
                    if (hex.length() < 2) hex = "0" + hex;
                    s += hex;
                }
                if (s.charAt(0) == '1') {
                    AngleY = -Integer.valueOf(s.substring(1));
                } else {
                    AngleY = Integer.valueOf(s.substring(1));
                }
//            AngleX      = dis.readInt();
//            AngleY      = dis.readInt();
//            Checksum    = dis.readByte();
                log.info(String.format("head: %d|DataSize: %d|SensorId: %d|command: %d|AngleX: %d|AngleY: %d|Checksum: %d",
                        head, DataSize, SensorId, command, AngleX, AngleY, Checksum));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
