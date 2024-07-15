package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SubjectChooser extends JFrame implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;

	JPanel pan, pan2, pan3;
	JButton ok;
	JLabel lab;

//	static Choice sub;
//	static String subject;

	static Choice sub;
	static String subject;

	static {
		subject = new String("hello");
	}

	SubjectChooser(String ttl) {
		super(ttl);

		setLayout(new GridLayout(3, 1));
		pan2 = new JPanel();

		pan = new JPanel();

		lab = new JLabel("Select Subject ");
		lab.setFont(new Font("", Font.BOLD, 15));
		pan.add(lab);
		sub = new Choice();
		sub.setFont(new Font("", Font.BOLD, 15));
		// sub.addItemListener(this);
		sub.add("C Programming");
		sub.add("Data Structure");
		sub.add("C++ Programming");
		sub.add("Java Programming");
		sub.add("C# Programming");
		pan.add(sub);

		pan3 = new JPanel();

		ok = new JButton("Continue");
		ok.addActionListener(this);
		pan3.add(ok);

		add(pan2);
		add(pan);
		add(pan3);

		setVisible(true);
		setSize(300, 300);
		setLocation(350, 250);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		new InstructionFrame("Read It Care Fully..");
	}

	public void itemStateChanged(ItemEvent e) {
		// subject=sub.getItem(sub.getSelectedIndex());
	}

	static String getSubject() {
		subject = sub.getItem(sub.getSelectedIndex());
		return subject;
		// return subject;
	}
}
