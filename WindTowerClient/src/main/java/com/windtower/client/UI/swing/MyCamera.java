package com.windtower.client.UI.swing;

import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-28
 **/
public class MyCamera {
    public  OpenCVFrameGrabber grabber;
    public MyCamera(){
        //打开摄像头，获取视频
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.setImageWidth(400);
        grabber.setImageHeight(400);
        this.grabber = grabber;
        //设置图片的宽和高
       try {
           this.grabber.start();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

}
