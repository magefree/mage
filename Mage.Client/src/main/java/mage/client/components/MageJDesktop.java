package mage.client.components;

//import com.sun.java.swing.Painter;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JDesktopPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.jdesktop.swingx.painter.Painter;

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
            Painter painter = (g, c, w, h) -> {
                g.setColor( UIManager.getDefaults().getColor("desktop") );
                g.fillRect(0,0,w,h);
            };
            map.put("DesktopPane[Enabled].backgroundPainter", painter);
            putClientProperty("Nimbus.Overrides", map);
        }
        super.updateUI();
    }
}
