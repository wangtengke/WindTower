package com.windtower.client.Interfaces;

import java.util.Map;

public interface IOSService extends IWindTowerCommonBusClient{
    /**
     *
     * @return
     */
    public String getServiceName();
    /**
     *
     * @return
     * @throws Exception
     */
    public int startService(Map<String,Object> params) throws Exception;
    /**
     *
     * @return
     */
    public int stopService(Map<String,Object> params) throws Exception;
    /**
     *
     * @return
     */
    public IWindTowerCommonBus getBus();
    /**
     *
     * @return
     */
    public boolean isServiceStarted();
}
