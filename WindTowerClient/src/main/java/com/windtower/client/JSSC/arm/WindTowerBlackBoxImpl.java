package com.windtower.client.JSSC.arm;

import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-21
 **/
public class WindTowerBlackBoxImpl implements IWindTowerBlackBox {
    private String driveunitId;
    private volatile Integer lastDate;

    public WindTowerBlackBoxImpl(String driveunitId){
        //todo 黑匣子功能待完善
    }
    @Override
    public void record(Arm2ComputerNormalFrame frame) throws Exception {

    }

    @Override
    public void record(Computer2ArmFrame frame) throws Exception {

    }
}
