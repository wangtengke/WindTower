package com.windtower.client.Service;

import com.windtower.client.Controller.ClientBootInit;
import com.windtower.client.Controller.SimuBootInit;
import com.windtower.client.Interfaces.IWindTowerCommonBus;
import com.windtower.client.Interfaces.IWindTowerUIService;
import com.windtower.client.OS.WindTowerCommonBusMsg;
import com.windtower.client.OS.WindTowerOSContext;
import com.windtower.client.UI.WindTowerModel;
import com.windtower.client.UI.interfaces.IWindTowerEngineObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-27
 **/
@Component
@Slf4j
public class WindTowerUIService implements IWindTowerUIService,Runnable {
    public static String REGISTER_NAME = "uiService";
    public static String SERVICE_NAME = "uiService";
    @Autowired
    protected SimuBootInit simuBootInit;
    @Autowired
    protected ClientBootInit clientBootInit;
    protected WindTowerOSContext context;

    protected boolean isServiceStart = false;

    protected IWindTowerCommonBus bus;

    protected IWindTowerEngineObserver engineObserver;
    @Override
    public void run() {
        try {
            processMessage();
        } catch (Exception e) {
            log.error("processMessage has Exception",e);
        }
    }

    private void processMessage() {
        while (true) {
            try {
                clientBootInit.updateModel(context.getModel());
                Thread.sleep(20);
            }
            catch (Exception e){
                log.error("",e);
            }
        }
    }


    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public int startService(Map<String, Object> params) throws Exception {
        log.info("startService|called");
        if(!this.isServiceStarted()) {
//            this.pd = (DriveUnitPhysicalDescription)params.get("pd");
            this.bus = (IWindTowerCommonBus)params.get("bus");
            this.context = (WindTowerOSContext) params.get("context");
//            this.driveunitID = (String)params.get("driveunitID");
//            this.lastReOpenSerialPortTime = 0;
//            monitorDspConnectionState();
            isServiceStart = true;
            new Thread(this,"WindTowerUIService").start();
        }
        return 0;
    }

    @Override
    public int stopService(Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public IWindTowerCommonBus getBus() {
        return bus;
    }

    @Override
    public boolean isServiceStarted() {
        return isServiceStart;
    }

    @Override
    public String getRegisterName() {
        return REGISTER_NAME;
    }

    @Override
    public void onMessage(WindTowerCommonBusMsg msg) throws Exception {
        log.info("WindTowerUIService onMessage|called");
    }
}
