package PassFail;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class LoginFrame extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	JPanel tpan,bpan;

	JLabel lunm;
	JTextField tunm;

	JLabel lpas;
	JPasswordField tpas;

	JButton ok;

	String unm="OnlineExam";
	String pas="hello";

	LoginFrame(String ttl)
	{
		super(ttl);

		tpan=new JPanel();
		tpan.setLayout(new GridLayout(2,2,3,3));

		lunm=new JLabel(" User Name ");
		lunm.setFont(new Font("",Font.BOLD,15));
		tunm=new JTextField();
		tunm.setFont(new Font("",Font.BOLD,15));

		lpas=new JLabel(" Password ");
		lpas.setFont(new Font("",Font.BOLD,15));
		tpas=new JPasswordField();
		tpas.setFont(new Font("",Font.BOLD,15));

		tpan.add(lunm);tpan.add(tunm);
		tpan.add(lpas);tpan.add(tpas);

		
		bpan=new JPanel();
		ok=new JButton("Okay");
		ok.setFont(new Font("",Font.BOLD,15));
		ok.addActionListener(this);
		bpan.add(ok);

		add(tpan,BorderLayout.CENTER);
		add(bpan,BorderLayout.SOUTH);


		setVisible(true);
		setSize(300,150);
		setLocation(350,250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}
	public void actionPerformed(ActionEvent e)
	{
		if(unm.equals(tunm.getText().trim()) && pas.equals(tpas.getText().trim()))
		{
			setVisible(false);
			new SubjectChooser("Choose Subject");
		}
	}
}
