package mage.client.components;

import mage.client.components.ability.BackgroundPainter;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

/**
 * GUI: component for display chats and game logs. Support html, mana symbols and popups.
 *
 * @author nantuko, JayDi85
 */
public class ColorPane extends MageEditorPane {

    public ColorPane() {
        super();
    }

    /**
     * This method solves the known issue with Nimbus LAF background
     * transparency and background color.
     *
     * @param color
     */
    public void setExtBackgroundColor(Color color) {
        setBackground(new Color(0, 0, 0, 0));
        JPanel jPanel = new JPanel();
        jPanel.setBackground(color);
        setLayout(new BorderLayout());
        add(jPanel);
    }

    public void setExtBackgroundColor(BackgroundPainter backgroundPainter) {
        setBackground(new Color(0, 0, 0, 0));
        JXPanel jPanel = new JXPanel();
        jPanel.setBackgroundPainter(backgroundPainter);
        setLayout(new BorderLayout());
        add(jPanel);
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintChildren(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintChildren(g);
    }
}
