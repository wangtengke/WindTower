package com.windtower.client.UI;

import com.windtower.client.Model.WindTowerArmFeedbackState;

import java.io.Serializable;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-27
 **/
public class WindTowerModel implements Serializable {

    public WindTowerArmFeedbackState armFeedbackState;

    public static double loose = 0.0;

    public WindTowerModel(){
        this.armFeedbackState = new WindTowerArmFeedbackState();
    }
}
