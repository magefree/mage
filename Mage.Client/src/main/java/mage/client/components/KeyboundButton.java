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

    private boolean tinting = false;

    public KeyboundButton(String key, boolean drawText) {
        if (drawText) {
            text = PreferencesDialog.getCachedKeyText(key);
        } else {
            text = "";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (ui != null && g != null) {
            Graphics sg = g.create();
            try {
                ui.update(sg, this);

                if (tinting) {
                    sg.setColor(new Color(0, 0, 0, 32));
                    sg.fillRoundRect(2, 2, getWidth() - 4 , getHeight() - 4, 6, 6);
                }
                sg.setColor(tinting ? Color.lightGray : Color.white);

                if (!text.isEmpty()) {
                    sg.setFont(keyFont);

                    int textWidth = sg.getFontMetrics(keyFont).stringWidth(text);
                    int centerX = (getWidth() - textWidth) / 2;

                    sg.drawString(text, centerX, 28);
                }
            } finally {
                sg.dispose();
            }
        }
    }

    public void setTint(boolean tinting) {
        this.tinting = tinting;
        repaint();
    }
}
