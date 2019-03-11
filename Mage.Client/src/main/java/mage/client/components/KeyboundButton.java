package mage.client.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author Campbell Suter <znix@znix.xyz>
 */
public class KeyboundButton extends JButton {

	private final String text;
	private static final Font keyFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);

	private boolean pressing = false;

	public KeyboundButton(String key) {
		text = PreferencesDialog.getCachedKeyText(key);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (ui != null && g != null) {
			Graphics sg = g.create();
			try {
				ui.update(sg, this);
				if (pressing) {
					sg.setColor(new Color(0, 0, 0, 32));
					sg.fillRoundRect(2, 2, getWidth() - 4 , getHeight() - 4, 6, 6);
				}
				sg.setColor(pressing ? Color.lightGray : Color.white);
				sg.setFont(keyFont);

				int textWidth = sg.getFontMetrics(keyFont).stringWidth(text);
				int centerX = (getWidth() - textWidth) / 2;

				sg.drawString(text, centerX, 28);
			} finally {
				sg.dispose();
			}
		}
	}

	public void setPressing(boolean pressing) {
		this.pressing = pressing;
		repaint();
	}

}
