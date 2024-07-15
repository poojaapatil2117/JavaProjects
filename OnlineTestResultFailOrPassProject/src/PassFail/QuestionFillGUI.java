package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class QuestionFillGUI extends JFrame implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;

	JPanel mpan, bpan;

	JLabel lid;
	JTextField tid;
	JLabel lque;
	TextArea tque;
	JLabel lans;
	JTextField tans;
	JLabel lop1;
	JTextField top1;
	JLabel lop2;
	JTextField top2;
	JLabel lop3;
	JTextField top3;
	JLabel lop4;
	JTextField top4;

	JLabel status;

	JButton ins, up, dis;

	QuestionFillGUI(String ttl) {
		super(ttl);

		mpan = new JPanel();
		mpan.setLayout(new GridLayout(7, 2, 3, 3));

		lid = new JLabel("Question ID");
		tid = new JTextField();
		tid.addFocusListener(this);
		mpan.add(lid);
		mpan.add(tid);

		lque = new JLabel("Question");
		tque = new TextArea();
		tque.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mpan.add(lque);
		mpan.add(tque);

		lop1 = new JLabel("Option 1");
		top1 = new JTextField();
		mpan.add(lop1);
		mpan.add(top1);

		lop2 = new JLabel("Option 2");
		top2 = new JTextField();
		mpan.add(lop2);
		mpan.add(top2);

		lop3 = new JLabel("Option 3");
		top3 = new JTextField();
		mpan.add(lop3);
		mpan.add(top3);

		lop4 = new JLabel("Option 4 ");
		top4 = new JTextField();
		mpan.add(lop4);
		mpan.add(top4);

		lans = new JLabel("Answer");
		tans = new JTextField();
		mpan.add(lans);
		mpan.add(tans);

		bpan = new JPanel();

		ins = new JButton("Insert");
		ins.addActionListener(this);
		bpan.add(ins);

		up = new JButton("Update");
		up.addActionListener(this);
		bpan.add(up);

		dis = new JButton("Display");
		dis.addActionListener(this);
		bpan.add(dis);

		status = new JLabel();
		bpan.add(status);

		add(mpan, BorderLayout.CENTER);
		add(bpan, BorderLayout.SOUTH);
		setVisible(true);
		setSize(500, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ins) {
			QuestionFill qf = new QuestionFill();
			if (qf.getNextID() == Integer.parseInt(tid.getText().trim())) {
				qf.storeQuestion(Integer.parseInt(tid.getText().trim()), tque.getText().trim(), top1.getText().trim(),
						top2.getText().trim(), top3.getText().trim(), top4.getText().trim(), tans.getText().trim());
				status.setText("Inserted Successfully..");
				tid.setText("");
				tque.setText("");
				top1.setText("");
				top2.setText("");
				top3.setText("");
				top4.setText("");
				tans.setText("");
			} else
				JOptionPane.showMessageDialog(null, "Enter Only " + qf.getNextID() + " As ID");
		}
		if (e.getSource() == dis) {
			// DatabaseInterface di=new DatabaseInterface();
			QuestionFill qf = new QuestionFill();
			Question q = qf.getFullQuestionForQuetionFill(Integer.parseInt(tid.getText().trim()));

			System.out.println("----------" + q.getId());
			tid.setText("" + q.getId());
			tque.setText("" + q.getQuestion().trim());
			top1.setText("" + q.getOpt1().trim());
			top2.setText("" + q.getOpt2().trim());
			top3.setText("" + q.getOpt3().trim());
			top4.setText("" + q.getOpt4().trim());
			tans.setText("" + q.getAnswer().trim());
		}

		if (e.getSource() == up) {
			QuestionFill qf = new QuestionFill();
			qf.updateQuestion(Integer.parseInt(tid.getText().trim()), tque.getText().trim(), top1.getText().trim(),
					top2.getText().trim(), top3.getText().trim(), top4.getText().trim(), tans.getText().trim());
			status.setText("updated successfully");
		}
	}

	public void focusGained(FocusEvent e) {
		QuestionFill qf = new QuestionFill();
		tid.setText("" + qf.getNextID());
		status.setText("");
	}

	public void focusLost(FocusEvent e) {
	}

	public static void main(String[] args) {
		// new SubjectChooserForFillQuetion("Subject Chooser");
		new QuestionFillGUI("Fill Questions");
	}
}
