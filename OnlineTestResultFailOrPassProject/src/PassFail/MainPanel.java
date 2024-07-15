package PassFail;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;

class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Image img;

	MainPanel(Image img) {
		try {
			this.img = img;
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "" + e);
		}
	}

	public void setImage(Image img) {
		this.img = img;
		repaint();
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
}
