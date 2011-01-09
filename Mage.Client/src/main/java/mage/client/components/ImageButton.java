package mage.client.components;

import javax.swing.*;
import java.awt.*;

/**
 * Image based button.
 *
 * @author nantuko
 */
public class ImageButton extends JButton {

    private Image image;
    private String text;

    public ImageButton(String text, Image image) {
        super(new ImageIcon(image));
        this.image = image;
        this.text = text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (text != null) {
            g.setColor(Color.GRAY);
            int dx = 15;
            int dy = 17;
            g.setColor(Color.WHITE);
            if (text.length() > 5) {
                g.drawString(this.text, 8, dy);
            } else {
                g.drawString(this.text, dx, dy);
            }
        }
    }

    public void setImage(Image src) {
        this.image = src;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }

    @Override
    public void setText(String text) {
        this.text = text;
        repaint();
    }
}
