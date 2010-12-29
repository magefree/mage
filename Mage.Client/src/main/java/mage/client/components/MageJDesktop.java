package mage.client.components;

import com.sun.java.swing.Painter;

import javax.swing.*;
import java.awt.*;

/**
 * Overrides JDesktopPane settings for Nimbus LAF.
 *
 * @author nantuko
 */
public class MageJDesktop extends JDesktopPane {

    @Override
    public void updateUI() {
        if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
            UIDefaults map = new UIDefaults();
            Painter painter = new Painter() {

                Color color = null;

                @Override
                public void paint(Graphics2D g, Object c, int w, int h) {
                    g.setColor(color == null ? UIManager.getDefaults().getColor("desktop") : color);
                    g.fillRect(0,0,w,h);
                }
            };
            map.put("DesktopPane[Enabled].backgroundPainter", painter);
            putClientProperty("Nimbus.Overrides", map);
        }
        super.updateUI();
    }
}
