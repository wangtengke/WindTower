package com.windtower.client.Controller;

import com.windtower.client.OS.ClientOS;
import com.windtower.config.client.WindTowerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-26
 **/
@Slf4j
@Component
public class SimuBootInit {
    @Autowired
    private ClientOS clientOS;
    public void start(){
        log.info("bootInit|called");
//        WindTowerProperties.getInstance().init();
        String windtowerID= WindTowerProperties.getInstance().getWindTowerID();
        log.info("windtowerID is [{}]",windtowerID);
        try {
            clientOS.loadSimulationBasicAndInit(windtowerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
