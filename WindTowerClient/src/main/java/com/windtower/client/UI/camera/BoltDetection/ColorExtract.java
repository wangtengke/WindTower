package cn.WindElectricity.BoltDetection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.bitwise_and;
import static org.opencv.core.Core.bitwise_not;
import static org.opencv.core.Core.inRange;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2HSV;
import static org.opencv.imgproc.Imgproc.cvtColor;

/**
 * 对特定颜色图案进行提取
 * 首先将图片从RGB空间装换到HSV空间，然后提取图片中的特定颜色。
 * 腐蚀膨胀后使直线连通范围更大，并去掉噪声部分
 * 难点：像素值要设定精确，容易受到周围光线的干扰
 *       HSV图片颜色的阈值很难确定
 *
 *
 */

public class ColorExtract {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
        String originalImgPath = "G:\\java-project\\WindElectricity\\images\\LineDetection\\100.png";
        Mat img = imread(originalImgPath);
        Mat imgHSV = new Mat(img.rows(), img.cols(), CV_8UC3);

        //RGB->HSV
        cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        //imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\12_hsv1.png",imgHSV);

        //原图中紫外光照射后显示的颜色接近蓝色，因而获取蓝色的范围
        Scalar minValues = new Scalar(113, 43, 46);
        Scalar maxValues = new Scalar(128, 255, 255);

        //Scalar minValues = new Scalar(0, 0, 0);
       // Scalar maxValues = new Scalar(180, 255, 46);

        //掩模图片
        Mat mask = new Mat();
        //把指定颜色范围的区域提取出来放到mask
        inRange(imgHSV,minValues,maxValues,mask);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\12_mask1.png",mask);



        // 膨胀，腐蚀
        Imgproc.erode(mask, mask, new Mat(), new Point(-1, -1), 1);   //腐蚀
        Imgproc.dilate(mask, mask, new Mat(), new Point(-1, -1), 2);  //膨胀
        imwrite("D:\\WindElectricity\\images\\LineDetection\\12_mask2.png",mask);

        Mat blackImg = new Mat();
        bitwise_and(img, img, blackImg,mask);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\12_dst.png",blackImg);

    }

    public Mat ColorExtract(String path){
        Mat img = imread(path);
        String name = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));

        Mat imgHSV = new Mat(img.rows(), img.cols(), CV_8UC3);
        Mat mask1 = new Mat();
        Mat mask2 = new Mat();

        //RGB->HSV
        cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\"+name+"_HSV.png",imgHSV);

        //原图中紫外光照射后显示的颜色接近蓝色，因而获取蓝色的范围
        Scalar minValues = new Scalar(113, 43, 46);
        Scalar maxValues = new Scalar(128, 255, 255);

        //黑色范围
        //Scalar minValues = new Scalar(0, 0, 0);
        // Scalar maxValues = new Scalar(180, 255, 46);

        //掩模图片
        //把指定颜色范围的区域提取出来放到mask
        inRange(imgHSV,minValues,maxValues,mask1);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\"+name+"_mask1.png",mask1);


        // 膨胀，腐蚀
        Imgproc.erode(mask1, mask2, new Mat(), new Point(-1, -1), 1);   //腐蚀
        Imgproc.dilate(mask2, mask2, new Mat(), new Point(-1, -1), 1);  //膨胀
        imwrite("D:\\WindElectricity\\images\\LineDetection\\"+name+"_mask2.png",mask2);

        Mat blackImg = new Mat();
        bitwise_and(img, img, blackImg,mask2);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\"+name+"_blackImg.png",blackImg);

        return mask2;
    }

}
