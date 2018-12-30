package cn.WindElectricity.VideoDemo;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws Exception, IOException, InterruptedException {

        //打开摄像头，获取视频
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        //设置图片的宽和高
        grabber.setImageWidth(800);
        grabber.setImageHeight(640);
        grabber.start();

        //保存截图
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage grabbedImage = converter.convertToIplImage(grabber.grab());
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        // frame.setSize(800, 600);
        //frame.setBounds(200, 100, 640, 640);
        // 设置操作界面
        JPanel contentPane = new JPanel();
        //contentPane.setBounds(0, 0, 640, 640);
        Container contentPane2 = frame.getContentPane();


        JButton take_photo = new JButton("拍照");
        JButton save_photo = new JButton("保存");
        JButton cancle = new JButton("关闭");
        Camera camera = new Camera();
        // 监听拍摄
        take_photo.addMouseListener(new TakePhotoMouseAdapter(take_photo, camera));
        // 监听保存
        save_photo.addMouseListener(new SavePhotoMouseAdapter(grabbedImage));
        // 关闭
        cancle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                frame.setVisible(false);
            }
        });
        // 添加按钮
        contentPane.add(take_photo, BorderLayout.SOUTH);
        contentPane.add(save_photo, BorderLayout.SOUTH);
        contentPane.add(cancle, BorderLayout.SOUTH);
        // 添加面板
        contentPane2.add(contentPane, BorderLayout.SOUTH);
        // 操作状态
        while (frame.isVisible()) {


            // 获取图像
            if (camera.getState()) {
                grabbedImage = converter.convert(grabber.grab());
            }
            frame.showImage(converter.convert(grabbedImage));
            // 每40毫秒刷新视频,一秒25帧
            Thread.sleep(40);
        }


        frame.dispose();


        grabber.stop();
    }


}
