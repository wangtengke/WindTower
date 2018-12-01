package com.windtower.client.Service;

import com.windtower.client.Interfaces.IWindTowerCommonBus;
import com.windtower.client.Interfaces.IWindTowerStatusMonitorService;
import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import com.windtower.client.OS.WindTowerCommonBusMsg;
import com.windtower.client.OS.WindTowerCommonBusMsgType;
import com.windtower.client.OS.WindTowerOSContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-01
 **/
@Component
@Slf4j
public class WindTowerStatusMonitorService implements IWindTowerStatusMonitorService {

    public static String REGISTER_NAME = "statusMonitor";
    public static String SERVICE_NAME = "statusMonitor";

    protected WindTowerOSContext context;

    protected boolean isServiceStart = false;

    protected IWindTowerCommonBus bus;
    @Override
    public String getServiceName() {
        return null;
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
        }
        return 0;
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
        return REGISTER_NAME;
    }

    @Override
    public void onMessage(WindTowerCommonBusMsg msg) throws Exception {
        log.info(String.format("onMessage|called|type:%s|isStart:%b", msg.type.toString(), this.isServiceStart));
        WindTowerCommonBusMsgType type = msg.type;
        if(type.equals(WindTowerCommonBusMsgType.ARM_FRAME_REPORT)) {
            Object data = msg.params.get("frame");
            Arm2ComputerNormalFrame frame = (Arm2ComputerNormalFrame)data;
            if(this.isServiceStart) {
                onNormalMsgArrival(frame);
                this.context.updateArmState(frame);
            } else {
                log.info("onMessage|service is not start");
            }
        }
    }
    private void onNormalMsgArrival(Arm2ComputerNormalFrame frame) {
        log.info(String.format("onNormalMsgArrival|frame:%s",frame.toString()));

    }
    @Override
    public void startWindTowerStatusMonitor() {

    }

    @Override
    public void stopWindTowerStatusMonitor() {

    }
}
