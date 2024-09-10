package mage.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * GUI component: JPanel with background image
 */
public class ImagePanel extends JPanel {


    private BufferedImage image;
    private ImagePanelStyle style;
    private float alignmentX = 0.5f;
    private float alignmentY = 0.5f;

    public ImagePanel(BufferedImage image) {
        this(image, ImagePanelStyle.TILED);
    }

    public ImagePanel(BufferedImage image, ImagePanelStyle style) {
        this.image = image;
        this.style = style;
        setLayout(new BorderLayout());

        setOpaque(true);
    }

    public void setImageAlignmentX(float alignmentX) {
        this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
    }

    public void setImageAlignmentY(float alignmentY) {
        this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;

    }

    public void add(JComponent component) {
        add(component, null);
    }

    public void add(JComponent component, Object constraints) {
        component.setOpaque(false);

        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            JViewport viewport = scrollPane.getViewport();
            viewport.setOpaque(false);
            Component c = viewport.getView();

            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
        }

        super.add(component, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null)
            return;

        switch (style) {
            case TILED:
                drawTiled(g);
                break;
            case SCALED:
                Dimension d = getSize();
                g.drawImage(image, 0, 0, d.width, d.height, null);
                break;
            case ACTUAL:
                drawActual(g);
                break;
        }
    }

    private void drawTiled(Graphics g) {
        Dimension d = getSize();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        for (int x = 0; x < d.width; x += width) {
            for (int y = 0; y < d.height; y += height) {
                g.drawImage(image, x, y, null, null);
            }
        }
    }

    private void drawActual(Graphics g) {
        Dimension d = getSize();
        float x = (d.width - image.getWidth(null)) * alignmentX;
        float y = (d.height - image.getHeight(null)) * alignmentY;
        g.drawImage(image, (int) x, (int) y, this);
    }
}
