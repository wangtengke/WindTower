package com.windtower.client.Controller;

import com.windtower.client.OS.ClientOS;
import com.windtower.config.client.WindTowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 客户端启动
 * @author wtk
 */
@Controller
public class ClientBootController {
    @Autowired
    private ClientOS clientOS;
    public void start(){
        String windtowerID= WindTowerProperties.getInstance().getWindTowerID();
        clientOS.loadBasicAndInit(windtowerID);
    }
}
