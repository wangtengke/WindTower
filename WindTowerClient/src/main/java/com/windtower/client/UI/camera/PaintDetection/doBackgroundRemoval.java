package cn.WindElectricity.PaintDetection;

/*
 * 利用背景去除方式，把被腐蚀部分先去掉，再依次进行灰度，二值，获取轮廓，在原图上加上轮廓
 * 1.这种方式在背景很复杂的情况下不能准确识别出腐蚀部分，容易出现误判
 * 2.得到腐蚀部分的轮廓后也无法判断部件是不是被腐蚀
 * */

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.drawContours;

public class doBackgroundRemoval {

    //背景去除
    public Mat doBackgroundRemoval(Mat frame){
        // init
        Mat hsvImg = new Mat();
        List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();

        int thresh_type1 = Imgproc.THRESH_BINARY_INV;   //当前点值大于阈值时，设置为0，否则设置为Maxval;  去除背景
        int thresh_type2 = Imgproc.THRESH_BINARY;       //当前点值大于阈值时，取Maxval，否则设置为0；   留下背景
        int thresh_type3 = Imgproc.THRESH_TOZERO;       //当前点值大于阈值时，不改变，否则设置为0
        int thresh_type4 = Imgproc.THRESH_TRUNC;        //当前点值大于阈值时，设置为阈值，否则不改变
        int thresh_type5 = Imgproc.THRESH_TOZERO_INV;   //当前点值大于阈值时，设置为0，否则不改变

        // threshold the image with the average hue value
        hsvImg.create(frame.size(), CvType.CV_8U);
        Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);   //HSV 表示 hue、saturation、value(色调) HSV（色相，饱和度，明度）在概念上可以被认为是颜色的倒圆锥体（黑点在下顶点，白色在上底面圆心
        //hsvImg中目标部分的颜色和背景部分的颜色不同
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\hsvImg.png",hsvImg);
        Core.split(hsvImg, hsvPlanes);   //提取图像的RGB三原色分割图像，放到hsvPlanes中，一共三张

        //查看hsvPlanes中的图片
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\hsvPlanes1.png",hsvPlanes.get(0));
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\hsvPlanes2.png",hsvPlanes.get(1));
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\hsvPlanes3.png",hsvPlanes.get(2));

        // get the average hue value of the image
        Scalar average=Core.mean(hsvPlanes.get(0));                //Scalar类型数据[B,G,R,Alpha]
        double threshValue =average.val[0];                        //求得阈值
        System.out.println("average" + average);                   //[17.19307640503139, 0.0, 0.0, 0.0]   [43.06995285785666, 0.0, 0.0, 0.0]   [164.32810497273576, 0.0, 0.0, 0.0]
        System.out.println("threshValue：" + threshValue);        //17.19  43.06  164.32

        //二值化
        //图像的二值化就是将图像上的像素点的灰度值设置为0或255，这样将使整个图像呈现出明显的黑白效果
        Imgproc.threshold(hsvPlanes.get(2), thresholdImg, threshValue, 255, thresh_type1);
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\thresholdImg1.png",thresholdImg);

        Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));

        // dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);  //膨胀
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);   //腐蚀

        Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, thresh_type1);      //黑白颠倒
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\thresholdImg2.png",thresholdImg);

        // create the new image
        Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        thresholdImg.convertTo(thresholdImg,CvType.CV_8U);
        frame.copyTo(foreground, thresholdImg);//掩膜图像复制，src.copyTo( dst, detected_edges)，是将src中detected_edges矩阵对应的非零部分（即边缘检测结果）复制到dst中

        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\foregound.png",foreground);
        return foreground;

    }

    //灰度化
    public Mat Gray(Mat Image) {
        // Mat image_org = imread(SrcImage, IMREAD_COLOR);
        Mat image_gry = new Mat();
        cvtColor(Image, image_gry, COLOR_BGR2GRAY);
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\image_gry.png", image_gry);
        return image_gry;
    }

    //二值化
    public Mat Threshold(Mat Image){
        //Mat image_gry = imread(GrayImage);
        Mat image_bin = new Mat();
        threshold(Image, image_bin, 200, 255, THRESH_BINARY); // convert to binary image
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\image_bin.png",image_bin);
        return image_bin;
    }

    //膨胀
//    public Mat Dilate(Mat Image){
//        //Mat image_bin = imread(BinaryImage);
//        Mat image_dil = new Mat();
//        Mat element = getStructuringElement(MORPH_RECT, new Size(2, 2)); // 膨胀
//        dilate(Image, image_dil, element);
//        imwrite("G:\\java-project\\OpenCVtest1\\images\\youqifushi\\image_dil.png",image_dil);
//        return image_dil;
//    }

    //轮廓提取
    public void Contour(Mat Image){


        Mat image_src = doBackgroundRemoval(Image);
        Mat image_gray = Gray(image_src);
        Mat image_bin = Threshold(image_gray);


        //init
        List<MatOfPoint> contours = new ArrayList<>();  //用于存储轮廓，一个 MatOfPoint 保存一个轮廓，所有轮廓放在 List 中
        Mat hierarchy = new Mat();     //层级

        //find contours
        findContours(image_bin, contours, hierarchy, RETR_CCOMP , CHAIN_APPROX_SIMPLE); //从腐蚀后的图片中获得轮廓，放到hierarchy

        // if any contour exist...
        if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
        {
            // for each contour, display it in blue
            for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
            {
                drawContours(Image, contours, idx, new Scalar(250, 0, 0));   //把轮廓添加到原图上
            }
        }
        imwrite("G:\\java-project\\WindElectricity\\images\\youqifushi\\image_contours.png",Image);

    }
}
