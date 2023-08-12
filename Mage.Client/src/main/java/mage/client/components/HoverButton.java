package mage.client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import javax.swing.*;

import mage.client.util.Command;

/**
 * Image button with hover.
 *
 * @author nantuko
 */
public class HoverButton extends JPanel implements MouseListener {

    static final int TOP_TEXT_IMAGE_GAP = 3;

    private Image image;
    private Image hoverImage;
    private Image disabledImage;
    private Image selectedImage;
    protected Image overlayImage;
    private Rectangle imageSize;
    private Rectangle buttonSize;
    private String text;
    private boolean textAlwaysVisible = false;
    private int textOffsetY = 0;
    private int textOffsetButtonY = 2;
    private int textOffsetX = -1;
    private int topTextOffsetX = -1;
    private Dimension overlayImageSize;

    private String topText;
    private Image topTextImage;
    private Image topTextImageRight;
    private String centerText;

    private boolean wasHovered = false;
    private boolean isHovered = false;
    private boolean isSelected = false;
    private boolean drawSet = false;
    private String set = null;

    private Command observer = null;
    private Command onHover = null;
    private Color textColor = Color.white;
    private Color topTextColor = null;
    private final Rectangle centerTextArea = new Rectangle(5, 18, 75, 40);
    private Color centerTextColor = new Color(200, 210, 0, 200);
    private Color origCenterTextColor = new Color(200, 210, 0, 200);
    private final Color textBGColor = Color.black;

    static final Font textFont = new Font("Arial", Font.PLAIN, 12);
    static final Font textFontMini = new Font("Arial", Font.PLAIN, 11);
    static final Font textSetFontBoldMini = new Font("Arial", Font.BOLD, 12);
    static final Font textSetFontBold = new Font("Arial", Font.BOLD, 14);

    private boolean useMiniFont = false;

    private boolean alignTextLeft = false;

    Timer faderGainLife = null;
    Timer faderLoseLife = null;
    private int loseX = 0;
    private int gainX = 0;
    private boolean doLoseFade = true;
    private boolean doGainFade = true;

    public HoverButton(String text, Image image, Rectangle size) {
        this(text, image, image, null, image, size);
        if (image == null) {
            throw new IllegalArgumentException("Image can't be null");
        }
    }

    public HoverButton(String text, Image image, Image hover, Image disabled, Rectangle size) {
        this(text, image, hover, null, disabled, size);
    }

    public HoverButton(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size) {
        this.image = image;
        this.hoverImage = hover;
        this.selectedImage = selected;
        this.disabledImage = disabled;
        this.imageSize = size;
        this.text = text;
        setOpaque(false);
        addMouseListener(this);
    }

