package com.windtower.client.OS;

import com.windtower.client.Controller.ClientBootController;
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
    protected WindTowerBootloader windTowerBootloader;
    @Autowired
    protected WindTowerBootloaderSimulation windTowerBootloaderSimulation;
    @Autowired
    protected WindTowerOSContext driveUnitOSContext;
    private String windtowerID;
    protected Map<String, Object> params = new HashMap<String, Object>();
    public void load(){

    }
    public void loadBasicAndInit(String windtowerID) throws Exception {
        this.windtowerID = windtowerID;
        putParams(true);
        windTowerBootloader.load(params);
    }

    public void loadSimulationBasicAndInit(String windtowerID) throws Exception {
        this.windtowerID = windtowerID;
        putParams(true);
        windTowerBootloaderSimulation.load(params);
    }
    private void putParams(boolean isSingle) {
        params.put("isSingle", isSingle);
        params.put("context", driveUnitOSContext);
        params.put("windtowerID",windtowerID);

    }


}
