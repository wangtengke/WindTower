package com.windtower.client.OS;

import java.util.Map;

/**
 * @program: windtower
 * @description: windtower通用总线传输的消息对象
 * @author: wangtengke
 * @create: 2018-11-26
 **/
public class WindTowerCommonBusMsg {
    public WindTowerCommonBusMsgType type;
    public String src;
    public String des;
    public Map<String,Object> params;
    public Object data;

}
