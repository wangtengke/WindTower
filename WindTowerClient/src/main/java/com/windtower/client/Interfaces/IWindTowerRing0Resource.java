package com.windtower.client.Interfaces;

import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;

public interface IWindTowerRing0Resource {
    /**
     *
     * @return
     */
    public IComTrans getComTranser();
    /**
     *
     * @param comTranser
     */
    public void setComTranser(IComTrans comTranser);
    /**
     *
     * @return
     */
    public IWindTowerBlackBox getBlackbox();
    /**
     *
     * @param blackbox
     */
    public void setBlackbox(IWindTowerBlackBox blackbox);

}
