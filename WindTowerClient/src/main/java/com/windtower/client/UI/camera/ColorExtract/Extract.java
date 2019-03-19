package com.windtower.client.UI.camera.ColorExtract;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.inRange;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.cvtColor;

public class Extract {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String originalImgPath = "G:\\java-project\\OpenCVtest1\\images\\ColorExtract\\src.png";
        Mat img = imread(originalImgPath);
        //Mat imgHSV = new Mat(img.rows(),img.cols(), CvType.CV_8UC3);
        Mat imgHSV = new Mat();
        //RGB->HSV
        cvtColor(img,imgHSV, Imgproc.COLOR_BGR2GRAY);
        imwrite("G:\\java-project\\OpenCVtest1\\images\\ColorExtract\\imgHSV.png",imgHSV);
        Scalar minValues = new Scalar(0,0,0);
        Scalar maxValues = new Scalar(180,255,46);
        Mat mask = new Mat();
        inRange(imgHSV,minValues,maxValues,mask);
        imwrite("G:\\java-project\\OpenCVtest1\\images\\ColorExtract\\mask.png",mask);
        Mat blackImg = new Mat();
        Core.bitwise_and(img,imgHSV,blackImg);
        imwrite("G:\\java-project\\OpenCVtest1\\images\\ColorExtract\\blackImg.png",blackImg);
    }
}
