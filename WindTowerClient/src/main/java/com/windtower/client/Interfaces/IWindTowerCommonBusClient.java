package com.windtower.client.Interfaces;

import com.windtower.client.OS.WindTowerCommonBusMsg;

/**
 * driveunit总线客户端，用于收发数据
 * @author wangtengeke
 *
 */
public interface IWindTowerCommonBusClient {
    /**
     *
     * @return
     */
    public String getRegisterName();
    /**
     *
     * @param msg
     */
    public void onMessage(WindTowerCommonBusMsg msg) throws Exception;
}
