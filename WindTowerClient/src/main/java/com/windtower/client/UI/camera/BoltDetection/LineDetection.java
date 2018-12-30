package cn.WindElectricity.BoltDetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_PI;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

/**
 * 直线检测
   image：输入图像，要求必须是二值化图像，通常是用canny后的图片作为输入图；
   lines：输出结果，在HoughLines方法中，输出结果是（r,θ），lines是1行（row）n列（col）的Mat，通过get（0，i）可以获取到（r,θ）数组；在HoughLinesP方法中，输出结果是P1(x1,y1)，P2(x2,y2)，即端点的坐标值，lines是1行（row）n列（col）的Mat，通过get（0，i）可以获取到（x1, y1, x2, y2）数组；
   rho：极坐标系中半径r的搜索步长（累加值），如果为1表示每次累计1个像素；
   theta：极坐标系中角度θ的搜索步长，通常设置为π/180，表示每次累加角度为π/180；
   threshold：累加器的阈值参数，只有满足阈值数量的点的直线才会被检测出来；
   srn/stn：对于多尺度霍夫变换，rho为极坐标系中累加值的粗粒度表示，srn/stn为细粒度的累加值；
   minLineLength：HoughLinesP方法中，只有长度超过minLineLength的线段才能被检测出来；
   maxLineGap：HoughLinesP方法中，线段跨越的最大空隙为maxLineGap。
 *
 */


/**
 * canny边缘检测
 *
 * Canny边缘检测的步骤：
 * （1）消除噪声，一般使用高斯平滑滤波器卷积降噪
 * （2）计算梯度幅值和方向，此处按照sobel滤波器步骤来操作
 * （3）非极大值抑制，排除非边缘像素
 * （4）滞后阈值（高阈值和低阈值），若某一像素位置的幅值超过高阈值，该像素被保留为边缘像素；若小于低阈值，则被排除；若在两者之间，该像素仅在连接到高阈值像素时被保留。推荐高低阈值比在2:1和3:1之间
 *
 * 第一个参数，InputArray类型的image，输入图像，即源图像，填Mat类的对象即可，且需为单通道8位图像。
 * 第二个参数，OutputArray类型的edges，输出的边缘图，需要和源图片有一样的尺寸和类型。
 * 第三个参数，double类型的threshold1，第一个滞后性阈值。
 * 第四个参数，double类型的threshold2，第二个滞后性阈值。
 * 第五个参数，int类型的apertureSize，表示应用Sobel算子的孔径大小，其有默认值3。
 * 第六个参数，bool类型的L2gradient，一个计算图像梯度幅值的标识，有默认值false。
 */
public class LineDetection {
    //标准hough变换
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat srcImage = Imgcodecs.imread("G:\\java-project\\WindElectricity\\images\\LineDetection\\1.png");
        Mat dstImage = srcImage.clone();

        //边缘检测
        //threshold越大，轮廓的要求越高（灰度值变化越明显才能构成轮廓）
        int threshold = 800;
        Imgproc.Canny(srcImage, dstImage, threshold, threshold * 3, 5, false);
        imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\dstImage.png",dstImage);

        Mat storage = new Mat();
        Imgproc.HoughLines(dstImage, storage, 1, Math.PI / 180, 200, 0, 0, 0, 180);
        //imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\storage.png",storage);

        List list = new ArrayList();
        for (int x = 0; x < storage.rows(); x++){
            double[] vec = storage.get(x, 0);            //获取到（r,θ）数组
            double rho = vec[0];
            double theta = vec[1];

            //theta越小直线越竖直
            if(theta <= 160){
                continue;
            }else {
                list.add(theta);
                Point pt1 = new Point();
                Point pt2 = new Point();
                double a = Math.cos(theta);
                double b = Math.sin(theta);
                double x0 = a * rho;
                double y0 = b * rho;

                pt1.x = Math.round(x0 + 1000 * (-b));
                pt1.y = Math.round(y0 + 1000 * (a));
                pt2.x = Math.round(x0 - 1000 * (-b));
                pt2.y = Math.round(y0 - 1000 * (a));

                if (theta >= 0)
                {
                    Imgproc.line(srcImage, pt1, pt2, new Scalar(0, 0, 255, 255), 2, Imgproc.LINE_8, 0);
                }
            }

        }

            for(int i = 0; i < list.size(); i++){
                System.out.println(list.get(i));
            }
            Imgcodecs.imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\dst.png", srcImage);
    }

    //累计概率hough变换
//    public static void main(String[] args) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat srcImage = Imgcodecs.imread("G:\\java-project\\WindElectricity\\images\\LineDetection\\1.png");
//        Mat dstImage = srcImage.clone();
//
//        //边缘检测
//        //threshold越大，轮廓的要求越高（灰度值变化越明显才能构成轮廓）
//        int threshold = 1300;
//        Imgproc.Canny(srcImage, dstImage, threshold, threshold * 3, 5, false);
//        imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\dstImage.png",dstImage);
//
//        Mat storage = new Mat();
//        Imgproc.HoughLinesP(dstImage, storage, 1, Math.PI / 180, 10, 0, 0);
//        //获取每条直线的起点和终点，并画出来
//        for (int x = 0; x < storage.rows(); x++)
//        {
//            double[] vec = storage.get(x, 0);
//            double x1 = vec[0], y1 = vec[1], x2 = vec[2], y2 = vec[3];
//            //获取点
//            Point start = new Point(x1, y1);
//            Point end = new Point(x2, y2);
//            Imgproc.line(srcImage, start, end, new Scalar(0, 0, 255, 255), 1, Imgproc.LINE_4, 0);
//        }
//        Imgcodecs.imwrite("G:\\java-project\\WindElectricity\\images\\LineDetection\\dst.png", srcImage);
//    }


}
