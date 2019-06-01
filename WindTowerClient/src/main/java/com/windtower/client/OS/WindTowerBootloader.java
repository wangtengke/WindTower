package com.windtower.client.OS;

import com.windtower.client.Interfaces.IWindTowerBootloader;
import com.windtower.client.Interfaces.IWindTowerRing0Resource;
import com.windtower.client.JSSC.arm.JsscComTrans;
import com.windtower.client.JSSC.arm.WindTowerBlackBoxImpl;
import com.windtower.client.JSSC.interfaces.IComTrans;
import com.windtower.client.JSSC.interfaces.IWindTowerBlackBox;
import com.windtower.config.client.WindTowerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-11-25
 **/
@Slf4j
@Component
public class WindTowerBootloader implements IWindTowerBootloader {
    private boolean loaded;
    @Autowired
    protected WindTowerRing0Resource resource;
    @Override
    public void load(Map<String, Object> params) throws Exception {
        if (!loaded) {
            log.info("init|blackbox init");
            //	String logFilePath = this.getLogPath();
            String windtowerID = (String)params.get("windtowerID");
            WindTowerOSContext context =(WindTowerOSContext)params.get("context");

            //初始化黑匣子
            IWindTowerBlackBox blackbox = new WindTowerBlackBoxImpl(windtowerID);
            resource.setBlackbox(blackbox);

            //初始化串口通信
            IComTrans com = initCOM(blackbox);
            resource.setComTranser(com);

            // dug屏串口通信
//            if(DriveUnitProperties.instance.getDgusCommPortUsed()) {
//                BufferedReader br1 = null;
//                try {
//                    if (context.getMacInfo().size() == 0) {
//                        File file = WalleResourceLoader.getResourceFileFromFile("macInfo.properties", "conf");
//                        br1 = new BufferedReader(new FileReader(file));
//                        for (String line = br1.readLine(); line != null; line = br1.readLine()) {
//                            line = line.trim();
//                            String[] s = line.split("=");
//                            // s[0]--driveunitId
//                            // s[1]--macAddress
//                            context.getMacInfo().put(s[0], s[1]);
//                        }
//                    }
//                }catch (Exception e) {
//                    logger.error("readfile from errorList.properties error", e);
//                }finally {
//                    try {
//                        if (br1 != null) {
//                            br1.close();
//                            br1 = null;
//                        }
//                    } catch (IOException e) {
//                        logger.error("readfile from errorList.properties error", e);
//                    }
//                }
//
//                jcd = dgusConnect();
//                executor = Executors.newScheduledThreadPool(1);
//                executor.scheduleAtFixedRate(new ScheduleTask(), 0, 1, TimeUnit.SECONDS);
//            }


            //this.filters  = (IDspFrameFilter) params.get("filters");
            loaded = true;
        }

    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public IComTrans initCOM(IWindTowerBlackBox blackbox) throws Exception {
        //初始化串口通信
        JsscComTrans comTranser = new JsscComTrans(
                WindTowerProperties.getInstance().getWindTowerID(),
                WindTowerProperties.getInstance().getCommPort(),
                WindTowerProperties.getInstance().getBaudRate(),
                WindTowerProperties.getInstance().getDataBit(),
                WindTowerProperties.getInstance().getStopBit(),
                WindTowerProperties.getInstance().getParity(),
                WindTowerProperties.getInstance().getDspQueueLen(),
                blackbox
        );
        JsscComTrans comTranserUltrasound = new JsscComTrans(
                WindTowerProperties.getInstance().getWindTowerID(),
                WindTowerProperties.getInstance().getCommPortUltrasound(),
                WindTowerProperties.getInstance().getCommBaudRateUltrasound(),
                WindTowerProperties.getInstance().getDataBit(),
                WindTowerProperties.getInstance().getStopBit(),
                WindTowerProperties.getInstance().getParity(),
                WindTowerProperties.getInstance().getDspQueueLen(),
                blackbox
        );
//        comTranser.connect();
        comTranserUltrasound.connect();

        //comTranser.initFilter(filters);


        return comTranser;
    }

    @Override
    public IWindTowerRing0Resource exchangeResourceOwner() {
        //����ַ���浽�м����
        WindTowerRing0Resource retResource = this.resource;
        //������ԴΪnull����ζ�Ÿ÷���ֻ�ܱ�����һ��
        this.resource = null;
        return retResource;
    }
}
