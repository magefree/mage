package mage.client.components;

import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ShadowRenderer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Mage round pane with transparency.
 * Used for tooltips.
 *
 * @author nantuko
 */
public class MageRoundPane extends JPanel {

    private int X_OFFSET = 30;
    private int Y_OFFSET = 30;
    private BufferedImage shadow = null;
    private Color backgroundColor = new Color(255, 255, 255, 200);
    private int alpha = 0;

    @Override
    protected void paintComponent(Graphics g) {
        int x = X_OFFSET;
        int y = Y_OFFSET;
        int w = getWidth() - 2 * X_OFFSET;
        int h = getHeight() - 2 * Y_OFFSET;
        int arc = 10;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (shadow != null) {
            int xOffset = (shadow.getWidth() - w) / 2;
            int yOffset = (shadow.getHeight() - h) / 2;
            g2.drawImage(shadow, x - xOffset, y - yOffset, null);
        }

        //////////////////////////////////////////////////////////////////
        // fill content

        /**
         * Add white translucent substrate
         */
        /*if (alpha != 0) {
            g2.setColor(new Color(255, 255, 255, alpha));
            g2.fillRoundRect(x, y, w, h, arc, arc);
        }*/

        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, w, h, arc, arc);
        //////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////
        // draw border
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(x, y, w, h, arc, arc);
        // ////////////////////////////////////////////////////////////////

        g2.dispose();
    }

	public void setXOffset(int x_offset) {
		X_OFFSET = x_offset;
	}

	public void setYOffset(int y_offset) {
		Y_OFFSET = y_offset;
	}

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        int w = getWidth() - 2 * X_OFFSET;
        int h = getHeight() - 2 * Y_OFFSET;
        int arc = 10;
        int shadowSize = 50;

        shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
        Graphics2D g2 = shadow.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w, h, arc, arc);
        g2.dispose();

        ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f,
                Color.GRAY);
        shadow = renderer.createShadow(shadow);
    }

    public void showDialog(boolean bShow) {
        setVisible(bShow);
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;
}
