package com.windtower.client.Interfaces;

import com.windtower.client.OS.WindTowerCommonBusMsg;

/**
 * 通用数据交换总线，用于不同的服务、操作系统、内核之间进行信息交换
 * @author wangtengke
 *
 */
public interface IWindTowerCommonBus {
    /**
     *
     * @param client
     * @return
     */
    public int register(IWindTowerCommonBusClient client) throws Exception;
    /**
     * @throws Exception
     *
     */
    public void send(String src, String des, WindTowerCommonBusMsg msg) throws Exception;
}
