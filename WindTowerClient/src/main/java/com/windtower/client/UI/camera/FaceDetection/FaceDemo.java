package cn.WindElectricity.FaceDetection;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.rectangle;

public class FaceDemo {
    public static void main(String[] args) {
        System.out.println("Hello, OpenCV");
        // Load the native library.
        //System.loadLibrary("opencv_java246");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FaceDemo face = new FaceDemo();
        face.run();
    }


    public void run() {
        System.out.println("\nRunning DetectFaceDemo");
//        System.out.println(getClass().getResource("F:\\opencv\\opencv-3.2.0\\opencv-3.2.0\\data\\haarcascades\\haarcascade_frontalface_alt2.xml").getPath());
        // Create a face detector from the cascade file in the resources
        // directory.
        //CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("haarcascade_frontalface_alt2.xml").getPath());
        //Mat image = Highgui.imread(getClass().getResource("lena.png").getPath());
        //注意：源程序的路径会多打印一个‘/’，因此总是出现如下错误
        /*
         * Detected 0 faces Writing faceDetection.png libpng warning: Image
         * width is zero in IHDR libpng warning: Image height is zero in IHDR
         * libpng error: Invalid IHDR data
         */
        //因此，我们将第一个字符去掉
        String xmlfilePath=getClass().getResource("F:\\opencv\\opencv-3.2.0\\opencv-3.2.0\\data\\haarcascades\\haarcascade_frontalface_alt2.xml").getPath();
        CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
        Mat image = imread("E:\\face2.jpg");
        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        // Save the visualized detection.
        String filename = "E:\\faceDetection.png";
        System.out.println(String.format("Writing %s", filename));
        System.out.println(filename);
        imwrite(filename, image);
    }

}
