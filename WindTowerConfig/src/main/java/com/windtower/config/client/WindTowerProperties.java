package com.windtower.config.client;

import java.util.Properties;

/**
 * @program: windtower
 * @description: 客户端配置文件类
 * @author: wangtengke
 * @create: 2018-11-21
 **/
public class WindTowerProperties {
    private static WindTowerProperties instance;

    private Properties props;

    private WindTowerProperties(){
        init();
    }

    public static WindTowerProperties getInstance() {
        if(instance == null){
            instance = new WindTowerProperties();
        }
        return instance;
    }

    public void init(){
        //todo 读取Properties配置文件
        try{
            props = new Properties();

        }
        catch (Exception e){

        }

    }

    public String getWindTowerID() {
        return this.props.getProperty("WINDTOWER_ID");
    }
}
