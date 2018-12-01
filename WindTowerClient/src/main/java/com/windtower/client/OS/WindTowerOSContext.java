package com.windtower.client.OS;

import com.windtower.client.JSSC.arm.Arm2ComputerNormalFrame;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: windtower
 * @description: 操作系统上下文，用来保存多个服务间的上下文信息
 * @author: wangtengke
 * @create: 2018-11-25
 **/
@Component
@Slf4j
@Data
public class WindTowerOSContext {
    //数据帧队列
    public static BlockingQueue<Arm2ComputerNormalFrame> arm2ComputerNormalFrames = new LinkedBlockingQueue<Arm2ComputerNormalFrame>();
    public void updateArmState(Arm2ComputerNormalFrame frame) {
        log.info("updateArmState|called");

    }
}
