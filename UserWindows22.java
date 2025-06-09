package com.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.mysqld.Mysqld;
import com.tools.Table;
import com.tools.Tools;
import com.windows.loginResult.NumberDocument;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UserWindows {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	String account="";
	public UserWindows(String account) {
		this.account=account;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("奶茶管理");
		frame.setBounds(100, 100, 769, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(10, 10, 735, 57);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("奶茶名字");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("数量");
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("购买");

		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("查找奶茶");
		
		panel.add(btnNewButton_1);
		

		
		//________________________________________________________
		
				Object columns[] ={"奶茶名字","奶茶价格","奶茶库存","奶茶种类"};//创建表格
							
				Table t1Table=new Table(columns);
				JTable table = t1Table.getTables();
				JScrollPane JS = t1Table.getJScrollPane();
				DefaultTableModel model = t1Table.getModel();
				//JS.setPreferredSize(new Dimension( 600,280));//设置整个滚动条窗口的大小
				
				JS.setBounds(10, 91, 735, 275);
			
				
				frame.getContentPane().add(JS);
		//_________________________________________________________________
				
				//购买
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(textField.getText().equals("")) {
							Tools.messageWindows("请输入奶茶名字");
						}else if(textField_1.getText().equals("")) {
							Tools.messageWindows("请输入奶茶数量");
						}else {
							//购买
							//数量
							String date[]= {
								textField_1.getText(),
								textField.getText()
							};
							int a=Mysqld.upDate("update s_tea set numbers=numbers-? where name=?", date);
							if(a==-1) {
								//数量不足
								Tools.messageWindows("库存不足，请重新输入数量");
							}
							if(a==0) {
								Tools.messageWindows("奶茶名字错误");
							}
							if(a==1) {
								//购买
								//Tools.messageWindows("奶茶名字错误");
								String sql="insert into s_rcod VALUES(?,?,?,?,?)";
								String d[]= {
										textField.getText()
								};
								ResultSet rs = Mysqld.QueryData("select * from s_tea where name=?",d);
								try {
									if(rs.next()) {
										String datee[]= {
												
												rs.getString(1),
												rs.getString(2),
												textField_1.getText(),
												rs.getString(4),
												account
										};
										
										
										int cc=Mysqld.upDate(sql, datee);
										if(cc==1) {
											Tools.messageWindows("购买成功");
										}else {
											Tools.messageWindows("购买失败");
										}
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								

								
								
							}
							
						}
					}
				});			
				textField_1	 .setDocument(new NumberDocument());
				
				JButton btnNewButton_2 = new JButton("查找购买记录");
				panel.add(btnNewButton_2);


				//________________________________________________________
				
						Object columns1[] ={"奶茶名字","奶茶价格","奶茶数量","奶茶种类","账号"};//创建表格
									
						Table t1Table1=new Table(columns1);
						JTable table1 = t1Table1.getTables();
						JScrollPane JS1 = t1Table1.getJScrollPane();
						DefaultTableModel model1 = t1Table1.getModel();
						//JS.setPreferredSize(new Dimension( 600,280));//设置整个滚动条窗口的大小
						
						JS1.setBounds(10, 411, 735, 152);
					
						
						frame.getContentPane().add(JS1);
				//_________________________________________________________________
						
						
						btnNewButton_2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
							
								if(textField_1.getText()	.equals("")) {
								ResultSet rs = Mysqld.QueryData("select * from s_rcod", null);
								Tools.addDataTable(rs, model1, 5);
								
								
							}
							}
						});
				
				
			
				//查找
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String data[]= {
								textField_1.getText()	
						};
						if(textField_1.getText()	.equals("")) {
						ResultSet rs = Mysqld.QueryData("select * from s_tea", null);
						Tools.addDataTable(rs, model, 4);
							
						}
						
					}
				});
	}
	//设置样式
	public class NumberDocument extends PlainDocument {
	    public NumberDocument() {
	    }

	    public void insertString(int var1, String var2, AttributeSet var3) throws BadLocationException {
	        if (this.isNumeric(var2)) {
	            super.insertString(var1, var2, var3);
	        } else {
	            Toolkit.getDefaultToolkit().beep();
	        }

	    }

	    private boolean isNumeric(String var1) {
	        try {
	            Long.valueOf(var1);
	            return true;
	        } catch (NumberFormatException var3) {
	            return false;
	        }
	    }
	}
}
