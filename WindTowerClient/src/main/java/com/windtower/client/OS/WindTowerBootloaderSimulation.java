package com.windtower.client.OS;

import com.windtower.client.Interfaces.IWindTowerBootloader;
import com.windtower.client.JSSC.arm.SimulationComTrans;
import com.windtower.client.JSSC.arm.WindTowerBlackBoxImpl;
import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-26
 **/
@Slf4j
@Component
public class WindTowerBootloaderSimulation implements IWindTowerBootloader {

    private boolean loaded;
    @Autowired
    protected WindTowerRing0Resource resource;
    @Override
    public void load(Map<String, Object> params) throws Exception {
        if (!loaded) {
            log.info("init|blackbox init");
            //	String logFilePath = this.getLogPath();
            String windtowerID = (String)params.get("windtowerID");
            WindTowerOSContext context =(WindTowerOSContext)params.get("context");

            //初始化黑匣子
            IWindTowerBlackBox blackbox = new WindTowerBlackBoxImpl(windtowerID);
            resource.setBlackbox(blackbox);

            //初始化串口通信
            IComTrans com = initCOM(blackbox);
            resource.setComTranser(com);
            loaded = true;
        }

    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public IComTrans initCOM(IWindTowerBlackBox blackbox) throws Exception {
        SimulationComTrans comTranser = new SimulationComTrans();
        comTranser.init();
        comTranser.connect();
        new Thread(comTranser).start();
        return comTranser;
    }
}