    public HoverButton(HoverButton button) {
        this(button.text, button.image, button.hoverImage, button.selectedImage, button.disabledImage, button.imageSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isEnabled()) {
            if (isHovered || textAlwaysVisible) {
                if (isHovered) {
                    wasHovered = true;
                    setCenterColor(Color.YELLOW);
                }
                g.drawImage(hoverImage, 0, 0, imageSize.width, imageSize.height, this);
                if (text != null) {
                    if (textColor != null) {
                        g2d.setColor(textColor);
                    }
                    if (useMiniFont) {
                        g2d.setFont(textFontMini);
                    } else {
                        g2d.setFont(textFont);
                    }
                    textOffsetX = calculateOffset(g2d);
                    g2d.drawString(text, textOffsetX, textOffsetY);
                }
            } else {
                if (wasHovered) {
                    wasHovered = false;
                    setCenterColor(origCenterTextColor);
                }
                g.drawImage(image, 0, 0, imageSize.width, imageSize.height, this);
            }
            if (isSelected) {
                if (selectedImage != null) {
                    g.drawImage(selectedImage, 0, 0, imageSize.width, imageSize.height, this);
                } else {
                    System.err.println("No selectedImage for button.");
                }
            }
        } else {
            g.drawImage(disabledImage, 0, 0, imageSize.width, imageSize.height, this);
        }
        if (topText != null) {
            if (useMiniFont) {
                g2d.setFont(textFontMini);
            } else {
                g2d.setFont(textFont);
            }
            topTextOffsetX = calculateOffsetForTop(g2d, topText);
            g2d.setColor(textBGColor);
            g2d.drawString(topText, topTextOffsetX + 1, 14);
            g2d.setColor(topTextColor != null ? topTextColor : textColor);
            g2d.drawString(topText, topTextOffsetX, 13);
        }
        if (topTextImage != null) {
            g.drawImage(topTextImage, 4, 3, this);
        }
        if (topTextImageRight != null) {
            g.drawImage(topTextImageRight, this.getWidth() - 20, 3, this);
        }

        if (centerText != null) {
            g2d.setColor(centerTextColor);
            int fontSize = 40;
            int val = Integer.parseInt(centerText);
            if (val > 9999) {
                fontSize = 24;
            } else if (val > 999) {
                fontSize = 28;
            } else if (val > 99) {
                fontSize = 34;
            }
            drawCenteredStringWOutline(g2d, centerText, centerTextArea, new Font("Arial", Font.BOLD, fontSize));
        }
        g2d.setColor(textColor);
        if (overlayImage != null) {
            g.drawImage(overlayImage, (imageSize.width - overlayImageSize.width) / 2, 10, this);
        } else if (set != null) {
            // draw only if it is not current tab
            if (!drawSet) {
                g2d.setFont(textSetFontBoldMini);
                g2d.drawString(set, 5, 25);
            }
        }

        if (drawSet && set != null) {
            g2d.setFont(textSetFontBold);
            int w = (int) (getWidth() / 2.0);
            int h = (int) (getHeight() / 2.0);
            int dy = overlayImage == null ? 15 : 25;
            g2d.translate(w + 5, h + dy);
            g2d.rotate(-Math.PI / 2.0);
            g2d.drawString(set, 0, 0);
        }
    }

    public void setCenterColor(Color c) {
        centerTextColor = c;
    }

    private int calculateOffset(Graphics2D g2d) {
        if (textOffsetX == -1) { // calculate once
            FontRenderContext frc = g2d.getFontRenderContext();
            int textWidth = (int) textFont.getStringBounds(text, frc).getWidth();
            if (textWidth > buttonSize.width) {
                g2d.setFont(textFontMini);
                useMiniFont = true;
                frc = g2d.getFontRenderContext();
                textWidth = (int) textFontMini.getStringBounds(text, frc).getWidth();
            }
            if (alignTextLeft) {
                textOffsetX = 0;
            } else {
                textOffsetX = (imageSize.width - textWidth) / 2;
            }
        }
        return textOffsetX;
    }

