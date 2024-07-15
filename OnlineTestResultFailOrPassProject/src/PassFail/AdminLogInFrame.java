package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLogInFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel tpan, bpan;

	JLabel lemail;
	JTextField temail;

	JLabel lpass;
	JPasswordField tpass;

	JButton singUp;

	String emailname = "poojalpatil21";
	String pass = "Pooja@123";

	public AdminLogInFrame(String ttl) {
		super(ttl);

		tpan = new JPanel();
		tpan.setLayout(new GridLayout(2, 2, 3, 3));

		lemail = new JLabel(" Email ");
		lemail.setFont(new Font("", Font.BOLD, 15));
		temail = new JTextField();
		temail.setFont(new Font("", Font.BOLD, 15));

		lpass = new JLabel(" Password ");
		lpass.setFont(new Font("", Font.BOLD, 15));
		tpass = new JPasswordField();
		tpass.setFont(new Font("", Font.BOLD, 15));
		tpan.add(lemail);
		tpan.add(temail);
		tpan.add(lpass);
		tpan.add(tpass);

		bpan = new JPanel();
		singUp = new JButton("LogIn");
		singUp.setFont(new Font("", Font.BOLD, 15));
		singUp.addActionListener(this);
		bpan.add(singUp);

		add(tpan, BorderLayout.CENTER);
		add(bpan, BorderLayout.SOUTH);

		setVisible(true);
		setSize(300, 150);
		setLocation(350, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (emailname.equals(temail.getText().trim()) && pass.equals(tpass.getText().trim())) {
			setVisible(false);
			new AdminClassForChoose("Admin Area");
		}
	}
}
