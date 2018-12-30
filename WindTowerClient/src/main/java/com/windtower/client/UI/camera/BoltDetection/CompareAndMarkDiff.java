package cn.WindElectricity.BoltDetection;

import org.opencv.core.*;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.absdiff;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;


/**
 * 通过对两张图片做差，找出不同之处
 */
public class CompareAndMarkDiff {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );

        Mat img1 = imread("G:\\java-project\\WindElectricity\\images\\LineDetection\\different\\20.png");
        Mat img2 = imread("G:\\java-project\\WindElectricity\\images\\LineDetection\\different\\21.png");

        Mat img = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat threshImg = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        //像素做差，图片大小必须相同
        absdiff(img1, img2, img);
        imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\different\\diff.png",img);

        Mat kernel1 = getStructuringElement(1,new Size(4,6));
        Mat kernel2 = getStructuringElement(1,new Size(2,3));
        //腐蚀
        erode(img, erodeImg, kernel1,new Point(-1,-1),1);
        //膨胀
        dilate(erodeImg, dilateImg, kernel2);
        //检测边缘
        threshold(dilateImg, threshImg, 20, 255, THRESH_BINARY);
        imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\different\\threshold.png",threshImg);
        //转化成灰度
        cvtColor(threshImg, threshImg, COLOR_RGB2GRAY);
        //找到轮廓(3：CV_RETR_TREE，2：CV_CHAIN_APPROX_SIMPLE)
        findContours(threshImg, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE, new Point(0,0));

        //存放每个矩形框的相关数据
        List<Rect> boundRect = new ArrayList<>(contours.size());    //list大小
        for(int i=0;i<contours.size();i++){
//        	Mat conMat = (Mat)contours.get(i);
//        	Imgproc.approxPolyDP((MatOfPoint2f)conMat,contours_poly.get(i),3,true);
            //根据轮廓生成外包络矩形
            Rect rect = boundingRect(contours.get(i));
            //System.out.println(rect.height + "\t" + rect.width);
            //过滤掉不符合条件的小矩形
            if(rect.width * rect.height >500){
                boundRect.add(rect);
            }else {
                continue;
            }

        }
        rectangle(img1, boundRect.get(0).tl(), boundRect.get(0).br(), new Scalar(0,0,255), 2, Core.LINE_8, 0);
//        for(int i=0 ;i<contours.size();i++){
//            //加载红色
//            Scalar color = new Scalar(0,0,255);
//            //绘制轮廓
////        	Imgproc.drawContours(img1, contours, i, color, 1, Core.LINE_8, hierarchy, 0, new Point());
//            //绘制矩形
//            rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 2, Core.LINE_8, 0);
//        }

        imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\different\\new_diff7.png", img1);
    }

    /**
     * 找出两张图片的不同之处，返回二值图
     * @param img1
     * @param img2
     * @return
     */
    public Mat Differ(Mat img1, Mat img2){

        Mat img = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat threshImg = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        //两帧图片做差，图片大小必须相同
        absdiff(img1, img2, img);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\different\\diff.png",img);

        Mat kernel1 = getStructuringElement(1,new Size(4,6));
        Mat kernel2 = getStructuringElement(1,new Size(2,3));
        //腐蚀
        erode(img, erodeImg, kernel1,new Point(-1,-1),1);
        //膨胀
        dilate(erodeImg, dilateImg, kernel2);
        //检测边缘
        threshold(dilateImg, threshImg, 20, 255, THRESH_BINARY);
        imwrite("D:\\WindElectricity\\images\\LineDetection\\different\\threshold.png",threshImg);

        //设定阈值判断是否有松动
        Sums(threshImg);

        return threshImg;
    }

    public boolean Sums(Mat image){

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
        //用for循环遍历图片像素值速度很慢，不能用于实时判断
        for(int i = 0; i < data.length; i++){
            if(data[i] == 0){
                black++;
            }else{
                white++;
            }
        }

        double standardLine = (double)white / (double)(white + black);
        System.out.println(standardLine);
        if(standardLine > 0.003){
            System.out.println("螺栓出现松动");
            return true;
        }else{
            System.out.println("螺栓未出现松动");
            return false;
        }

    }
}
