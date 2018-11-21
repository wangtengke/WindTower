package com.windtower.client.JSSC.interfaces;

import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import com.windtower.client.JSSC.arm.Computer2ArmFrame;

/**
 * 黑匣子，记录PC到ARM，ARM到PC的数据帧
 */
public interface IWindTowerBlackBox {
    /**
     * 记录dsp反馈数据
     * @throws Exception
     */
    public void record(Arm2ComputerNormalFrame frame) throws Exception;
    /**
     * 记录向dsp发送数据
     * @throws Exception
     */
    public void record(Computer2ArmFrame frame) throws Exception;
}
