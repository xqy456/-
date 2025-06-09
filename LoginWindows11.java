package com.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;

import com.mysqld.Mysqld;
import com.style.StyleFont;

import com.tools.Tools;

public class LoginWindows {

	JFrame jFrame=new JFrame();
	
	final int WIDTH=450;
	final int HEIGHT=230;
	//登录界面
	public LoginWindows() {
		init();	
		jFrame.setResizable(false);//窗口是否可变
		jFrame.setVisible(true);//窗口是否可见
		jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);//设置默认关闭方式
		jFrame.validate();//让组件生成
	}
	
	void init() {
		jFrame.setTitle("奶茶销售管理系统");
		//设置窗口在屏幕中
		Tools.setWindowPos(WIDTH, HEIGHT, jFrame);
		jFrame.setTitle("奶茶销售管理系统");
		
		jFrame.setLayout(null);
		
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,HEIGHT/3/2-36));
        jPanel.setOpaque(false);
        jPanel.setBounds(0, 0, WIDTH, HEIGHT/3);
        
        JLabel jLabel=new JLabel("奶茶销售管理系统");
        jLabel.setForeground(Color.ORANGE);
       
        jPanel.add(jLabel);
        jLabel.setFont(StyleFont.titleFont);
     
        
        JPanel jPanel2=new JPanel();//存放账号和密码
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));//设置居中对齐
        jPanel2.setBounds(0, HEIGHT/3, WIDTH, HEIGHT/3-15);
       
        Box boxH;
        Box boxOne,boxTwo;
        boxH=Box.createHorizontalBox();
        boxOne=Box.createVerticalBox();
        boxTwo=Box.createVerticalBox();
        
        //______________________________________________________
    
        JLabel bgimg1 = new JLabel("账号");//定义�?个标签用于存放图�?
        JTextField jTextField=new JTextField(14);
        JLabel bgimg2 = new JLabel("密码");//定义�?个标签用于存放图
        JPasswordField jTextField2=new JPasswordField(14);
        //定义  登录按钮 找回密码
        
        JPanel jPanel3=new JPanel();
        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel3.setBounds(0, HEIGHT/3*2-15, WIDTH, HEIGHT/3+10);

        JButton jButton=new JButton("安全登录");
        jPanel3.add(jButton);
        jButton.setPreferredSize(new Dimension(175,27));
        jButton.setBackground(new Color( 255,255,255));
        jButton.setForeground(new Color( 0,0,0));
        jButton.setFont(StyleFont.loginFont);
        
        JButton jButton1=new JButton("注册");
        jPanel3.add(jButton1);
        jButton1.setPreferredSize(new Dimension(175+30,27));
        jButton1.setBackground(new Color( 255,255,255));
        jButton1.setForeground(new Color( 0,0,0));
        jButton1.setFont(StyleFont.loginFont);
        
        //对事件处�?
   		jButton1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Register window = new Register();
			window.frame.setVisible(true);
		}
		});
        
        boxOne.add(bgimg1);
        boxOne.add(bgimg2);
        boxTwo.add(jTextField);
        boxTwo.add(jTextField2);
        
        boxH.add(boxOne);
        boxH.add(boxH.createHorizontalStrut(10));//设置组件之间间隔
        boxH.add(boxTwo);
        jPanel2.add(boxH);
        
        //______________________________________________________
  
       jFrame.add(jPanel3);
       jFrame.add(jPanel2);
       jFrame.add(jPanel);


   		jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
			
			String accountString=jTextField.getText();
			String password=Tools.getPassword(jTextField2);
			
			if(accountString.equals("")) {
				Tools.messageWindows("账号不能为空");
				//账号不能为空
			}else if(password.equals("")) {
				//密码不能为空
				Tools.messageWindows("密码不能为空");
			}else {
				String data[]=new String [2];//初始化一个数�?
				data[0]=accountString;
				data[1]=password;
				String sqlString="select * from s_admin where account=? and password=? and pow='1'";
				ResultSet rs = Mysqld.QueryData(sqlString, data);
				try {
					
					if(rs.next()) {
					
							//登录管理员界面
						loginResult aLoginResult=new loginResult();
						
							jFrame.dispose();
						rs.close();	
					}else {
						
						sqlString="select * from s_admin where account=? and password=? and pow='2'";
						rs = Mysqld.QueryData(sqlString, data);
						if(rs.next()) {
							
						UserWindows window = new UserWindows(data[0]);
							window.frame.setVisible(true);
							jFrame.dispose();
							rs.close();
						}else {
							Tools.messageWindows("密码错误");
						}
						
					}
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			

		}
	});
   		   
	}

}
