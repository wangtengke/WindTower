package cn.WindElectricity.VideoDemo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 切换图片显示
 */
public class TakePhotoMouseAdapter extends MouseAdapter {


    private JButton jButton;
    private Camera camera;


    public TakePhotoMouseAdapter(JButton jButton, Camera camera) {
        this.jButton = jButton;
        this.camera = camera;
    }


    @Override
    public void mouseClicked(MouseEvent arg0) {
        System.out.println("拍照");

        // 修改显示
        if (camera.getState()) {
            jButton.setText("继续拍照");
            // 暂停拍照
            camera.setState(false);
        } else {
            jButton.setText("拍照");
            // 继续拍照
            camera.setState(true);
        }
    }
}
