package mage.client.components.ability;

import org.jdesktop.swingx.painter.AbstractPainter;
import sun.swing.CachedPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Background painter.
 *
 * @author nantuko
 */
public class BackgroundPainter extends AbstractPainter {

    private Color bgColor = Color.black;

    float bgalpha = 0.6f;

    public BackgroundPainter() {
        super();
        setAntialiasing(true);
    }

    @Override
    protected void doPaint(Graphics2D g2, Object o, int i, int i1) {
        float alpha = bgalpha;
        Component c = (Component)o;
        Composite composite = g2.getComposite();
        if (composite instanceof AlphaComposite) {
            alpha *= ((AlphaComposite) composite).getAlpha();
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(bgColor);

        RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, c.getWidth() - 1, c.getHeight() - 1, 24, 24);
        g2.fill(rect);

        Ellipse2D ellipse = new Ellipse2D.Double(-c.getWidth(),
                c.getHeight() / 3.0, c.getWidth() * 3.0,
                c.getHeight() * 2.0);

        Area area = new Area(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        area.subtract(new Area(ellipse));
        area.intersect(new Area(rect));

        alpha = 0.1f;
        if (composite instanceof AlphaComposite) {
            alpha *= ((AlphaComposite) composite).getAlpha();
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(new Color(1.0f, 1.0f, 1.0f));
        g2.fill(area);
        g2.setComposite(composite);
    }
}