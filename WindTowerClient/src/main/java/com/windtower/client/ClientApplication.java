package com.windtower.client;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
//        System.setProperty("java.awt.headless", "false");
//        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        SpringApplication.run(ClientApplication.class, args);

    }
}
