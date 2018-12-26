package com.windtower.client.OS;

import com.windtower.client.Controller.ClientBootController;
import com.windtower.client.Interfaces.*;
import com.windtower.client.Service.WindTowerStatusMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: windtower
 *
 * @description: 客户端os启动，这个类将启动串口，通信等服务
 *
 * @author: wangtengke
 *
 * @create: 2018-11-21
 **/
@Component
public class ClientOS {
    @Autowired
    protected ClientBootController clientBootController;
    @Autowired
    protected IWindTowerBootloader windTowerBootloader;
    @Autowired
    protected WindTowerBootloaderSimulation windTowerBootloaderSimulation;
    @Autowired
    protected WindTowerOSContext driveUnitOSContext;
    @Autowired
    protected IWindTowerKernel kernel;
    @Autowired
    protected IWindTowerCommonBus windTowerCommonBus;
    @Autowired
    protected IWindTowerStatusMonitorService windTowerStatusMonitorService;
    @Autowired
    protected IWindTowerInitService windTowerInitService;
    private String windtowerID;
    protected Map<String, Object> params = new HashMap<String, Object>();
    public void load(){

    }
    public void loadBasicAndInit(String windtowerID) throws Exception {
        this.windtowerID = windtowerID;
        putParams(true);
        busRegister();
        windTowerBootloader.load(params);
        kernel.load(windTowerBootloader,params);
        windTowerInitService.startService(params);
    }

    public void loadSimulationBasicAndInit(String windtowerID) throws Exception {
        this.windtowerID = windtowerID;
        putParams(true);
        busRegister();
        windTowerBootloaderSimulation.load(params);

        kernel.load(windTowerBootloader,params);
        windTowerInitService.startService(params);

    }

    private void busRegister() throws Exception {
        windTowerCommonBus.register(kernel);
        windTowerCommonBus.register(windTowerStatusMonitorService);
    }

    private void putParams(boolean isSingle) {
        params.put("isSingle", isSingle);
        params.put("context", driveUnitOSContext);
        params.put("windtowerID",windtowerID);
        params.put("bus",windTowerCommonBus);
        params.put(WindTowerStatusMonitorService.SERVICE_NAME,windTowerStatusMonitorService);

    }


}
