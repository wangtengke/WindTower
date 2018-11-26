package com.windtower.client.JSSC.interfaces;

import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;

/**
 * 串口观察者
 * @author wtk
 */
public interface IComTransObserver {
    /**
     * 处理正常数据帧回调函数
     * @param frame 正常数据帧
     */
    public void processReadedArmFrame(Arm2ComputerNormalFrame frame) throws Exception;
}
