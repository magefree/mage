package mage.client.components.ability;

import mage.client.themes.ThemeManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.metal.MetalScrollButton;


/**
 * Buttons for scroll bar.
 *
 * @author nantuko
 */
public class MageScrollButton extends MetalScrollButton {

    private static final ImageIcon buttonLeft;
    private static final ImageIcon buttonRight;
    private static final ImageIcon buttonUp;
    private static final ImageIcon buttonDown;

    private int width;

    static {
        buttonLeft = new ImageIcon(ThemeManager.getCurrentTheme().getResourceImage("/buttons/left.png"));
        buttonRight = new ImageIcon(ThemeManager.getCurrentTheme().getResourceImage("/buttons/right.png"));
        buttonUp = new ImageIcon(ThemeManager.getCurrentTheme().getResourceImage("/buttons/up.png"));
        buttonDown = new ImageIcon(ThemeManager.getCurrentTheme().getResourceImage("/buttons/down.png"));
    }

    public MageScrollButton(int direction, int width, boolean freeStanding) {
        super(direction, width, freeStanding);
        setOpaque(false);
        this.width = width;
        buttonUp.setImage(buttonUp.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH));
        buttonDown.setImage(buttonDown.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH));
        buttonLeft.setImage(buttonLeft.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH));
        buttonRight.setImage(buttonRight.getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH));
    }

    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, width);
    }

    @Override
    public void paint(Graphics g) {
        switch (getDirection()) {
        case BasicArrowButton.WEST:
            buttonLeft.paintIcon(null, g, 0, 0);
            break;
        case BasicArrowButton.EAST:
            buttonRight.paintIcon(null, g, 0, 0);
            break;
        case BasicArrowButton.NORTH:
            buttonUp.paintIcon(null, g, 0, 0);
            break;
        case BasicArrowButton.SOUTH:
            buttonDown.paintIcon(null, g, 0, 0);
            break;
        }
    }
}
