package com.windtower.client.JSSC.arm;

import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: windtower
 * @description: 串口通信
 * @author: wangtengke
 * @create: 2018-11-21
 **/
@Slf4j
public class JsscComTrans extends AbsComTrans implements SerialPortEventListener,Runnable{

    private SerialPort sPort;
    //黑匣子
    private  IWindTowerBlackBox blackBox;
    //数据帧队列
    private BlockingQueue<Arm2ComputerNormalFrame> arm2ComputerNormalFrames;
    //队列长度
    private int FrameQueueLen;
    public JsscComTrans(String windtowerID, String port, int baudRate, int databit, int stopbit, int paritybit, int FrameQueueLen, IWindTowerBlackBox blackBox) {
        this.port = port;
        this.baudRate = baudRate;
        this.databit = databit;
        this.stopbit = stopbit;
        this.paritybit = paritybit;

        log.info(String.format("JsscComTrans|called|port:%s|baudrate:%d|databit:%d|stopbit:%d|parity:%d", port,baudRate,databit,stopbit,paritybit));

        this.blackBox = blackBox;
        arm2ComputerNormalFrames = new LinkedBlockingQueue<Arm2ComputerNormalFrame>(FrameQueueLen);
        this.FrameQueueLen = FrameQueueLen;
        new Thread(this,"ArmFrame recv|DriveunitID:"+windtowerID).start();
    }
    @Override
    public boolean connect() throws Exception {
        log.info("connect|called");
        try{
            if(this.isConnected){
                return true;
            }
            //新建一个串口类
            sPort = new SerialPort(port);
            //判断是否打开串口
            boolean isOpened = sPort.openPort();
            if(!isOpened){
                return false;
            }
            //是否设置串口参数
            boolean isParam = sPort.setParams(baudRate, databit, stopbit, paritybit);
            if(!isParam){
                return false;
            }
            sPort.addEventListener(this,SerialPort.MASK_RXCHAR);
            isConnected = true;
            return true;
        }
        catch (Exception e) {
            log.error("connect failed",e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean close() throws Exception {
        return false;
    }

    @Override
    public boolean openPort() {
        return false;
    }

    @Override
    public boolean reconnect() throws Exception {
        return false;
    }

    @Override
    public boolean write(Computer2ArmFrame frame) throws Exception {
        return false;
    }

    //启动串口程序
    @Override
    public void run() {

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }
}
