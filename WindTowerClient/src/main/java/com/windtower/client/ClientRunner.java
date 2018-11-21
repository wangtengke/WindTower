package com.windtower.client;

import com.windtower.client.Controller.ClientBootController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;
@Component
@Slf4j
public class ClientRunner implements CommandLineRunner{
    @Autowired
    private ClientBootController clientBootController;
    @Override
    public void run(String... args) throws Exception {
        try{
            String programToRun = "-fu";
            if (args.length >= 1) {
                programToRun = args[0];
            }
            if("-fu".equalsIgnoreCase(programToRun)){
                clientBootController.start();
                log.info("-fu start!");
            }
            else if("-su".equalsIgnoreCase(programToRun)){
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
