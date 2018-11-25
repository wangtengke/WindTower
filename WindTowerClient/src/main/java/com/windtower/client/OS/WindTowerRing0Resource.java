package com.windtower.client.OS;

import com.windtower.client.Interfaces.IWindTowerRing0Resource;
import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-25
 **/
@Component
public class WindTowerRing0Resource implements IWindTowerRing0Resource {
    private IComTrans comTrans;
    private IWindTowerBlackBox blackBox;

    @Override
    public IComTrans getComTranser() {
        return this.comTrans;
    }

    @Override
    public void setComTranser(IComTrans comTranser) {
        this.comTrans = comTranser;
    }

    @Override
    public IWindTowerBlackBox getBlackbox() {
        return this.blackBox;
    }

    @Override
    public void setBlackbox(IWindTowerBlackBox blackbox) {
        this.blackBox = blackbox;
    }
}
