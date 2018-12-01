package com.windtower.client.OS;

import com.windtower.client.Interfaces.*;
import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import com.windtower.client.JSSC.interfaces.IComTransObserver;
import com.windtower.client.Operation.WindTowerOperator;
import com.windtower.client.Service.WindTowerStatusMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: windtower
 * @description: windtower内核，上层的操作系统如果进行关键调用，会陷入到内核当中，由内核完成，并将结果反馈给上位机
 * @author: wangtengke
 * @create: 2018-12-01
 **/
@Component
@Slf4j
public class WindTowerKernel  extends WindTowerOperator implements
        IWindTowerKernel, IComTransObserver {

    public static String REGISTER_NAME = "kernel";

    protected IWindTowerCommonBus bus;

    protected IWindTowerRing0Resource resource;

    protected WindTowerOSContext context;
    @Override
    public void load(IWindTowerBootloader bootloader, Map<String, Object> params) {
        //
        this.bus = (WindTowerCommonBus) params.get("bus");

        this.context = (WindTowerOSContext) params.get("context");
        this.resource = bootloader.exchangeResourceOwner();
        this.resource.getComTranser().setObserver(this);
    }

    @Override
    public IWindTowerKernelOwner getOwner() {
        return null;
    }

    @Override
    public void setOwner(IWindTowerKernelOwner newOwner) {

    }
    @Override
    public String getRegisterName() {
        return REGISTER_NAME;
    }

    @Override
    public void onMessage(WindTowerCommonBusMsg msg) throws Exception {

    }

    @Override
    public void processReadedArmFrame(Arm2ComputerNormalFrame frame) throws Exception {
        log.info(frame.toString());
        WindTowerCommonBusMsg msg = new WindTowerCommonBusMsg();
        msg.type = WindTowerCommonBusMsgType.ARM_FRAME_REPORT;
        msg.params = new HashMap<String, Object>();
        msg.params.put("frame", frame);
        bus.send(this.getRegisterName(),
                WindTowerStatusMonitorService.REGISTER_NAME, msg);

    }
}
