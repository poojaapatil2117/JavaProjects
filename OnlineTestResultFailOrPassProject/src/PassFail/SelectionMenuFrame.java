package PassFail;

import javax.swing.*;
import java.awt.event.*;

class SelectionMenuFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	JMenuBar bar;

	JMenu client;
	JMenuItem ins;
	JMenuItem soe;
	JMenuItem ex;

	JMenu abt;
	JMenuItem dev;
	JMenuItem sys;

	JMenu admin;
	JMenuItem adminLogIn;

	SelectionMenuFrame(String ttl) {
		super(ttl);

		bar = new JMenuBar();

		client = new JMenu("Client");
		ins = new JMenuItem("Instructions");
		ins.addActionListener(this);
		client.add(ins);
		soe = new JMenuItem("Start Exam");
		soe.addActionListener(this);
		client.add(soe);
		ex = new JMenuItem("Exit");
		ex.addActionListener(this);
		client.add(ex);
		bar.add(client);

		abt = new JMenu("About");
		dev = new JMenuItem("Developer Team");
		dev.addActionListener(this);
		abt.add(dev);
		sys = new JMenuItem("Product Help");
		sys.addActionListener(this);
		abt.add(sys);

		bar.add(abt);

		admin = new JMenu("Admin");
		adminLogIn = new JMenuItem("LogIn");
		adminLogIn.addActionListener(this);
		admin.add(adminLogIn);
		bar.add(admin);

		setJMenuBar(bar);

		setVisible(true);
		setLocation(350, 250);
		setSize(300, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ins) {
			new InstructionFrame("Instructions Must Be Read");
			InstructionFrame.makeDisable();
		}
		if (e.getSource() == soe) {
			new LoginFrame("Login System");
		}
		if (e.getSource() == adminLogIn) {
			new AdminLogInFrame("Admin LogIn");
		}
		if (e.getSource() == ex) {
			System.exit(0);
		}

		/*
		 * if(e.getSource()==dev) { new DeveloperInfoFrame("We Are Developers.."); }
		 * if(e.getSource()==sys) { new ProductHelpFrame("product Help"); }
		 */

	}
}
