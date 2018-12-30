package cn.WindElectricity.PaintDetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.opencv.core.Core.subtract;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.drawContours;

/**
 * 金属表面划痕检测（金属表面缺陷检测）
 */
public class CheckScratch {
    public void CheckScratch(){
        Mat image = imread("G:\\java-project\\OpenCVtest1\\images\\MentalScratch\\1.png");
        Mat imagemen = new Mat();
        Mat diff = new Mat();
        Mat Mask = new Mat();

        //均值模糊
        blur(image, imagemen, new Size(13, 13));

        //图像差分
        subtract(imagemen, image, diff);    //矩阵减法，A-B的更高级形式,两幅图片的像素矩阵相减，不同的部分放在diff中

        //同动态阈值分割dyn_threshold
        threshold(diff,Mask,50,255,THRESH_BINARY_INV);
        imshow("imagemen",imagemen);
        waitKey(0);
        imshow("diff",diff);
        waitKey(0);
        imshow("Mask",Mask);
        waitKey(0);
        //转化为灰度图
        Mat imagegray = new Mat();
        cvtColor(Mask,imagegray,CV_RGB2GRAY);

        List<MatOfPoint> contours = new ArrayList<>();  //用于存储轮廓，一个 MatOfPoint 保存一个轮廓，所有轮廓放在 List 中
        Mat hierarchy = new Mat();     //层级

        findContours(imagegray, contours, hierarchy, RETR_CCOMP , CHAIN_APPROX_SIMPLE); //从腐蚀后的图片中获得轮廓，放到hierarchy
        Mat drawing = Mat.zeros(Mask.size(),CV_8U);

        int j = 0;
        double height = hierarchy.size().height;
        double width = hierarchy.size().width;
        if ( height > 0 && width > 0)
        {
            // for each contour, display it in blue
            for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
            {
                drawContours(drawing, contours, idx, new Scalar(250, 0, 0));   //把轮廓添加到原图上
            }
        }
    }


}
