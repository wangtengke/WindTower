package cn.WindElectricity;

import cn.WindElectricity.PaintDetection.BackgroundRemove;
import cn.WindElectricity.PaintDetection.doBackgroundRemoval;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;

import static org.opencv.imgcodecs.Imgcodecs.imread;


public class CorrosionTest {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File file = new File("D:\\WindElectricity\\images\\youqifushi\\src");
        File[] files = file.listFiles();
        BackgroundRemove backgroundRemove = new BackgroundRemove();
        for(int i = 0; i < files.length; i++){
            String fileName = files[i].toString().trim();
            fileName = fileName.substring(fileName.lastIndexOf("\\")+1,fileName.lastIndexOf("."));
            Mat src = imread("D:\\WindElectricity\\images\\youqifushi\\src\\"+fileName+".png");
            backgroundRemove.Contour(src,fileName);
        }
        System.out.println("存在腐蚀："+backgroundRemove.getPainted()+"张");
        System.out.println("不存在腐蚀："+backgroundRemove.getUnpainted()+"张");
    }
}
