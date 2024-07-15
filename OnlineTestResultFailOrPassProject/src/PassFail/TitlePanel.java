package PassFail;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

class TitlePanel extends JPanel {
	JPanel fpan;
	JPanel span;
	BevelBorder border;

	JLabel maintitle;
	JLabel subtitle;

	GraphicsEnvironment ge;
	String[] fnt;
	Font f;

	TitlePanel() {
		setLayout(new BorderLayout());
		border = (BevelBorder) BorderFactory.createRaisedBevelBorder();

		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fnt = ge.getAvailableFontFamilyNames();

		f = new Font(fnt[57], Font.BOLD, 25);

		fpan = new JPanel();
		maintitle = new JLabel("Welcome To LESP kupwad");
		maintitle.setFont(f);
		fpan.add(maintitle);
		fpan.setBorder(border);
		add(fpan, BorderLayout.NORTH);

		f = new Font(fnt[56], Font.BOLD, 25);

		span = new JPanel();
		subtitle = new JLabel("Aptitude Test ");
		subtitle.setFont(f);
		span.add(subtitle);
		span.setBorder(border);
		add(span, BorderLayout.SOUTH);
	}
}
