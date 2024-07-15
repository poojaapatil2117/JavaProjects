package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.sql.*;

class MainWindow extends JFrame implements ActionListener, ItemListener, Runnable {
	private static final long serialVersionUID = 1L;

	private static final String url = "jdbc:ucanaccess://C:\\Users\\USER\\eclipse-workspace\\ResultFailOrPassProject\\src\\PassFail\\ResultDatabase1.accdb";

//	protected Connection con;

	Font fnt;

	JScrollPane quesr;
	JPanel qpan;
	JTextArea que;
	JScrollPane jsp;

	JPanel oopan;

	java.awt.List anslst;

	JPanel rmtpan, rmqpan;
	JLabel remtm, remqu;

	JPanel brpan, tbrpan, qbrpan;
	JLabel tbar, qbar;

	JPanel bpan, bbpan;

	JButton nxt, sub, skp, bak;

	question_chooser choose;
	Vector list, skplst, sublst;
	Question question;
	DatabaseInterface database;

	Thread t;

	int pos;
	static int mark;
	int skppos;
	int status;
	int mm = 19, ss = 60;

	Integer id;

	// MainWindow of Quetions And Options
	MainWindow(String ttl) throws ClassNotFoundException, SQLException {
		super("Best Luck ..." + ttl);
		setLayout(new GridLayout(3, 1));

		fnt = new Font("", Font.BOLD, 20);

		qpan = new JPanel();
		qpan.setBackground(Color.WHITE);
		qpan.setBorder(BorderFactory.createTitledBorder("Question To Attempt"));

		que = new JTextArea();
		que.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		que.setEditable(false);

		que.setFont(fnt);
		qpan.add(que);
		quesr = new JScrollPane(qpan);
		add(quesr);

		oopan = new JPanel();
		oopan.setBorder(BorderFactory.createTitledBorder("Select Any One Answer"));
		oopan.setLayout(new GridLayout(1, 1));
		anslst = new java.awt.List(4);
		anslst.addItemListener(this);
		anslst.setFont(fnt);
		oopan.add(anslst);
		add(oopan);

		bpan = new JPanel();
		bpan.setLayout(new GridLayout(2, 1));

		bbpan = new JPanel();

		nxt = new JButton("Next");
		nxt.addActionListener(this);
		nxt.setFont(new Font("", Font.BOLD, 20));
		nxt.setEnabled(false);
		bbpan.add(nxt);

		sub = new JButton("Submit");
		sub.setEnabled(false);
		sub.addActionListener(this);
		sub.setFont(new Font("", Font.BOLD, 20));
		bbpan.add(sub);

		skp = new JButton("Skip");
		skp.addActionListener(this);
		skp.setFont(new Font("", Font.BOLD, 20));
		bbpan.add(skp);

		bak = new JButton("Back");
		bak.addActionListener(this);
		bak.setFont(new Font("", Font.BOLD, 20));
		bbpan.add(bak);

		bpan.add(bbpan);

		brpan = new JPanel();
		brpan.setBorder(BorderFactory.createTitledBorder("Your Progress"));

		brpan.setLayout(new GridLayout(2, 1));

		rmtpan = new JPanel();
		rmtpan.setLayout(new GridLayout(1, 1));

		remtm = new JLabel("Remaining Time ..");
		remtm.setFont(fnt);
		rmtpan.add(remtm);
		brpan.add(rmtpan);

		tbrpan = new JPanel();

		tbrpan.setLayout(new GridLayout(2, 1));
		tbar = new JLabel("20:00");
		tbar.setFont(new Font("", Font.BOLD, 20));
		tbrpan.add(tbar);
		brpan.add(tbrpan);

		rmqpan = new JPanel();
		rmqpan.setLayout(new GridLayout(2, 1));

		remqu = new JLabel("Remaining Questions ..");
		remqu.setFont(fnt);
		rmqpan.add(remqu);
		brpan.add(rmqpan);

		qbrpan = new JPanel();
		qbrpan.setLayout(new GridLayout(2, 1));
		qbar = new JLabel("");
		qbar.setFont(new Font("", Font.BOLD, 20));
		qbrpan.add(qbar);
		brpan.add(qbrpan);
		bpan.add(brpan);

		add(bpan);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					// Class.forName("com.mysql.cj.jdbc.Driver");
					// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					// Connection con=DriverManager.getConnection(url,username,password);
//					con=DriverManager.getConnection(url);
					// Connection con=DriverManager.getConnection("jdbc:odbc:ResultDatabase1");

					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					Connection con = DriverManager.getConnection(url);

					String sql = new String("insert into result_sheer1(ID,name,mark,sub)values(?,?,?,?)");
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, "" + StartOfTest.getId());
					ps.setString(2, "" + StartOfTest.getName_());
					ps.setString(3, "" + mark);
					ps.setString(4, "" + SubjectChooser.subject); // SubjectChooser.getSubject()
					ps.executeUpdate();

