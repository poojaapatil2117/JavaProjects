package PassFail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AdminClassForChoose extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton SelctSubForFillQue;
	JButton DisplayRsultLlist;
	JPanel panel;

	public AdminClassForChoose(String ttl) {

		super(ttl);

		setLayout(new FlowLayout());
		SelctSubForFillQue = new JButton("Fill Quetion");
		SelctSubForFillQue.setFont(new Font("", Font.BOLD, 15));
		SelctSubForFillQue.addActionListener(this);

		DisplayRsultLlist = new JButton("Result List");
		DisplayRsultLlist.setFont(new Font("", Font.BOLD, 15));
		DisplayRsultLlist.addActionListener(this);

		add(SelctSubForFillQue);
		add(DisplayRsultLlist);

		setVisible(true);
		setSize(300, 300);
		setLocation(350, 250);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == SelctSubForFillQue) {
			new SubjectChooserForFillQuetion("Hello");
		}
		if (e.getSource() == DisplayRsultLlist) {
			try {
				new ResultDatabaseInterfaceGUI("Result List");
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
