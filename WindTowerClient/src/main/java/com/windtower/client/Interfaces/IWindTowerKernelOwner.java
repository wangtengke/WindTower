package com.windtower.client.Interfaces;

/**
 * 内核属主接口
 * @author wangtengke
 *
 */
public interface IWindTowerKernelOwner {
    /**
     * 获得了内核的控制权
     */
    public void onGetOwnership(IWindTowerKernel kernel);
    /**
     * 失去了内核的控制权
     */
    public void onLostOwnership();
    /**
     * 获取拥有者名称
     */
    public String getOwnerName();

    public void onExceptionFrame();

    public IWindTowerKernel getKernel();
}
