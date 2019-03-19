package com.windtower.client.UI.swing;


import com.windtower.client.UI.WindTowerModel;
import com.windtower.client.UI.camera.BoltDetection.ColorExtract;
import com.windtower.client.UI.camera.BoltDetection.CompareAndMarkDiff;
import com.windtower.client.UI.interfaces.IWindTowerStateObserver;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.windtower.client.UI.swing.SavePhotoMouseAdapter.cvSaveImage;



/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-29
 **/
@Slf4j
public class JudgePhotoMouseAdapter extends MouseAdapter implements IWindTowerStateObserver {
    private opencv_core.IplImage iplImage;
    private  double standardLine;

    public JudgePhotoMouseAdapter(opencv_core.IplImage grabbedImage) {
        this.iplImage = grabbedImage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        log.info("save picture success!");
        // 保存结果提示框
        JFrame myFrame = new JFrame();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");            //设置日期格式
        //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        try {
            if (iplImage != null) {
                // 保存图片，文件名按日期命名
                cvSaveImage("C:\\Users\\wangtengke\\Desktop\\judge.png", iplImage);
                // 发送修改用户头像请求...也可以直接发送字节数组到服务器，由服务器上传图片并修改用户头像
                JOptionPane.showMessageDialog(myFrame, "上传成功");


            }
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(myFrame, "保存失败");
            e1.printStackTrace();
        } finally {
            // 关闭提示jframe
            myFrame.dispose();
            myFrame = null;
        }
//        opencv_core.Mat matJudge = new opencv_core.Mat(iplImage);
//        opencv_core.Mat matBase  = new opencv_core.Mat(SavePhotoMouseAdapter.iplImage);
////        Mat matjudge = new Mat(matJudge.address());
////        Mat matbase = new Mat(matBase.address());
//        Differ(matJudge,matBase);
//        Differ(iplImage,SavePhotoMouseAdapter.iplImage);
        BoltDetect();
    }

    /**
     * 找出两张图片的不同之处，返回二值图
     * @param img1
     * @param img2
     * @return
     */
//    public opencv_core.Mat Differ(opencv_core.Mat img1, opencv_core.Mat img2){
//
//        opencv_core.Mat img = new opencv_core.Mat();
//        opencv_core.Mat erodeImg = new opencv_core.Mat();
//        opencv_core.Mat dilateImg = new opencv_core.Mat();
//        opencv_core.Mat threshImg = new opencv_core.Mat();
////        List<MatOfPoint> contours = new ArrayList<>();
////
////        opencv_core.Mat hierarchy = new opencv_core.Mat();
//        //两帧图片做差，图片大小必须相同
//        absdiff(img1, img2, img);
//        imwrite("C:\\Users\\wangtengke\\Desktop\\diff.png",img);
//
//        opencv_core.Mat kernel1 = getStructuringElement(1,new opencv_core.Size(4,6));
//        opencv_core.Mat kernel2 = getStructuringElement(1,new opencv_core.Size(2,3));
//        //腐蚀
//        erode(img, erodeImg, kernel1);
//        //膨胀
//        dilate(erodeImg, dilateImg, kernel2);
//        //检测边缘
//        threshold(dilateImg, threshImg, 20, 255, THRESH_BINARY);
//        imwrite("C:\\Users\\wangtengke\\Desktop\\threshold.png",threshImg);
//
//        //设定阈值判断是否有松动
//        Sums(threshImg);
//
//        return threshImg;
//    }

//    public boolean Sums(opencv_core.Mat image){
//
//        //二值图中每个位置的像素都是0或255，刚好是一个字节,于是可以把图片的所有像素点放入一个字节数组中
//        int width = image.cols();
//        int height = image.rows();
//        int white = 0;
//        int black = 0;
//        int[] data = new int[width * height];                  //针对单通道图片
//        image.convertTo(image, opencv_core.CV_32S);
//        IntBuffer intBuffer =image.createBuffer();
//        intBuffer.get(data);
////        image.get(0,0,data);
//
//        //显示图片矩阵
//        for(int i = 0; i < data.length; i++){
//            if(i % width == 0) {
//                System.out.println();
//            }else{
//                System.out.print(data[i] + "\t");                   //未被腐蚀部分（黑色部分）0  被腐蚀部分（白色部分）-1
//            }
//        }
//
//        //设定阈值判断是否被腐蚀
//        //用for循环遍历图片像素值速度很慢，不能用于实时判断
//        for(int i = 0; i < data.length; i++){
//            if(data[i] == 0){
//                black++;
//            }else{
//                white++;
//            }
//        }
//
//        standardLine = (double)white / (double)(white + black);
////        WindTowerModel.loose = standardLine
//
//        System.out.println("================"+standardLine+"================");
//        if(standardLine > 0.003){
//            System.out.println("螺栓出现松动");
//            return true;
//        }else{
//            System.out.println("螺栓未出现松动");
//            return false;
//        }
//
//    }

    //判断螺母是否松动
    public void BoltDetect(){
            String src1 = "C:\\Users\\wangtengke\\Desktop\\base.png";    //基准图像
            String src2 = "C:\\Users\\wangtengke\\Desktop\\judge.png";     //松动图像

            //蓝色区域提取
            ColorExtract extract = new ColorExtract();
            Mat img1 = extract.ColorExtract(src1);
            Mat img2 = extract.ColorExtract(src2);

            //做差
            CompareAndMarkDiff cad = new CompareAndMarkDiff();
            cad.Differ(img1,img2);
    }

    @Override
    public void updateUI(WindTowerModel windtowerModel) {

    }

    @Override
    public void updateLoose(WindTowerModel windTowerModel) {
//        windTowerModel.loose = standardLine;
    }
}


