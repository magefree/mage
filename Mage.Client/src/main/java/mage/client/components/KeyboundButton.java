package mage.client.components;

import mage.client.dialog.PreferencesDialog;

import javax.swing.*;
import java.awt.*;

/**
 * GUI component: button with hotkey info on it
 *
 * @author Campbell Suter <znix@znix.xyz>, JayDi85
 */
public class KeyboundButton extends JButton {

    private final String key;
    private boolean showKey;
    private String drawingText;
    private static final Font keyFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);

    private boolean tinting = false;

    public KeyboundButton(String key, boolean showKey) {
        this.key = key;
        this.showKey = showKey;
        updateDrawingText();
    }

    private void updateDrawingText() {
        if (this.showKey) {
            this.drawingText = PreferencesDialog.getCachedKeyText(key);
        } else {
            this.drawingText = "";
        }
    }

    public void setShowKey(boolean showKey) {
        this.showKey = showKey;
        updateDrawingText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (ui != null && g != null) {
            Graphics sg = g.create();
            try {
                ui.update(sg, this);

                if (tinting) {
                    sg.setColor(new Color(0, 0, 0, 32));
                    sg.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 6, 6);
                }
                sg.setColor(tinting ? Color.lightGray : Color.white);

                if (!this.drawingText.isEmpty()) {
                    sg.setFont(keyFont);

                    int textWidth = sg.getFontMetrics(keyFont).stringWidth(this.drawingText);
                    int centerX = (getWidth() - textWidth) / 2;

                    sg.drawString(this.drawingText, centerX, 28);
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
