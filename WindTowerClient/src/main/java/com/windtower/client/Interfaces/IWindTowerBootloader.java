package com.windtower.client.Interfaces;

import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;

import java.util.Map;

/**
 * 这里仿照了操作系统的方式来给类进行命名，因为之前整个系统初始化是从大到小，其实不是很符合实际的情况，实际的情况是，首先要获取arm控制权
 * 而后一点点加载网络、串口、各项传感器等模块，最后完成上位机启动
 * 另外，这里面使用spring框架完成对象组装
 * @author wangtengke
 */
public interface IWindTowerBootloader {
    /**
     * 引导
     * @throws Exception
     */
    public void load(Map<String,Object> params) throws Exception;
    /**
     * 引导程序是否成功加载
     * @return
     */
    public boolean isSuccessful();
    /**
     * 初始化串口通信
     * @param blackbox
     * @return
     * @throws Exception
     */
    public IComTrans initCOM(IWindTowerBlackBox blackbox) throws Exception;

    /**
     * 获取日志地址
     * @return
     */
    //public String getLogPath();
    /**
     * 核心资源移交，切记，只能调用一次，首次返回资源对象，之后全部返回null
     * @return
     */
    public IWindTowerRing0Resource exchangeResourceOwner();

}
