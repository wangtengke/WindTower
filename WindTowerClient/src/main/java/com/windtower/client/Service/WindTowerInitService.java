package com.windtower.client.Service;

import com.windtower.client.Interfaces.IWindTowerCommonBus;
import com.windtower.client.Interfaces.IWindTowerInitService;
import com.windtower.client.Interfaces.IWindTowerStatusMonitorService;
import com.windtower.client.Interfaces.IWindTowerUIService;
import com.windtower.client.OS.WindTowerCommonBusMsg;
import com.windtower.client.OS.WindTowerOSContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-01
 **/
@Component
@Slf4j
public class WindTowerInitService implements IWindTowerInitService {

    /**
     * 操作系统核心参数列表
     */
    protected  Map<String,Object> params;
    /**
     * 操作系统上下文
     */
    protected WindTowerOSContext context;
    /**
     * 总线
     */
    protected IWindTowerCommonBus bus;

    protected IWindTowerStatusMonitorService windTowerStatusMonitorService;

    protected IWindTowerUIService windTowerUIService;
    protected boolean isServiceStart = false;
    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public int startService(Map<String, Object> params) throws Exception {
        log.info("startService|called");
        if(!this.isServiceStarted()) {
            this.params = params;
            //
            context = (WindTowerOSContext) params.get("context");
//            pd = (DriveUnitPhysicalDescription)params.get("pd");
            bus = (IWindTowerCommonBus) params.get("bus");
            windTowerStatusMonitorService = (IWindTowerStatusMonitorService) params.get(WindTowerStatusMonitorService.SERVICE_NAME);
            windTowerUIService            = (IWindTowerUIService) params.get(WindTowerUIService.SERVICE_NAME);
            //todo 通信服务，与后台交互时需要使用
//            communicator = (IDriveUnitComputerCommunicationService)params.get("communicator");
            try {
                startAllService(params);
                isServiceStart = true;
            } catch(Exception e) {
                log.error("", e);
            }

        }
        return 0;
    }

    private void startAllService(Map<String, Object> params) throws Exception {
        windTowerStatusMonitorService.startService(params);
    }

    @Override
    public int stopService(Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public IWindTowerCommonBus getBus() {
        return null;
    }

    @Override
    public boolean isServiceStarted() {
        return false;
    }

    @Override
    public String getRegisterName() {
        return null;
    }

    @Override
    public void onMessage(WindTowerCommonBusMsg msg) throws Exception {

    }
}