    private int calculateOffsetForTop(Graphics2D g2d, String text) {
        if (topTextOffsetX == -1) { // calculate once
            FontRenderContext frc = g2d.getFontRenderContext();
            int textWidth = (int) textFont.getStringBounds(text, frc).getWidth();
            int neededImageWidth = (topTextImage == null ? 0 : topTextImage.getWidth(this));
            int availableXWidth = imageSize.width - neededImageWidth;
            topTextOffsetX = (availableXWidth - textWidth) / 2 + neededImageWidth;
        }
        return topTextOffsetX;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    /**
     * Overrides textColor for the upper text if non-null.
     * If null, return back to textColor.
     * @param textColor
     */
    public void setTopTextColor(Color textColor) {
        this.topTextColor = textColor;
    }

    public void setOverlayImage(Image image) {
        this.overlayImage = image;
        this.overlayImageSize = new Dimension(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHovered = true;
        this.repaint();
        if (onHover != null) {
            onHover.execute();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovered = false;
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (isEnabled() && observer != null) {
                observer.execute();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void setObserver(Command observer) {
        this.observer = observer;
    }

    public void setOnHover(Command onHover) {
        this.onHover = onHover;
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        this.textOffsetY = r.height - this.textOffsetButtonY;
        this.buttonSize = r;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.textOffsetY = height - this.textOffsetButtonY;
        this.buttonSize = new Rectangle(x, y, width, height);
    }

    public void setTextOffsetButtonY(int textOffsetButtonY) {
        this.textOffsetButtonY = textOffsetButtonY;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void changeSelected() {
        this.isSelected = !this.isSelected;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void drawSet() {
        this.drawSet = true;
    }

    public void update(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size) {
        this.image = image;
        this.hoverImage = hover;
        this.selectedImage = selected;
        this.disabledImage = disabled;
        this.imageSize = size;
        this.text = text;
        repaint();
    }

    public void execute() {
        if (isEnabled() && observer != null) {
            observer.execute();
        }
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public void setTopTextImage(Image topTextImage) {
        this.topTextImage = topTextImage;
        this.textOffsetX = -1; // rest for new calculation
    }

    public void setTopTextImageRight(Image topTextImage) {
        this.topTextImageRight = topTextImage;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public void setTextAlwaysVisible(boolean textAlwaysVisible) {
        this.textAlwaysVisible = textAlwaysVisible;
    }

    public void setAlignTextLeft(boolean alignTextLeft) {
        this.alignTextLeft = alignTextLeft;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @param font
     */
    public void drawCenteredStringWOutline(Graphics2D g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);

        GlyphVector gv = font.createGlyphVector(g.getFontRenderContext(), text);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.drawGlyphVector(gv, x, y);

        g.translate(x - 1, y - 1);
        for (int i = 0; i < text.length(); i++) {
            g.setColor(Color.BLACK);
            g.draw(gv.getGlyphOutline(i));
        }
        g.translate(-x + 1, -y + 1);

    }

    public void gainLifeDisplay() {
        if (faderGainLife == null && doGainFade) {
            doGainFade = false;
            faderGainLife = new Timer(50, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    gainX++;
                    int alpha = Math.max(250 - gainX, 200);
                    setCenterColor(new Color(2 * gainX, 210, 255, alpha));
                    repaint();
                    if (gainX >= 100) {
                        setCenterColor(new Color(200, 210, 0, 200));
                        gainX = 100;

                        if (faderGainLife != null) {
                            faderGainLife.stop();
                            faderGainLife.setRepeats(false);
                            faderGainLife.setDelay(50000);
                        }
                    }
                }
            });
            gainX = 0;
            faderGainLife.setInitialDelay(25);
            faderGainLife.setRepeats(true);
            faderGainLife.start();
        }
    }

    public void loseLifeDisplay() {
        if (faderLoseLife == null && doLoseFade) {
            doLoseFade = false;
            faderLoseLife = new Timer(50, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    loseX++;
                    int alpha = Math.max(250 - loseX, 200);
                    setCenterColor(new Color(250 - loseX / 2, 130 + loseX, 0, alpha));
                    repaint();
                    if (loseX >= 100) {
                        setCenterColor(new Color(200, 210, 0, 200));
                        loseX = 100;
                        stopLifeDisplay();

                        if (faderLoseLife != null) {
                            faderLoseLife.stop();
                            faderLoseLife.setRepeats(false);
                            faderLoseLife.setDelay(50000);
                        }
                    }
                }
            });
            loseX = 0;
            faderLoseLife.setInitialDelay(25);
            faderLoseLife.setRepeats(true);
            faderLoseLife.start();
        }
    }

    public void stopLifeDisplay() {

        if (faderGainLife != null && gainX >= 100) {
            faderGainLife.stop();
            faderGainLife = null;
        }
        doGainFade = true;
        if (faderLoseLife != null && loseX >= 100) {
            faderLoseLife.stop();
            faderLoseLife = null;
        }
        doLoseFade = true;
    }
}
