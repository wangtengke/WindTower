package com.windtower.client.JSSC.arm;

import com.windtower.client.OS.WindTowerOSContext;
import com.windtower.config.client.SimulationProperties;
import com.windtower.config.client.WindTowerProperties;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-26
 **/
@Slf4j
public class SimulationComTrans extends AbsComTrans implements Runnable{
    private Object lock;
    //数据帧队列
    protected WindTowerOSContext context;
    public SimulationComTrans() {
        this.lock = new Object();
    }

    @Override
    public boolean connect() throws Exception {
        synchronized (lock) {
            return true;
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

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                Arm2ComputerNormalFrame frame = new Arm2ComputerNormalFrame();

                assemFrame(frame);
                log.info(String.format("head: %d|WindFarmId: %d|TowerId: %d|SensorId: %d|AngleX: %d|AngleY: %d|DataSize: %d",
                        frame.getHead(),frame.getWindFarmId(),frame.getTowerId(),frame.getSensorId(),frame.getAngleX(),frame.getAngleY(),frame.getDataSize()));
                log.info("JsscCommComTrans|arm consumer take frame");
                WindTowerOSContext.arm2ComputerNormalFrames.offer(frame);
                if(observer != null){
                    synchronized(observer) {
                        //todo observer观察者需要实现类
                        Arm2ComputerNormalFrame frame1 = WindTowerOSContext.arm2ComputerNormalFrames.take();
                        observer.processReadedArmFrame(frame1);
                    }
                }
//                filter(frame);
//                if (null != observer && !model.isClearFrame()) { observer.processReadedDSPFrame(frame); }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void assemFrame(Arm2ComputerNormalFrame frame) {
        frame.setHead(SimulationProperties.getInstance().getHead());
        frame.setWindFarmId(SimulationProperties.getInstance().getWindFarmId());
        frame.setTowerId(SimulationProperties.getInstance().getTowerId());
        frame.setSensorId(SimulationProperties.getInstance().getSensorId());
        frame.setAngleX(SimulationProperties.getInstance().getAngleX()+ (int)(Math.random()*100-50));
        frame.setAngleY(SimulationProperties.getInstance().getAngleY()+ (int)(Math.random()*100-50));
        frame.setDataSize(SimulationProperties.getInstance().getDataSize());
    }

    public void init() {

    }
}
