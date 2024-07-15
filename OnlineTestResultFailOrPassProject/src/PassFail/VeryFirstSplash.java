package PassFail;

import javax.swing.*;

import java.awt.*;

class VeryFirstSplash extends JWindow {
	private static final long serialVersionUID = 1L;

	JPanel pan;
	JLabel lab;

	VeryFirstSplash() {
		setLayout(new GridLayout(1, 1));
		pan = new JPanel();
		pan.setLayout(new GridLayout(1, 1));
		pan.setBackground(Color.PINK);
		lab = new JLabel("Welcome To Online Examination System");
		lab.setForeground(Color.WHITE);
		lab.setFont(new Font("", Font.BOLD, 30));
		pan.add(lab, BorderLayout.CENTER);
		add(pan, BorderLayout.CENTER);

		setVisible(true);
		setSize(600, 200);
		setLocation(200, 300);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
			Thread.sleep(5000);
			setVisible(false);
			new SelectionMenuFrame("Selection Platform");
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		new VeryFirstSplash();
	}
}