					System.out.println("Record inserted successfully");
					ps.close();
					con.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "" + a);
				}
			}
		});

		pos = 0;
		mark = 0;
		skppos = 0;
		status = 0;

		t = new Thread(this);
		t.start();

		setVisible(true);
		setSize(1024, 700);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}

	}

	public void run() {
		choose = new question_chooser();

		list = choose.addQuestion();
		skplst = new Vector();
		sublst = new Vector();
		database = new DatabaseInterface();

		question = database.getFullQuestion((Integer) list.elementAt(pos));
		pos++;

		que.setText(question.getQuestion());
		anslst.add(question.getOpt1());
		anslst.add(question.getOpt2());
		anslst.add(question.getOpt3());
		anslst.add(question.getOpt4());

		for (int i = 0; i < 1200; i++) {
			if (i == 1200) {
				anslst.setEnabled(false);

				nxt.setEnabled(false);
				skp.setEnabled(false);
				sub.setEnabled(false);
				bak.setEnabled(false);

				try {

//					Class.forName("com.mysql.cj.jdbc.Driver");
//					Connection con=DriverManager.getConnection(url,username,password);

//					Connection con=DriverManager.getConnection("jdbc:odbc:result_db");

//					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//					con=DriverManager.getConnection(url);

//					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//					Connection con=DriverManager.getConnection("jdbc:odbc:ResultDatabase1");

					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					Connection con = DriverManager.getConnection(url);

					String sql = new String("insert into result_sheer1(ID,name,mark,sub) values(?,?,?,?)");
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, "" + StartOfTest.getId());
					ps.setString(2, "" + StartOfTest.getName_());
					ps.setString(3, "" + mark);
					ps.setString(4, "" + SubjectChooser.getSubject());
					ps.executeUpdate();
					ps.close();
					con.close();

				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "" + a);
				}
				new ResultFrame("Is It Result..");
				return;
			}

			try {
				Thread.sleep(1000);
				ss -= 1;
				if (ss == 0) {
					mm -= 1;
					ss = 60;
				}
				tbar.setText(mm + " : " + ss);
			} catch (Exception a) {
				JOptionPane.showMessageDialog(null, "" + a);
			}

		}
	}

	public void itemStateChanged(ItemEvent e) {
		sub.setEnabled(true);
	}

	public static int getMark() {
		return mark;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nxt) {
			nxt.setEnabled(false);
			skp.setEnabled(true);
			sub.setEnabled(false);
			bak.setEnabled(true);
			qbar.setText("" + (20 - pos));

			anslst.setEnabled(true);

			if (pos == 20) {
				nxt.setEnabled(false);
				skp.setEnabled(false);
				sub.setEnabled(false);
				bak.setEnabled(false);

				que.setText("Exam Finished...");
				new ResultFrame("Is It Result..");
				anslst.removeAll();
				anslst.setEnabled(false);

				try {

//					Class.forName("com.mysql.cj.jdbc.Driver");
//					Connection con=DriverManager.getConnection(url,username,password);
//					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

//					Connection con=DriverManager.getConnection("jdbc:odbc:result_db");
//					con=DriverManager.getConnection(url);

//					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//					Connection con=DriverManager.getConnection("jdbc:odbc:ResultDatabase1");
//					
//					String sql=new String("insert into result_sheer1 values(?,?,?,?)");

					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					Connection con = DriverManager.getConnection(url);
					String sql = new String("insert into result_sheer1(ID,name,mark,sub) values(?,?,?,?)");

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, "" + StartOfTest.getId());
					ps.setString(2, "" + StartOfTest.getName_());
					ps.setString(3, "" + mark);
					ps.setString(4, "" + SubjectChooser.getSubject());
					ps.executeUpdate();
					ps.close();
					con.close();

				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "" + a);
				}

				return;
			}
			anslst.removeAll();
			question = database.getFullQuestion((Integer) list.elementAt(pos));

			que.setText(question.getQuestion());
			anslst.add(question.getOpt1());
			anslst.add(question.getOpt2());
			anslst.add(question.getOpt3());
			anslst.add(question.getOpt4());
			pos++;
		}
		if (e.getSource() == sub) {
			status++;

			sub.setEnabled(false);
			skp.setEnabled(false);
			nxt.setEnabled(true);
			if (anslst.getSelectedIndex() < 0) {
				return;
			}
			id = (Integer) list.elementAt(pos - 1);
			sublst.add(id);
			String ans = anslst.getItem(anslst.getSelectedIndex());

			if (Answer.checkAnswer(id, ans)) {
				mark++;
			}
			anslst.setEnabled(false);
		}
		if (e.getSource() == skp) {
			nxt.setEnabled(true);
			skp.setEnabled(false);
			sub.setEnabled(false);
			id = (Integer) list.elementAt(pos - 1);
			skplst.add(id);
		}
		if (e.getSource() == bak) {
			skp.setEnabled(true);
			sub.setEnabled(true);
			if (skppos == skplst.size() - 1) {
				bak.setEnabled(false);
			}
			anslst.setEnabled(true);
			anslst.removeAll();
			id = (Integer) skplst.elementAt(skppos);
			question = database.getFullQuestion(id);
			if ((!sublst.contains(id))) {
				skppos++;
				que.setText(question.getQuestion());
				anslst.add(question.getOpt1());
				anslst.add(question.getOpt2());
				anslst.add(question.getOpt3());
				anslst.add(question.getOpt4());
			} else
				nxt.setEnabled(true);
		}
	}
}
