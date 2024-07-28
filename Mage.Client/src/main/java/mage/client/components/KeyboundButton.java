package mage.client.components;

import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;

import javax.swing.*;
import java.awt.*;

/**
 * GUI component: button with hotkey info on it
 *
 * @author Campbell Suter <znix@znix.xyz>, JayDi85
 */
public class KeyboundButton extends JButton {

    private float guiScale = 1.0f;
    private final String key;
    private boolean showKey;
    private String drawingText;
    private Font keyFont;

    private boolean tinting = false;

    public KeyboundButton(String key, boolean showKey) {
        this.key = key;
        this.showKey = showKey;
        updateFont();
        updateDrawingText();
    }

    private void updateFont() {
        this.keyFont = new Font(Font.SANS_SERIF, Font.BOLD, sizeMod(13));
    }

    private void updateDrawingText() {
        if (this.showKey) {
            this.drawingText = PreferencesDialog.getCachedKeyText(key);
        } else {
            this.drawingText = "";
        }
    }

    public void updateGuiScale(float guiScale) {
        this.guiScale = guiScale;
        updateFont();
        invalidate();
    }

    public void setShowKey(boolean showKey) {
        this.showKey = showKey;
        updateDrawingText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // must ignore look and fill, so no calls of super.paintComponent(g);
        if (ui != null && g != null) {
            Graphics sg = g.create();
            try {
                ui.update(sg, this);

                if (tinting) {
                    sg.setColor(new Color(0, 0, 0, 32));
                    sg.fillRoundRect(sizeMod(2), sizeMod(2), getWidth() - sizeMod(4), getHeight() - sizeMod(4), sizeMod(6), sizeMod(6));
                }
                sg.setColor(tinting ? Color.lightGray : Color.white);

                if (!this.drawingText.isEmpty()) {
                    sg.setFont(keyFont);

                    int textWidth = sg.getFontMetrics(keyFont).stringWidth(this.drawingText);
                    int centerX = (getWidth() - textWidth) / 2;

                    sg.drawString(this.drawingText, centerX, sizeMod(28));
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

    private int sizeMod(int value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScale);
    }

    private float sizeMod(float value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScale);
    }
}
