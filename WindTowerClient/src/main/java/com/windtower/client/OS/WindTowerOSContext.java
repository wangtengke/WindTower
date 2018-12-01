package com.windtower.client.OS;

import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @program: windtower
 * @description: 操作系统上下文，用来保存多个服务间的上下文信息
 * @author: wangtengke
 * @create: 2018-11-25
 **/
@Component
@Slf4j
public class WindTowerOSContext {


    public void updateDspState(Arm2ComputerNormalFrame frame) {
        log.info("updateDspState|called");
    }
}
