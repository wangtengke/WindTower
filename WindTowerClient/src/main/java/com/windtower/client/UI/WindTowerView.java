package com.windtower.client.UI;

import com.windtower.client.UI.interfaces.IWindTowerStateObserver;

import javax.swing.*;
import java.awt.*;


/**
 * @program: windtower
 * @description:
 * @author: wangtengke
 * @create: 2018-12-27
 **/
public class WindTowerView implements IWindTowerStateObserver,Runnable{
    private JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    //标定
    private JButton calibration;
    //判断
    private JButton judge;
    //退出
    private JButton exit;
    //X
    private JLabel Xlabel;
    //Y
    private JLabel Ylabel;
    //X轴
    private JTextField Xdata;
    //Y轴
    private JTextField Ydata;
    public WindTowerView() {
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("塔筒检测系统");
        frame.setLayout(null);
        frame.setSize(1000,500);

        //左边
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 500, 500);

        //右边
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(501,0,500,500);

        calibration = new JButton("标定");
        calibration.setFont(new Font("宋体", Font.BOLD, 12));
        calibration.setBounds(100,400,80,30);
//        calibration.setText("标定");
        judge = new JButton("判断");
        judge.setBounds(200,400,80,30);
        judge.setFont(new Font("宋体", Font.BOLD, 12));
//        judge.setText("判断");

        exit = new JButton("退出");
        exit.setBounds(300,400,80,30);
        exit.setFont(new Font("宋体", Font.BOLD, 12));
//        exit.setText("退出");

        Xlabel = new JLabel("X轴");
        Xlabel.setBounds(0,400,80,30);
//        Xlabel.setText("X轴");

        Ylabel = new JLabel("Y轴");
        Ylabel.setBounds(120,400,80,30);
//        Ylabel.setText("Y轴");

        Xdata = new JTextField();
        Xdata.setBounds(60,400,80,30);

        Ydata = new JTextField();
        Ydata.setBounds(180,400,80,30);

        frame.add(panel1);
        frame.add(panel2);
        panel1.add(calibration);
        panel1.add(judge);
        panel1.add(exit);

        panel2.add(Xlabel);
        panel2.add(Ylabel);
        panel2.add(Xdata);
        panel2.add(Ydata);
        frame.setVisible(true);
//        jPanel2 = new javax.swing.JPanel();
//        jLabel1 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jTextField1 = new javax.swing.JTextField();
//        jTextField2 = new javax.swing.JTextField();
//
////        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//
//        jButton1.setText("jButton1");
//
//        jButton2.setText("jButton2");
//
//        jButton3.setText("jButton3");
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel1Layout.createSequentialGroup()
//                                .addComponent(jButton1)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jButton2)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(jButton3)
//                                .addGap(0, 10, Short.MAX_VALUE))
//        );
//        jPanel1Layout.setVerticalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                                .addGap(0, 143, Short.MAX_VALUE)
//                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(jButton1)
//                                        .addComponent(jButton2)
//                                        .addComponent(jButton3)))
//        );
//
//        jLabel1.setText("X轴");
//
//        jLabel2.setText("Y轴");
//
//        jTextField1.setToolTipText("");
//        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
//
//        jTextField2.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField2ActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
//        jPanel2.setLayout(jPanel2Layout);
//        jPanel2Layout.setHorizontalGroup(
//                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanel2Layout.createSequentialGroup()
//                                .addContainerGap(42, Short.MAX_VALUE)
//                                .addComponent(jLabel1)
//                                .addGap(18, 18, 18)
//                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(18, 18, 18)
//                                .addComponent(jLabel2)
//                                .addGap(18, 18, 18)
//                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(31, 31, 31))
//        );
//        jPanel2Layout.setVerticalGroup(
//                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
//                                .addGap(0, 0, Short.MAX_VALUE)
//                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(jLabel2)
//                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//        );
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                .addGap(124, 124, 124))
//        );


    }

    @Override
    public void updateUI(WindTowerModel model) {
        Xdata.setText(String.valueOf(model.armFeedbackState.AngleX));
        Ydata.setText(String.valueOf(model.armFeedbackState.AngleY));
    }

    @Override
    public void run() {
        initComponents();
        while (true) {

        }
    }
}
