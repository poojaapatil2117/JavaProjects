package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class StartOfTest extends JFrame implements ActionListener {
	TitlePanel pan;
	JButton start;
	JPanel tpan, bpan;;
	JLabel lid;
	JTextField tid;
	JLabel lnm;
	JTextField tnm;

	static String ID;
	static String name;

	StartOfTest(String ttl) {
		setLayout(new GridLayout(3, 2, 10, 10));

		pan = new TitlePanel();
		add(pan, BorderLayout.NORTH);

		tpan = new JPanel();
		tpan.setLayout(new GridLayout(2, 2, 10, 10));

		lid = new JLabel("    ID ");
		lid.setFont(new Font("", Font.BOLD, 25));
		tid = new JTextField();
		tid.setFont(new Font("", Font.BOLD, 25));
		tpan.add(lid);
		tpan.add(tid);

		lnm = new JLabel("    Name ");
		lnm.setFont(new Font("", Font.BOLD, 25));
		tnm = new JTextField();
		tnm.setFont(new Font("", Font.BOLD, 25));
		tpan.add(lnm);
		tpan.add(tnm);

		add(tpan, BorderLayout.NORTH);

		bpan = new JPanel();

		start = new JButton("Start");
		start.addActionListener(this);
		start.setFont(new Font("", Font.BOLD, 25));
		bpan.add(start);
		add(bpan, BorderLayout.NORTH);

		setVisible(true);
		setLocation(175, 200);
		setSize(650, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}

	}

	public void actionPerformed(ActionEvent e) {
		try {
			setVisible(false);

			ID = tid.getText().trim();
			name = tnm.getText().trim();

			MainWindow nm = new MainWindow("ID - " + tid.getText() + " Name - " + tnm.getText());

		} catch (Exception a) {

			// JOptionPane.showMessageDialog(null,""+a);
		}
	}

	static String getId() {
		return ID;
	}

	static String getName_() {
		return name;
	}

}
