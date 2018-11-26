package com.windtower.client;

import com.windtower.client.Controller.ClientBootController;
import com.windtower.client.Controller.ClientBootInit;
import com.windtower.client.Controller.SimuBootInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;
@Component
@Slf4j
public class ClientRunner implements CommandLineRunner{
    @Autowired
    protected ClientBootInit clientBootInit;
    @Autowired
    protected SimuBootInit simuBootInit;
    @Override
    public void run(String... args) throws Exception {
        try{
            String programToRun = "-fu";
            if (args.length >= 1) {
                programToRun = args[0];
            }
            if("-fu".equalsIgnoreCase(programToRun)){
                clientBootInit.start();
                log.info("-fu start!");
            }
            else if("-su".equalsIgnoreCase(programToRun)){
                simuBootInit.start();
                log.info("-su start!");
            }
            else {
                throw new RuntimeException("string is not right!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("Client run error+{}",e);
        }

    }
}
