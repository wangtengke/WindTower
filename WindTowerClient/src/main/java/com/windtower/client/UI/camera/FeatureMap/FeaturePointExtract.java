package cn.WindElectricity.FeatureMap;


import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.objdetect.CascadeClassifier;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.resize;

/**
 * opencv的features2d包中提供了surf,sift和orb等特征点算法，用于图像查找图像对象，搜索对象，分析对象，识别对象，合成全景等场合
 * 通过一些代码研究三种特征点算法，我有意把原始图像转为灰度并旋转90度与照处中人物比较，以研究三种算法对人脸识别的优点和局限。辅助使用了人脸查找获取待查找图像中人脸子矩阵
 * API版本不对无法使用
 */

public class FeaturePointExtract {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = imread("G:\\java-project\\WindElectricity\\images\\VideoImage\\2.png");   //灰度图片
        Mat dst = imread("G:\\java-project\\WindElectricity\\images\\VideoImage\\1.png");  //正常图片
        MatOfRect mr = getFace(dst);
        Mat sub = dst.submat(mr.toArray()[0]);
        imwrite("G:\\java-project\\WindElectricity\\images\\VideoImage\\Y4.png", sub);

//        imwrite("G:\\java-project\\WindElectricity\\images\\VideoImage\\Y4.png", FeatureSurfBruteforce(src.t(), sub));
//        imwrite("G:\\java-project\\WindElectricity\\images\\VideoImage\\Y5.png", FeatureSiftLannbased(src.t(), sub));
//        imwrite("G:\\java-project\\WindElectricity\\images\\VideoImage\\Y6.png", FeatureOrbLannbased(src.t(), sub));
    }

    public static Mat FeatureSurfBruteforce(Mat src, Mat dst){
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SURF);              //用于计算关键特征点

        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SURF);   //用于计算特征描述矩阵
        //DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);

        //获取关键点与特征描述矩阵声明
        MatOfKeyPoint mkp = new MatOfKeyPoint();
        fd.detect(src, mkp);
        Mat desc = new Mat();
        de.compute(src, mkp, desc);
        Features2d.drawKeypoints(src, mkp, src);


        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
        fd.detect(dst, mkp2);
        Mat desc2 = new Mat();
        de.compute(dst, mkp2, desc2);
        Features2d.drawKeypoints(dst, mkp2, dst);


        // Matching features
        MatOfDMatch Matches = new MatOfDMatch();
        Matcher.match(desc, desc2, Matches);

        double maxDist = Double.MIN_VALUE;
        double minDist = Double.MAX_VALUE;

        DMatch[] mats = Matches.toArray();
        for (int i = 0; i < mats.length; i++) {
            double dist = mats[i].distance;
            if (dist < minDist) {
                minDist = dist;
            }
            if (dist > maxDist) {
                maxDist = dist;
            }
        }
        System.out.println("Min Distance:" + minDist);
        System.out.println("Max Distance:" + maxDist);
        List<DMatch> goodMatch = new LinkedList<>();

        for (int i = 0; i < mats.length; i++) {
            double dist = mats[i].distance;
            if (dist < 3 * minDist && dist < 0.2f) {
                goodMatch.add(mats[i]);
            }
        }

        Matches.fromList(goodMatch);
        // Show result
        Mat OutImage = new Mat();
        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);

        return OutImage;
    }
    public static Mat FeatureSiftLannbased(Mat src, Mat dst){
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);

        MatOfKeyPoint mkp = new MatOfKeyPoint();
        fd.detect(src, mkp);
        Mat desc = new Mat();
        de.compute(src, mkp, desc);
        Features2d.drawKeypoints(src, mkp, src);

        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
        fd.detect(dst, mkp2);
        Mat desc2 = new Mat();
        de.compute(dst, mkp2, desc2);
        Features2d.drawKeypoints(dst, mkp2, dst);


        // Matching features
        MatOfDMatch Matches = new MatOfDMatch();
        Matcher.match(desc, desc2, Matches);

        List<DMatch> l = Matches.toList();
        List<DMatch> goodMatch = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            DMatch dmatch = l.get(i);
            if (Math.abs(dmatch.queryIdx - dmatch.trainIdx) < 10f) {
                goodMatch.add(dmatch);
            }

        }

        Matches.fromList(goodMatch);
        // Show result
        Mat OutImage = new Mat();
        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);

        return OutImage;
    }
    public static Mat FeatureOrbLannbased(Mat src, Mat dst){
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.ORB);
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);

        MatOfKeyPoint mkp = new MatOfKeyPoint();
        fd.detect(src, mkp);
        Mat desc = new Mat();
        de.compute(src, mkp, desc);
        Features2d.drawKeypoints(src, mkp, src);

        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
        fd.detect(dst, mkp2);
        Mat desc2 = new Mat();
        de.compute(dst, mkp2, desc2);
        Features2d.drawKeypoints(dst, mkp2, dst);


        // Matching features

        MatOfDMatch Matches = new MatOfDMatch();
        Matcher.match(desc, desc2, Matches);

        double maxDist = Double.MIN_VALUE;
        double minDist = Double.MAX_VALUE;

        DMatch[] mats = Matches.toArray();
        for (int i = 0; i < mats.length; i++) {
            double dist = mats[i].distance;
            if (dist < minDist) {
                minDist = dist;
            }
            if (dist > maxDist) {
                maxDist = dist;
            }
        }
        System.out.println("Min Distance:" + minDist);
        System.out.println("Max Distance:" + maxDist);
        List<DMatch> goodMatch = new LinkedList<>();

        for (int i = 0; i < mats.length; i++) {
            double dist = mats[i].distance;
            if (dist < 3 * minDist && dist < 0.2f) {
                goodMatch.add(mats[i]);
            }
        }

        Matches.fromList(goodMatch);
        // Show result
        Mat OutImage = new Mat();
        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);

        //Highgui.imwrite("E:/work/qqq/Y4.jpg", OutImage);
        return OutImage;
    }
    public static MatOfRect getFace(Mat src) {
        Mat result = src.clone();
        if (src.cols() > 1000 || src.rows() > 1000) {
            resize(src, result, new Size(src.cols() / 3, src.rows() / 3));
        }

        CascadeClassifier faceDetector = new CascadeClassifier("F:\\opencv\\opencv-3.2.0\\opencv-3.2.0\\data\\haarcascades\\haarcascade_frontalface_alt2.xml");
        MatOfRect objDetections = new MatOfRect();
        faceDetector.detectMultiScale(result, objDetections);

        return objDetections;
    }

}
