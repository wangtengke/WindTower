package com.windtower.client.JSSC.arm;

import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IComTransObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: windtower
 * @description: 串口抽象类
 * @author: wangtengke
 * @create: 2018-11-21
 **/
@Slf4j
public abstract class AbsComTrans implements IComTrans {
    protected String port;
    protected int baudRate;
    protected int databit;
    protected int stopbit;
    protected int paritybit;
    protected int totalFrame;
    protected boolean isConnected = false;
    protected IComTransObserver observer;

    @Override
    public void setObserver(IComTransObserver observer) {
        this.observer = observer;
    }

    public int getTotalFrame() {
        return totalFrame;
    }
    /**
     * 连接串口
     * @return 成功返回true
     * @throws Exception
     */
    public abstract boolean connect() throws Exception;
    /**
     * 关闭串口
     * @return
     * @throws Exception
     */
    public abstract boolean close() throws Exception;

    public abstract boolean reconnect() throws Exception;

    /**
     * 写一帧数据
     * @param frame
     * @throws Exception
     */
    public abstract boolean write(Computer2ArmFrame frame) throws Exception;
    /**
     * 通过校验和位验证数据正确性
     *
     * @param msgPack
     * @param state
     * @return
     */
    public boolean checkSumFrame(byte[] msgPack, int state) {
        //todo 校验数据帧需要根据实际情况
        byte checksum = 0;
        switch (state) {
            // 正常帧，32个字节
            case 2:
                for (int i = 0; i < 46; i++) {
                    checksum += msgPack[i];
                }
                return checksum == msgPack[46];
            // 测试帧，33个字节
            case 3:
                for (int i = 0; i < 47; i++) {
                    checksum += msgPack[i];
                }
                return checksum == msgPack[47];
        }
        return false;
    }
}
