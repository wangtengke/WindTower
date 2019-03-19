package com.windtower.client.UI.swing;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 点击保存
 */
@Slf4j
public class SavePhotoMouseAdapter extends MouseAdapter {

    public static opencv_core.IplImage iplImage;

    public SavePhotoMouseAdapter(opencv_core.IplImage iplImage) {
        SavePhotoMouseAdapter.iplImage = iplImage;
    }


    @Override
    public void mouseClicked(MouseEvent arg0) {
        log.info("save picture success!");
        // 保存结果提示框
        JFrame myFrame = new JFrame();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");            //设置日期格式
        //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        try {
            if (iplImage != null) {
                // 保存图片，文件名按日期命名
                cvSaveImage("C:\\Users\\wangtengke\\Desktop\\base.png", iplImage);
                // 发送修改用户头像请求...也可以直接发送字节数组到服务器，由服务器上传图片并修改用户头像
                JOptionPane.showMessageDialog(myFrame, "上传成功");


            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(myFrame, "保存失败");
            e.printStackTrace();
        } finally {
            // 关闭提示jframe
            myFrame.dispose();
            myFrame = null;
        }
    }


    public static void cvSaveImage(String path, opencv_core.IplImage image) throws IOException {
        File file = new File(path);
        // ImageIO.write(toBufferedImage(image), "jpg", file);
        // 使用字节保存
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(toBufferedImage(image), "jpg", out);
        byte[] bs = out.toByteArray();
        // 保存字节数组为图片到本地
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bs, 0, bs.length);
        fos.close();

        out.close();
    }

    // 通过image获取bufferedImage
    //iplConverter 可以查看convert方法可以转换的对象，Frame IplImage,Mat之间转换
    //Java2DFrameConverter让Frame和BufferedImage之间相互转换
    public static BufferedImage toBufferedImage(opencv_core.IplImage image) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        BufferedImage bufferedImage = java2dConverter.convert(iplConverter.convert(image));
        return bufferedImage;
    }
}
