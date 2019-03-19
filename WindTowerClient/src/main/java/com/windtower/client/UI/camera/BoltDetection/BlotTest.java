package com.windtower.client.UI.camera.BoltDetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class BlotTest {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
        String src1 = "D:/WindElectricity/images/LineDetection/src/99.png";    //未松动
        String src2 = "D:/WindElectricity/images/LineDetection/src/100.png";    //松动


        //蓝色区域提取
        ColorExtract extract = new ColorExtract();
        Mat img1 = extract.ColorExtract(src1);
        Mat img2 = extract.ColorExtract(src2);

        //做差
        CompareAndMarkDiff cad = new CompareAndMarkDiff();
        cad.Differ(img1,img2);
    }
}
