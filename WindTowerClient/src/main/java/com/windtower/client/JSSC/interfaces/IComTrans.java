package com.windtower.client.JSSC.interfaces;

import com.windtower.client.JSSC.arm.Computer2ArmFrame;

/**
 * @author wtk
 */
public interface IComTrans {
    /**
     * 设置串口通信观察者
     * @param observer 指定的观察者对象
     */
    public void setObserver(IComTransObserver observer);
    /**
     * 获取总的通信帧数
     * @return 总通信帧数
     */
    public int getTotalFrame();
    /**
     * 连接串口
     * @return 成功返回true
     * @throws Exception
     */
    public boolean connect() throws Exception;
    /**
     * 串口重连方法
     * @return 成功返回true
     * @throws Exception
     */
    public boolean reconnect() throws Exception;
    /**
     * 关闭串口
     * @return
     */
    public boolean close() throws Exception;
    /**
     * 开启串口
     * @return
     */
    public boolean openPort();
    /**
     * 一帧数据
     * @param frame
     */
    public boolean write(Computer2ArmFrame frame) throws Exception;
    /**
     * 通过校验和位验证数据正确性
     *
     * @param msgPack
     * @param state
     * @return
     */
    public boolean checkSumFrame(byte[] msgPack, int state);
}
