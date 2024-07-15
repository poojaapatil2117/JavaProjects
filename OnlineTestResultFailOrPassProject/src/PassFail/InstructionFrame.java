package PassFail;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class InstructionFrame extends JFrame implements ActionListener,ItemListener
{
	private static final long serialVersionUID = 1L;
	JPanel ipan;
	static JLabel i1,i2,i3,i4,i5;

	static JPanel apan;
	static JButton ok;
	static JCheckBox agr;

	
	static boolean status;  //why

	InstructionFrame(String ttl)
	{
		super(ttl);
		
		ipan=new JPanel();
		ipan.setBorder(BorderFactory.createTitledBorder("Instructions"));
		ipan.setLayout(new GridLayout(5,1));

		i1=new JLabel("(1).You Can Submit Your Answer Only Once");
		i1.setFont(new Font("",Font.ITALIC,20));
		ipan.add(i1);
		i2=new JLabel("(2).Any MisBehaving Will Lead To Disqualify");
		i2.setFont(new Font("",Font.ITALIC,20));
		ipan.add(i2);
		i3=new JLabel("(3).Passing Marks 5 Out Of 20");
		i3.setFont(new Font("",Font.ITALIC,20));
		ipan.add(i3);
		i4=new JLabel("(4).There Are 20 Questions For One Mark Each");
		i4.setFont(new Font("",Font.ITALIC,20));
		ipan.add(i4);
		i5=new JLabel("(5).You Have Selected "+SubjectChooser.subject+" Subject");  //change is : use subject instead of getSubject() method
		i5.setFont(new Font("",Font.ITALIC,20));
		ipan.add(i5);

		add(ipan,BorderLayout.CENTER);

		apan=new JPanel();
		apan.setBorder(BorderFactory.createTitledBorder("Agriment"));
		agr=new JCheckBox(" I / We Agree All Above Terms");
		agr.addItemListener(this);
		agr.setFont(new Font("",Font.BOLD,15));
		apan.add(agr);

		ok=new JButton("Continue");
		ok.addActionListener(this);
		ok.setEnabled(false);
		apan.add(ok);

		add(apan,BorderLayout.SOUTH);

		setVisible(true);
		setLocation(250,150);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		status=false;
	}
	public void itemStateChanged(ItemEvent e)
	{
		if(agr.isSelected())
		{
			ok.setEnabled(true);
			status=true;
		}
		else
		{
			ok.setEnabled(false);
			status=false;
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
		//new StartOfTest("Good Luck");
		new StartOfTest("Best Luck");
	}
	static void setInstruction1(String ins)
	{
		i1.setText(ins);
	}
	static void setInstruction2(String ins)
	{
		i2.setText(ins);
	}
	static void setInstruction3(String ins)
	{
		i3.setText(ins);
	}
	static void setInstruction4(String ins)
	{
		i4.setText(ins);
	}
	static void setInstruction5(String ins)
	{
		i5.setText(ins);
	}
	static void makeDisable()
	{
		apan.setVisible(false);
		agr.setVisible(false);
		ok.setVisible(false);
	}
}
