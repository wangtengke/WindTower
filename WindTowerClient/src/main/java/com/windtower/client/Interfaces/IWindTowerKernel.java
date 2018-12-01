package com.windtower.client.Interfaces;

import java.util.Map;

public interface IWindTowerKernel extends IWindTowerCommonBusClient,IWindTowerOperator{
    /**
     *
     * @param resource
     */
    public void load(IWindTowerBootloader bootloader, Map<String,Object> params);
    /**
     *
     */
    public IWindTowerKernelOwner getOwner();
    /**
     *
     */
    public void setOwner(IWindTowerKernelOwner newOwner);
}
