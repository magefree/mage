package mage.client.components.ability;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.metal.MetalScrollBarUI;

/**
 * Custom scroll bar.
 *
 * @author nantuko
 */
public class MageScrollbarUI extends MetalScrollBarUI {

    private static int ANTI_WIDTH = -3;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        decreaseButton = new MageScrollButton(orientation, scrollBarWidth + ANTI_WIDTH, isFreeStanding);
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        increaseButton = new MageScrollButton(orientation, scrollBarWidth + ANTI_WIDTH, isFreeStanding);
        return increaseButton;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            int width = trackBounds.width - 4 + ANTI_WIDTH;
            int height = trackBounds.height;

            g2.translate(trackBounds.x + 2, trackBounds.y);

            Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(Color.BLACK);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            g2.drawLine(-1, 0, -1, height);
            g2.drawLine(-2, 0, -2, height);

            g2.drawLine(width, 0, width, height);
            g2.drawLine(width + 1, 0, width + 1, height);

            RoundRectangle2D roundCasing = new RoundRectangle2D.Double(0, 2, width, height - 4, width, width);
            Area area = new Area(casing);
            area.subtract(new Area(roundCasing));
            g2.fill(area);

            g2.translate(-trackBounds.x - 2, -trackBounds.y);
        } else {
            int width = trackBounds.width;
            int height = trackBounds.height - 4 + ANTI_WIDTH;

            g2.translate(trackBounds.x, trackBounds.y + 2);

            Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(Color.BLACK);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            g2.drawLine(0, -1, width, -1);
            g2.drawLine(0, -2, width, -2);

            g2.drawLine(0, height, width, height);
            g2.drawLine(0, height + 1, width, height + 1);

            RoundRectangle2D roundCasing = new RoundRectangle2D.Double(2, 0, width - 4, height, height, height);
            Area area = new Area(casing);
            area.subtract(new Area(roundCasing));
            g2.fill(area);

            g2.translate(-trackBounds.x, -trackBounds.y - 2);
        }

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            g2.translate(thumbBounds.x + 1, thumbBounds.y + 2);

            int width = thumbBounds.width - 3 + ANTI_WIDTH;
            int height = thumbBounds.height - 4;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, width, width);
            g2.setColor(Color.BLACK);
            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, new Color(0x818a9b), 0, height, new Color(0x3a4252)));
            g2.fill(casing);
            g2.setPaint(paint);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 1, -thumbBounds.y - 2);
        } else {
            g2.translate(thumbBounds.x + 2, thumbBounds.y + 1);

            int width = thumbBounds.width - 4;
            int height = thumbBounds.height - 3 + ANTI_WIDTH;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, height, height);
            g2.setColor(Color.BLACK);

            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, new Color(0x818a9b), 0, height, new Color(0x3a4252)));
            g2.fill(casing);
            g2.setPaint(paint);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 2, -thumbBounds.y - 1);
        }
    }
}
