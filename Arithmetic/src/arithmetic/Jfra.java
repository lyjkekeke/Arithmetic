package arithmetic;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Jfra{

	private JFrame jfr; 
	private static void InitGlobalFont(Font font) {
		  FontUIResource fontRes = new FontUIResource(font);
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
		   Object key = keys.nextElement();
		   Object value = UIManager.get(key);
		   if (value instanceof FontUIResource) {
		    UIManager.put(key, fontRes);
		   }
		  }
		 }
	
	Jfra(){//生成可视化界面
		jfr = new JFrame();
		jfr.setTitle("四则运算");
		jfr.setSize(1320,850);
		InitGlobalFont(new Font("微软雅黑", Font.BOLD, 20));
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		jfr.add("North",p);
		p1.setLayout(new BorderLayout());
		p1.add("North",p2);
		p1.add("Center",p3);
		jfr.add("Center",p1);
	    JButton b1 = new JButton("生成题目");
	    JButton b2 = new JButton("保存文件");
	    JButton b3 = new JButton("上传答案");
	    JButton b4 = new JButton("核对答案");
	    Dimension preferredSize = new Dimension(150,35);//设置尺寸
	    b1.setPreferredSize(preferredSize );
	    b2.setPreferredSize(preferredSize );
	    b3.setPreferredSize(preferredSize );
	    b4.setPreferredSize(preferredSize );
	    Label l1 = new Label("生成题目                                               ");
	    Label l2 = new Label("输入/上传答案");
	    Label l3 = new Label("                                            核对情况");
	    JTextArea a1 = new JTextArea(30,30);
	    JTextArea a2 = new JTextArea(30,30);
	    JTextArea a3 = new JTextArea(30,30);
	    ScrollPane js1 = new ScrollPane();
	    ScrollPane js2 = new ScrollPane();
	    ScrollPane js3 = new ScrollPane();
	    js1.setSize(400,600);
	    js2.setSize(400,600);
	    js3.setSize(400,600);
	    js1.add(a1);
	    js2.add(a2);
	    js3.add(a3);
	    p.add(b1);
	    p.add(b2);
	    p.add(b3);
	    p.add(b4);
	    p2.add(l1);
	    p2.add(l2);
	    p2.add(l3);
	    p3.add(js1);
	    p3.add(js2);
	    p3.add(js3);
	    jfr.setVisible(true);
	    Creater ct = new Creater();
	    List<String> anslist = new ArrayList<String>();
	    //点击生成题目事件
	    b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				a1.setText("");
				a2.setText("");
				a3.setText("");
				anslist.clear();
				//产生窗口让用户设置题目个数和数值范围
				String num = JOptionPane.showInputDialog(
						jfr,
						"请输入需要生成的题目个数（不能超过10000）：",
						"");
				String range = JOptionPane.showInputDialog(
						jfr,
						"请输入需要生成的数值范围：",
						"");
				if(Integer.parseInt(num) > 10000) { 
					JOptionPane.showMessageDialog(jfr, "输入题目个数超出范围，请重新输入");
				}
				else if(range == null) { 
					JOptionPane.showMessageDialog(jfr, "请输入数值范围");
				}
				else {
					String[] str = new String[Integer.parseInt(num)];
					HashSet<String> phashSet = new HashSet<String>();
					formJudge fj = new formJudge();
					DupCheck dc = new DupCheck();
					//循环实现产生num个题目
					for(int i=0; i<str.length; i++) {
						numItem nit = new numItem();
						nit = ct.pro_creater(Integer.parseInt(range));
						String result = fj.calculate(nit.oldstr);
						String pos = fj.listToString(fj.infix2postfix(fj.parse(nit.oldstr)));
						if( result == null || !phashSet.add(pos) || dc.muldupcheck(nit.newstr,str)) {//如果出现不合法操作或重复式子则重新产生一条题目
							i--;
				        }
						else {
							str[i] = nit.newstr;
							anslist.add(result);
							a1.append(i+1 + "." + " " + nit.newstr+ " " + "=" + '\n');
				        }
					}
				}
			}
	    	
	    	
	    });
	    //点击保存文件的事件
	    b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fd = new FileDialog(jfr,"另存为",FileDialog.SAVE);
				fd.addWindowListener (new WindowAdapter() {
					public void windowClosing(WindowEvent ee){
					System.exit(0);
				    }
			    });
				fd.setVisible(true);
				String [] s = a1.getText().split("\n");
				try {
					File f = new File(fd.getDirectory() + fd.getFile());
					String filename = fd.getDirectory() + fd.getFile();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
					for(int i = 0; i < s.length; i++) {
						bw.write(s[i]);
						bw.newLine();
					}
					bw.close();
				}
				catch(FileNotFoundException fe_) {
					System.out.println("file not found");
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IO error");
					System.exit(0);
				}
				try {
					File f1 = new File(fd.getDirectory() + "answer_" + fd.getFile());
					String filename = fd.getDirectory() + "answer_" + fd.getFile();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f1)));
					for(int i = 0; i < s.length; i++) {
						bw.write(anslist.get(i));
						bw.newLine();
					}
					bw.close();
				}
				catch(FileNotFoundException fe_) {
					System.out.println("file not found");
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IO error");
					System.exit(0);
				}
			}
	    	
	    });
	    //点击上传答案的事件
	    b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				a2.setText("");
				a3.setText("");
				FileDialog fd = new FileDialog(jfr,"打开",FileDialog.LOAD);
				fd.addWindowListener (new WindowAdapter() {
					public void windowClosing(WindowEvent ee){
					System.exit(0);
				    }
			    });
				fd.setVisible(true);
				String s;
				try {
					File f = new File(fd.getDirectory() + fd.getFile());
					String filename = fd.getDirectory() + fd.getFile();
					BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
					while((s = bw.readLine()) != null) {
						a2.append(s + '\n');
					}
					bw.close();
				}
				catch(FileNotFoundException fe_) {
					System.out.println("file not found");
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IO error");
					System.exit(0);
				}
			}
	    });
	    //点击核对答案的事件
	    b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				a3.setText("");
				String [] ansline = a2.getText().split("\n");
				int correct = 0,error = 0;
				for(int i = 0; i < ansline.length ; i++) {
					if(anslist.get(i).equals(ansline[i])){
						a3.append("√" + " " + "正答：" + " " + anslist.get(i) + '\n');
						correct++;
					}else {
						a3.append("×"  + " " +"正答：" + " " + anslist.get(i)+ '\n');
						error++;
						
					}
				}
				a3.append("正确数目为：" + correct + '\n');
				a3.append("错误数目为：" + error + '\n');
			}
	    	
	    });
	}

}
