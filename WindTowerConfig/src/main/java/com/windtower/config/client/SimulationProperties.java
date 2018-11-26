package com.windtower.config.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @program: windtower
 * @description: 客户端仿真配置
 * @author: wangtengke
 * @create: 2018-11-26
 **/
@Component
@Slf4j
public class SimulationProperties {
    private static SimulationProperties instance;
    //
    private Properties props;
    //
    private SimulationProperties(){
        init();
    }

    public static SimulationProperties getInstance() {
        if(instance == null){
            instance = new SimulationProperties();
        }
        return instance;
    }
    //
    public void init(){
        //todo 读取Properties配置文件 fix
        try{
            props = new Properties();
            props.load((this.getClass().getResourceAsStream("/conf/simulation.properties")));
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("load simulation config file error!");
        }
    }
    public String getWindTowerID() {
        return this.props.getProperty("WINDTOWER_ID");
    }

    public int getHead(){
        return Integer.valueOf(this.props.getProperty("HEAD"));
    }
    //风场编号
    public int getWindFarmId(){
        return Integer.valueOf(this.props.getProperty("WindFarmId"));
    }
    //塔筒编号
    public int getTowerId(){
        return Integer.valueOf(this.props.getProperty("TowerId"));
    }
    //传感器编号
    public int getSensorId(){
        return Integer.valueOf(this.props.getProperty("SensorId"));
    }
    //X轴数据
    public int getAngleX(){
        return Integer.valueOf(this.props.getProperty("AngleX"));
    }
    //Y轴数据
    public int getAngleY(){
        return Integer.valueOf(this.props.getProperty("AngleY"));
    }
    //数据长度
    public int getDataSize(){
        return Integer.valueOf(this.props.getProperty("DataSize"));
    }
}
