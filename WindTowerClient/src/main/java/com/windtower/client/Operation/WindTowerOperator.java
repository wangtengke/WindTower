package com.windtower.client.Operation;

import com.windtower.client.Interfaces.IArmOpFrameFactory;
import com.windtower.client.Interfaces.IKernelSenderCallback;
import com.windtower.client.Interfaces.IWindTowerOperator;
import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IComTransObserver;

/**
 * @program: windtower
 * @description: 上位机操作数据帧
 * @author: wangtengke
 * @create: 2018-11-26
 **/
public class WindTowerOperator implements IWindTowerOperator, IComTransObserver {


    @Override
    public void init(IArmOpFrameFactory frameFactory, IComTrans comTranser, IKernelSenderCallback kernelSenderCallback, IComTransObserver observers, boolean isSimu) {

    }

    @Override
    public void processReadedArmFrame(Arm2ComputerNormalFrame frame) throws Exception {
        //logger.info(frame.toString());
//        if (null != stateMachine) {
//            stateMachine.onFrameArrival(frame);
//        }
    }
}
