package com.windtower.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ClientApplicationTests {

        static class Parking{
            //信号量
            private Semaphore semaphore;

            Parking(int count){
                semaphore = new Semaphore(count);
            }

            public void park(){
                try {
                    //获取信号量
                    semaphore.acquire();
                    long time = (long) (Math.random() * 10);
                    System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "秒..." );
                    Thread.sleep(time);
                    System.out.println(Thread.currentThread().getName() + "开出停车场...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        }


        static class Car extends Thread {
            Parking parking ;

            Car(Parking parking){
                this.parking = parking;
            }

            @Override
            public void run() {
                parking.park();     //进入停车场
            }
        }

        public static void main(String[] args){
            Parking parking = new Parking(3);

            for(int i = 0 ; i < 5 ; i++){
                new Car(parking).start();
            }
        }
}