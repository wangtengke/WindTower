package com.windtower.client.Interfaces;

import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IComTransObserver;

public interface IWindTowerOperator {
    /**
     *
     */
    void init(IArmOpFrameFactory frameFactory, IComTrans comTranser, IKernelSenderCallback kernelSenderCallback,
              IComTransObserver observers, boolean isSimu);
}
