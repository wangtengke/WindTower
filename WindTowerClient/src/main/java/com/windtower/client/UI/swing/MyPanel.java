package com.windtower.client.UI.swing;

import com.windtower.config.client.WindTowerProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-28
 **/
public class MyPanel extends JPanel {
    private final static int Width = 300;
    private final static int High = 300;
    private AnglePoint anglePoint;
    public MyPanel(AnglePoint anglePoint){
        this.anglePoint = anglePoint;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawLine(20,150,Width-20,150);
        //x箭头
        g2D.drawLine(Width-30,140,Width-20,150);
        g2D.drawLine(Width-30,160,Width-20,150);
        //“x”
        g2D.drawString("X",Width-10,155);


        //画y轴
        g2D.drawLine(High/2,High-20,High/2,20);
        g2D.drawLine(High/2-10,30,High/2,20);
        g2D.drawLine(High/2+10,30,High/2,20);
        g2D.drawString("Y",High/2,20);
        for (int i = 0; i <180 ; i=i+20) {
            g2D.setColor(Color.RED);
            g2D.drawOval(Width/2-i/2,High/2-i/2,i,i);
        }

        g2D.setColor(Color.BLUE);    //设置颜色
        g2D.fillOval(145+(int)(anglePoint.getX()),145+(int)(anglePoint.getY()),10,10); //画

        // 解释
        g2D.setColor(Color.RED);
        g2D.setFont(new Font("黑体",Font.BOLD,12));
        g2D.drawString("---",220,50);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("宋体",Font.BOLD,12));
        g2D.drawString("间隔20度",250,50);
        g2D.drawRect(210,30,110,30);
    }

}
