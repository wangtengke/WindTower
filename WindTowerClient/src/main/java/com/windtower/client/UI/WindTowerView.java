package com.windtower.client.UI;

import com.windtower.client.UI.swing.SavePhotoMouseAdapter;
import com.windtower.client.UI.interfaces.IWindTowerStateObserver;
import com.windtower.client.UI.swing.AnglePoint;
import com.windtower.client.UI.swing.JudgePhotoMouseAdapter;
import com.windtower.client.UI.swing.MyCamera;
import com.windtower.client.UI.swing.MyPanel;
import com.windtower.config.client.WindTowerProperties;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;


import javax.swing.*;
import java.awt.*;


/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-27
 **/
@Slf4j
public class WindTowerView implements IWindTowerStateObserver,Runnable{
    private final static float  DemarcateXAngle= WindTowerProperties.getInstance().getDemarcateXAngle();
    private final static float  DemarcateYAngle= WindTowerProperties.getInstance().getDemarcateYAngle();

    private CanvasFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    //摄像头区域
    private JPanel cameraPanel;
    //摄像头画布
    private CanvasFrame cameraCanvas;
    //新建摄像头
    private MyCamera myCamera;
    //坐标轴
    private MyPanel myPanel;

    private JLabel title1;
    //螺栓是否松动显示结果
    private JTextField loose;
    //标定
    private JButton calibration;
    //判断
    private JButton judge;
    //退出
    private JButton exit;

    private JLabel title2;
    //X
    private JLabel Xlabel;
    //Y
    private JLabel Ylabel;
    //X轴
    private JTextField Xdata;
    //Y轴
    private JTextField Ydata;
    //X 度
    private JLabel Xangle;
    //Y 度
    private JLabel Yangle;
    //水深
    private JLabel WaterDepth;

    private JTextField WaterDepthNum;

    private JLabel WaterDepthCm;
    public WindTowerView() {
        initComponents();
    }

    private void initComponents() {
        frame = new CanvasFrame("塔筒检测系统");
        frame.setLayout(null);
//        frame.setSize(1000,500);
        //摄像头位置
        myCamera = new MyCamera();

        //左边
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 300, 400, 200);

        //右边
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(401,300,500,500);

        myPanel = new MyPanel(new AnglePoint(0,0));
        myPanel.setLayout(null);
        myPanel.setBounds(501,0,500,300);

        title1 = new JLabel("螺栓松动检测系统");
        title1.setBounds(50,0,300,100);
        title1.setFont(new Font("黑体",Font.BOLD,30));

        loose = new JTextField();
        loose.setBounds(20,100,40,30);

        calibration = new JButton("标定");
        calibration.setFont(new Font("宋体", Font.BOLD, 12));
        calibration.setBounds(80,100,70,30);

        judge = new JButton("判断");
        judge.setBounds(170,100,70,30);
        judge.setFont(new Font("宋体", Font.BOLD, 12));

        exit = new JButton("退出");
        exit.setBounds(260,100,70,30);
        exit.setFont(new Font("宋体", Font.BOLD, 12));

        title2 = new JLabel("塔筒倾斜检测系统");
        title2.setBounds(100,0,300,100);
        title2.setFont(new Font("黑体",Font.BOLD,30));

        Xlabel = new JLabel("X轴");
        Xlabel.setBounds(20,100,30,30);

        Xdata = new JTextField();
        Xdata.setBounds(50,100,80,30);

        Xangle = new JLabel("度");
        Xangle.setBounds(132,100,20,30);

        Ylabel = new JLabel("Y轴");
        Ylabel.setBounds(170,100,30,30);

        Ydata = new JTextField();
        Ydata.setBounds(200,100,80,30);

        Yangle = new JLabel("度");
        Yangle.setBounds(282,100,20,30);

        WaterDepth = new JLabel("水深");
        WaterDepth.setBounds(320,100,30,30);
        WaterDepthNum = new JTextField();
        WaterDepthNum.setBounds(352,100,50,30);
        WaterDepthCm = new JLabel("cm");
        WaterDepthCm.setBounds(404,100,30,30);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(myPanel);

        panel1.add(title1);
        panel1.add(loose);
        panel1.add(calibration);
        panel1.add(judge);
        panel1.add(exit);

        panel2.add(title2);
        panel2.add(Xlabel);
        panel2.add(Ylabel);
        panel2.add(Xdata);
        panel2.add(Ydata);
        panel2.add(Xangle);
        panel2.add(Yangle);
        panel2.add(WaterDepth);
        panel2.add(WaterDepthNum);
        panel2.add(WaterDepthCm);

        new Thread(()->{
            while (true) {
                try {
                    frame.showImage(myCamera.grabber.grab());
                    if(frame.getSize().width!=900)
                        frame.setSize(900,500);
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setSize(900,500);
        frame.setVisible(true);

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage grabbedImage = null;
        try {
            grabbedImage = converter.convertToIplImage(myCamera.grabber.grab());
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
//        SavePhotoMouseAdapter.getInstance().iplImage = grabbedImage;
        calibration.addMouseListener(new SavePhotoMouseAdapter(grabbedImage));

        judge.addMouseListener(new JudgePhotoMouseAdapter(grabbedImage));

    }

    @Override
    public void updateUI(WindTowerModel model) {
        Xdata.setText(String.format("%.3f",(float) model.armFeedbackState.AngleX/1000-DemarcateXAngle));
        Ydata.setText(String.format("%.3f",-((float) model.armFeedbackState.AngleY/1000-DemarcateYAngle)));
        AnglePoint anglePoint = new AnglePoint((float) model.armFeedbackState.AngleX/1000-DemarcateXAngle,(float) model.armFeedbackState.AngleY/1000-DemarcateYAngle);
        frame.remove(myPanel);
        myPanel = new MyPanel(anglePoint);
        myPanel.setLayout(null);
        myPanel.setBounds(501,0,500,300);
        frame.add(myPanel);
        myPanel.validate();
        myPanel.repaint();
    }

    @Override
    public void updateLoose(WindTowerModel windTowerModel) {
        loose.setText(windTowerModel.loose>0.006?"松动":"未松动");
    }

    @Override
    public void run() {
        initComponents();
        while (true) {

        }
    }
}
