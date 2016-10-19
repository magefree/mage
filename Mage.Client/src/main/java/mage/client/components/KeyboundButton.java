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

	private final String key;
	private static final Font keyFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);

	public KeyboundButton(String key) {
		this.key = key;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (ui != null && g != null) {
			Graphics sg = g.create();
			try {
				ui.update(sg, this);
				sg.setColor(Color.white);
				sg.setFont(keyFont);

				String text = PreferencesDialog.getCachedKeyText(key);
				int textWidth = sg.getFontMetrics(keyFont).stringWidth(text);
				int centerX = (getWidth() - textWidth) / 2;

				sg.drawString(text, centerX, 28);
			} finally {
				sg.dispose();
			}
		}
	}
}
