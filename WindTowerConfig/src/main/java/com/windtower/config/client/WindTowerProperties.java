package com.windtower.config.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @program: windtower
 * @description: 客户端配置文件类
 * @author: wangtengke
 * @create: 2018-11-21
 **/
@Component
@Slf4j
public class WindTowerProperties {
    private static WindTowerProperties instance;
//
    private Properties props;
//
    private WindTowerProperties(){
        init();
    }

    public static WindTowerProperties getInstance() {
        if(instance == null){
            instance = new WindTowerProperties();
        }
        return instance;
    }
//
    public void init(){
        try{
            props = new Properties();
            props.load((this.getClass().getResourceAsStream("/conf/windtower.properties")));
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("load config file error!");
        }
    }

    public String getWindTowerID() {
        return this.props.getProperty("WINDTOWER_ID");
    }
    /*****************************   串口信息    **************************************/
    public String getCommPort() {
        return this.props.getProperty("COMM_PORT");
    }

    public int getBaudRate() {
        return Integer.valueOf(this.props.getProperty("COMM_BAUD_RATE"));
    }

    public int getDataBit() {
        return Integer.valueOf(this.props.getProperty("COMM_DATA_BIT"));
    }

    public int getStopBit() {
        return Integer.valueOf(this.props.getProperty("COMM_STOP_BIT"));
    }

    public int getParity() {
        return Integer.valueOf(this.props.getProperty("COMM_PARITY"));
    }

    public int getDspQueueLen() {
        return Integer.valueOf(this.props.getProperty("dsp_queue_len","20"));
    }
}
