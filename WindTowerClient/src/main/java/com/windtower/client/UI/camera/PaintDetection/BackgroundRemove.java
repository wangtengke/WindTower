package com.windtower.client.UI.camera.PaintDetection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

/**
 * 利用油漆脱落褐色或褐色部分的像素值很小，先将原图灰度化后，灰度图的每个像素值与特定的阈值比较
 * 第一次二值化：若大于阈值，则该点像素变为0（黑色），小于阈值则像素变为max（白色），经过处理，脱落部分变为白色
 * 去噪，膨胀，腐蚀操作：使脱落区域连成一个整体
 * 第二次二值化：若大于阈值，则该点像素变为max（白色），小于阈值则像素变为0（黑色），得到更清晰的轮廓
 * 去除背景：得到腐蚀部分
 */

public class BackgroundRemove {

    private int painted ;
    private int unpainted ;

    public int getPainted() {
        return painted;
    }

    public int getUnpainted() {
        return unpainted;
    }

    //背景去除，提取脱落部分
    public Mat BackgroundRemove(Mat image,String i){

        int thresh_type1 = Imgproc.THRESH_BINARY_INV;   //当前点值大于阈值时，设置为0，否则设置为Maxval;  去除背景
        int thresh_type2 = Imgproc.THRESH_BINARY;       //当前点值大于阈值时，取Maxval，否则设置为0；   留下背景
        int thresh_type3 = Imgproc.THRESH_TOZERO;       //当前点值大于阈值时，不改变，否则设置为0
        int thresh_type4 = Imgproc.THRESH_TRUNC;        //当前点值大于阈值时，设置为阈值，否则不改变
        int thresh_type5 = Imgproc.THRESH_TOZERO_INV;   //当前点值大于阈值时，设置为0，否则不改变

        Mat thresholdImg1 = new Mat();
        Mat thresholdImg2 = new Mat();
        Mat thresholdImg3 = new Mat();

        //灰度化
        Mat image_gray = Gray(image,i);
        //第一次二值化      100
        Imgproc.threshold(image_gray, thresholdImg1, 100, 255, thresh_type1);
        //imwrite("G:\java-project\WindElectricity\images\youqifushi\result\\"+i+"_thresholdImg1.png",thresholdImg1);

        //去除噪声
        Imgproc.blur(thresholdImg1, thresholdImg2, new Size(5, 5));

        // 膨胀，腐蚀
        Imgproc.dilate(thresholdImg2, thresholdImg2, new Mat(), new Point(-1, -1), 1);  //膨胀
        Imgproc.erode(thresholdImg2, thresholdImg2, new Mat(), new Point(-1, -1), 3);   //腐蚀
        //imwrite("G:\java-project\WindElectricity\images\youqifushi\result\\"+i+"_thresholdImg2.png",thresholdImg2);

        //第二次二值化
        Imgproc.threshold(thresholdImg2, thresholdImg3, 90, 255, thresh_type2);


        //imwrite("G:\java-project\WindElectricity\images\youqifushi\result\\"+i+"_thresholdImg3.png",thresholdImg3);

        // 得到腐蚀部分的图片
        Mat foreground = new Mat(image.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        thresholdImg3.convertTo(thresholdImg3,CvType.CV_8U);
        //掩膜图像复制，src.copyTo( dst, detected_edges)，是将src中detected_edges矩阵对应的非零部分（即边缘检测结果）复制到dst中
        image.copyTo(foreground, thresholdImg3);
        //imwrite("G:\java-project\WindElectricity\images\youqifushi\result\\"+i+"_foregound.png",foreground);

        //通过二值图中白色像素占整张图片的比例，设定一个阈值，若超过这个阈值，则判断为存在腐蚀问题
        if(Sums(thresholdImg3,i)){
            painted++;
        }else {
            unpainted++;
        }


        return thresholdImg3;
    }



    //统计二值图中白色像素所占比例，判断是否有腐蚀
    public boolean Sums(Mat image,String i){

        //二值图中每个位置的像素都是0或255，刚好是一个字节,于是可以把图片的所有像素点放入一个字节数组中
        int width = image.cols();
        int height = image.rows();
        int white = 0;
        int black = 0;
        byte[] data = new byte[width * height];                  //针对单通道图片
        image.get(0,0,data);

//        //显示图片矩阵
////        for(int i = 0; i < data.length; i++){
////            if(i % width == 0) {
////                System.out.println();
////            }else{
////                System.out.print(data[i] + "\t");                   //未被腐蚀部分（黑色部分）0  被腐蚀部分（白色部分）-1
////            }
////        }

        //设定阈值判断是否被腐蚀
        for(int j = 0; j < data.length; j++){
            if(data[j] == 0){
                black++;
            }else{
                white++;
            }
        }

        double standardLine = (double)white / (double)(white + black);

        if(standardLine > 0.05){
            System.out.println("第"+i+"张图片出现腐蚀情况");
            return true;
        }else{
            System.out.println("第"+i+"张图片未出现腐蚀");
            return false;
        }

    }



    //灰度化
    public Mat Gray(Mat Image, String i) {
        Mat image_gry = new Mat();
        cvtColor(Image, image_gry, COLOR_BGR2GRAY);
        // imwrite("G:\java-project\WindElectricity\images\youqifushi\result\\"+i+"_image_gry.png", image_gry);
        return image_gry;
    }


    //轮廓提取
    public void Contour(Mat Image, String i){

        Mat image_bin = BackgroundRemove(Image,i);

        //init
        List<MatOfPoint> contours = new ArrayList<>();  //用于存储轮廓，一个 MatOfPoint 保存一个轮廓，所有轮廓放在 List 中
        Mat hierarchy = new Mat();     //层级

        //find contours
        Imgproc.findContours(image_bin, contours, hierarchy, RETR_CCOMP , CHAIN_APPROX_SIMPLE); //从腐蚀后的图片中获得轮廓，放到hierarchy

        // if any contour exist...
        if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
        {
            // for each contour, display it in blue
            for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
            {
                Imgproc.drawContours(Image, contours, idx, new Scalar(0, 255, 0));   //把轮廓添加到原图上
            }
        }
        imwrite("D:\\WindElectricity\\images\\youqifushi\\result\\"+i+"_image_contours.png",Image);

    }



}
