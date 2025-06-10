package com.windows;


import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.mysqld.Mysqld;
import com.tools.Table;
import com.tools.Tools;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
public class loginResult {


	JFrame jFrame=new JFrame();
	final int WIDTH=530;//宽
	final int HEIGHT=450;//高



	public loginResult(){

		init();
		jFrame.setResizable(false);//窗口是否可变
		jFrame.setVisible(true);//窗口是否可见
		jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);//设置默认关闭方式
		jFrame.validate();//让组件生效

	}
	void init() {
		//进行初始化
		Toolkit kit =Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int width=screenSize.width;
		int height=screenSize.height;
		int x=(width-WIDTH)/2;
		int y=(height-HEIGHT)/2;
		jFrame.setSize(WIDTH,HEIGHT);
		jFrame.setLocation(x, y);
		jFrame.setLayout(null);

		jFrame.setTitle("奶茶销售管理系统");
		//添加背景图片
		JPanel jPanel=new JPanel();
		jPanel.setLayout(null);//
		ImageIcon img = new ImageIcon("src/img/2.jpg");//这是背景图片
		JLabel img1 = new JLabel(img);
		img1.setBounds(0,0,530, 50);//设置背景图片 设置背景位置
		jPanel.setBounds(0,0,530,50);
		jPanel.add(img1);
		jFrame.add(jPanel);


		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel headname = new JLabel("查询奶茶");
		JTextField headNumber = new JTextField(9);
		jPanel2.setBounds(0,52,516,57);


		JButton addDate = new JButton("增加");
		JButton delDate = new JButton("删除");
		JButton chekDate = new JButton("更改");
		JButton showDate = new JButton("查找");
		JButton revData = new JButton("重置");


		jPanel2.add(addDate);
		jPanel2.add(delDate);
		jPanel2.add(chekDate);

		jPanel2.add(revData);
		jPanel2.add(headname);


		jPanel2.add(headname);
		jPanel2.add(headNumber);
		jPanel2.add(showDate);
		jPanel2.setBorder(BorderFactory.createTitledBorder("基本信息处理"));

		jFrame.add(jPanel2);

		FlowLayout flowlayout = new FlowLayout();//流布局
		JPanel jPanel3=new JPanel();
		jPanel3.setLayout(flowlayout);
		jPanel3.setBounds(0,107,516,110);//设置大小

		JLabel jLabel=new JLabel("奶茶名字");
		jPanel3.add(jLabel);
		JTextField jTextField=new JTextField(16);
		jPanel3.add(jTextField);

		JLabel jLabel_1=new JLabel("奶茶价格");
		jPanel3.add(jLabel_1);
		JTextField jTextField_1=new JTextField(16);
		jPanel3.add(jTextField_1);

		JLabel jLabel_2=new JLabel("奶茶库存");
		jPanel3.add(jLabel_2);
		JTextField jTextField_2=new JTextField(16);
		jPanel3.add(jTextField_2);

		JLabel jLabel_3=new JLabel("奶茶种类");
		jPanel3.add(jLabel_3);
		JTextField jTextField_3=new JTextField(16);
		jPanel3.add(jTextField_3);






		jPanel3.setBorder(BorderFactory.createTitledBorder("信息处理界面"));
		jFrame.add(jPanel3);

		//表格


		//________________________________________________________

		Object columns[] ={"奶茶名字","奶茶价格","奶茶库存","奶茶种类"};//创建表格

		Table t1Table=new Table(columns);
		JTable table = t1Table.getTables();
		JScrollPane JS = t1Table.getJScrollPane();
		DefaultTableModel model = t1Table.getModel();
		//JS.setPreferredSize(new Dimension( 600,280));//设置整个滚动条窗口的大小

		JS.setBounds(0, 215,516,190);


		 jFrame.add(JS);
				//_________________________________________________________________

		 addDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(jTextField.equals("")) {
					Tools.messageWindows("请输入完整信息");
				}else {
					String data[]= {
							jTextField.getText(),
							jTextField_1.getText(),
							jTextField_2.getText(),
							jTextField_3.getText(),


					};




					int a=Mysqld.upDate("insert into s_tea values (?,?,?,?)", data);
					if(a==1) {
						Tools.messageWindows("添加成功");
					}else {
						Tools.messageWindows("添加失败请输入完整信息");
					}
				}
			}

		 });




		 delDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(jTextField.equals("")) {
					Tools.messageWindows("请输入完整信息");
				}else {
					String data[]= {

							 headNumber.getText()
					};
					int a=Mysqld.upDate("DELETE FROM s_tea where name=?", data);
					if(a==1) {
						Tools.messageWindows("删除成功");
					}else {
						Tools.messageWindows("删除失败请输入完整信息");
					}
				}
			}

		 });



		 //更改
		 chekDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(jTextField.equals("")||headNumber.getText().equals("")) {
					Tools.messageWindows("请输入完整信息");
				}else {
					String data[]= {
							jTextField.getText(),
							jTextField_1.getText(),
							jTextField_2.getText(),
							jTextField_3.getText(),

							headNumber.getText()

					};
					int a=Mysqld.upDate("UPDATE s_tea set name=?,price=?,numbers=?,types=? where name=?", data);
					if(a==1) {
						Tools.messageWindows("更改成功");
					}else {
						Tools.messageWindows("更改失败请重新输入");
					}


				}
			}

		 });
		 //查找
		showDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchText = headNumber.getText().trim();
				ResultSet rs = null;
				if (searchText.equals("")) {
					rs = Mysqld.QueryData("select * from s_tea", null);
				} else {
					String[] data = { "%" + searchText + "%", "%" + searchText + "%" };
					rs = Mysqld.QueryData(
							"select * from s_tea where name like ? or types like ?", data);
				}
				Tools.addDataTable(rs, model, 4);

				try {
					if (rs != null && rs.next()) {
						jTextField.setText(rs.getString(1));
						jTextField_1.setText(rs.getString(2));
						jTextField_2.setText(rs.getString(3));
						jTextField_3.setText(rs.getString(4));
					}
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		 revData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub


				jTextField.setText("");
				jTextField_1.setText("");
				jTextField_2.setText("");
				jTextField_3.setText("");



			}

		 });

		 jTextField_1.setDocument(new NumberDocument());
		 jTextField_2.setDocument(new NumberDocument());

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
