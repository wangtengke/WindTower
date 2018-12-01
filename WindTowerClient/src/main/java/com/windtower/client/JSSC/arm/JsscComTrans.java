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
    //缓存区最大数据数量
    protected final static int RECV_BUF_MAX_LEN = 320;

    private SerialPort sPort;

    /**
     *
     */
    private int state = 0;
    /**
     * 索引值
     */
    private int index = 0;
    /**
     * 数据buf
     */
    private byte[] msgPack = new byte[28];
    //黑匣子
    private  IWindTowerBlackBox blackBox;
    //数据帧队列
    private BlockingQueue<Arm2ComputerNormalFrame> arm2ComputerNormalFrames;
    //队列长度
    private int FrameQueueLen;

    private Object serialLock = new Object();

    private int count = 0;

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
        log.info("close|called");
        try {
            synchronized (serialLock){
                boolean isSuccess = sPort.closePort();
                isConnected = false;
                return isSuccess;
            }
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
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
        while(true) {
            try {

                Arm2ComputerNormalFrame frame = getFrameQueueTake();
                log.info("JsscCommComTrans|arm consumer take frame");

                if(observer != null){
                    synchronized(observer) {
                        //todo observer观察者需要实现类
                        observer.processReadedArmFrame(frame);
                    }
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
    }
    //从数据帧队列中那frame
    private Arm2ComputerNormalFrame getFrameQueueTake() throws Exception {
        int queueLen = this.FrameQueueLen;
        int warningLen = Math.max(queueLen * 4 / 5, 1);
        if(arm2ComputerNormalFrames.size() > warningLen) {
            //dsp2ComputerNormalFrames.clear();
            //?????????????frame
            log.info("getFrameQueueTake|arm2ComputerNormalFrames {} size"+arm2ComputerNormalFrames.size());
            Arm2ComputerNormalFrame frame = null;
            while(arm2ComputerNormalFrames.size() > 0) {
                log.info("dsp2ComputerNormalFrames|discard frame|dsp2ComputerNormalFrames size"+arm2ComputerNormalFrames.size());
                frame = arm2ComputerNormalFrames.take();
            }
            return frame;
        }
        return arm2ComputerNormalFrames.take();
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        int eventVal = serialPortEvent.getEventValue();
        boolean isPortOpened = sPort.isOpened();
        log.info(String.format("serialEvent|called|eventVal:%d|isOpen:%b", eventVal, isPortOpened));
        try {
            int eventValue = serialPortEvent.getEventValue();
            if(eventValue > 0){
                log.info(String.format("serialEvent|buf readed|eventVal:%d", eventValue));
                byte[] buf = sPort.readBytes();
                int bufferSize = buf.length;
                log.info(String.format("serialEvent|buf readed|bufferSize:%d", bufferSize));
                if(bufferSize > RECV_BUF_MAX_LEN) {
                    log.info(String.format("serialEvent|bufLen:%d|Too many bucketId in serial port recv buffer!!", buf.length));
                    //return;
                }
                process(buf);
                log.info("serialEvent|done");
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void process(byte[] buf) {
        try {
            for(int i=0;i<buf.length;i++){
                byte newData = buf[i];
                switch (state){
                    case 0:
                        index = 0;
                        // ??????????
                        if (newData == (byte)0xaa) {
                            state = 1;
                            msgPack[index++] = newData;
                        }
                        break;
                    case 1:
                        msgPack[index++] = newData;
                        if(index==28){
                            if (msgPack[27] == (byte)0x07) {
                                log.info("frame right!");
                                Arm2ComputerNormalFrame frame = new Arm2ComputerNormalFrame(msgPack);
                                boolean isPass = arm2ComputerNormalFrames.offer(frame);
                                log.info(String.format("offer frame to queue:%b",isPass));
                            }
                            state = 0;
                        }
                        else if(index>28){
                            state = 0;
                        }
                        break;
                }
            }
//            Arm2ComputerNormalFrame frame = new Arm2ComputerNormalFrame(buf);
//            for(int i=0;i<buf.length;i++){
//                byte newData = buf[i];
//                switch (state) {
//                    // ?????
//                    case 0:
//                        index = 0;
//                        // ??????????
//                        if (newData == (byte)0xaa) {
//                            state = 1;
//                            msgPack[index++] = newData;
//                        }
//                        break;
//                    // ????????
//                    case 1:
//                        int tag = (newData >> 6) & (byte)0x01;
//                        if (tag == 0) {
//                            state = 2;
//                        } else if (tag == 1) {
//                            state = 2;
//                        }
//                        msgPack[index++] = newData;
//                        break;
//                    // ???????32?????
//                    case 2:
//                        msgPack[index++] = newData;
//                        if (index == 48) {
//                            // ???????????????????
//                            if (msgPack[47] == (byte)0x55) {
////                                frameIntervalAnalyzer.add2Analysis();
//                                if (!checkSumFrame(msgPack, state)) {
//                                    log.info("=== sum error ===");
//                                    continue;
//                                }
//                                Arm2ComputerNormalFrame frame = new Arm2ComputerNormalFrame(msgPack);
//                                //???????
//                                blackBox.record(frame);
//                                totalFrame++;
//							/*if(observer != null){
//								synchronized(observer) {
//									observer.processReadedDSPFrame(frame);
//								}
//							}*/
//                                count = (count > 10000 ? 0 : count++);
//                                log.info(String.format("process|size:%d|count:%d", arm2ComputerNormalFrames.size(),count));
//                                if(frameFilters != null) {
//                                    boolean flags= frameFilters.doFilter(frame);
//                                    logger.info("process|pass doFilter|flags:" + flags);
//                                    if(flags) {
//                                        frame.errno = (short) (0xff & frame.errno);
//                                        boolean isPass = dsp2ComputerNormalFrames.offer(frame);
//                                    }
//                                }
//                                state = 0;
//                            }
//                            // ?????????????
//                            else {
//                                state = 0;
//                            }
//                        }
//                        else if(index>48){
//                            state = 0;
//                        }
//                        break;
//                }
//            }

        } catch (Exception e) {
            //exceptionObserver.onException(DriveUnitExceptionCode.COM_READ_EXCEPTION, "com read error", e,null);
            log.error("",e);
        }
    }
}
