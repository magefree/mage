package mage.client.components;

import mage.client.util.Command;
import mage.client.util.GUISizeHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI component. Image button with hover support and GUI scale support
 *
 * @author nantuko, JayDi85
 */
public class HoverButton extends JPanel implements MouseListener {

    float guiScaleMod = 1.0f;

    private Image image;
    private Image hoverImage;
    private Image disabledImage;
    private Image selectedImage;
    protected Image overlayImage;
    private Rectangle imageSize;
    private Rectangle buttonSize;
    private String text;
    private boolean textAlwaysVisible = false;

    // real offset setup in constructor due gui scale
    private int textOffsetY = 0;
    private int textOffsetButtonY = 2;
    private int textOffsetX = -1;
    private int topTextOffsetX = -1;

    private Dimension overlayImageSize;

    private String topText;
    private Image topTextImage;
    private final List<Image> topTextImagesRight = new ArrayList<>();
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
    private final Rectangle centerTextArea;
    private Color centerTextColor = new Color(200, 210, 0, 200);
    private Color origCenterTextColor = new Color(200, 210, 0, 200);
    private final Color textBGColor = Color.black;

    final Font textFont;
    final Font textFontMini;
    final Font textSetFontBoldMini;
    final Font textSetFontBold;

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
        this(text, image, hover, null, disabled, size, 1.0f);
    }

    public HoverButton(String text, Image image, Image hover, Image disabled, Rectangle size, float guiScaleMod) {
        this(text, image, hover, null, disabled, size, guiScaleMod);
    }

    public HoverButton(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size) {
        this(text, image, hover, selected, disabled, size, 1.0f);
    }

    public HoverButton(String text, Image image, Image hover, Image selected, Image disabled, Rectangle size, float guiScaleMod) {
        this.image = image;
        this.hoverImage = hover;
        this.selectedImage = selected;
        this.disabledImage = disabled;
        this.imageSize = size; // already scaled
        this.text = text;
        this.guiScaleMod = guiScaleMod;
        setOpaque(false);
        addMouseListener(this);

        // late init due gui scale settings
        this.setFont(this.getFont().deriveFont(sizeMod(this.getFont().getSize2D())));
        this.centerTextArea = new Rectangle(sizeMod(5), sizeMod(18), sizeMod(75), sizeMod(40));
        textFont = new Font("Arial", Font.PLAIN, sizeMod(12));
        textFontMini = new Font("Arial", Font.PLAIN, sizeMod(11));
        textSetFontBoldMini = new Font("Arial", Font.BOLD, sizeMod(12));
        textSetFontBold = new Font("Arial", Font.BOLD, sizeMod(14));

        textOffsetY = 0;
        textOffsetButtonY = sizeMod(2);
        textOffsetX = -1; // no scale, it's calc on first usage
        topTextOffsetX = -1; // no scale, it's calc on first usage
    }

    private int sizeMod(int value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScaleMod);
    }

    private float sizeMod(float value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScaleMod);
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
            g2d.drawString(topText, topTextOffsetX + sizeMod(1), sizeMod(14));
            g2d.setColor(topTextColor != null ? topTextColor : textColor);
            g2d.drawString(topText, topTextOffsetX, sizeMod(13));
        }
        if (topTextImage != null) {
            g.drawImage(topTextImage, sizeMod(4), sizeMod(3), this);
        }

        int offset = 0;
        for (Image img : topTextImagesRight) {
            g.drawImage(img, this.getWidth() - sizeMod(20), sizeMod(3) + offset, this);
            offset += sizeMod(20);
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
            drawCenteredStringWOutline(g2d, centerText, centerTextArea, new Font("Arial", Font.BOLD, sizeMod(fontSize)));
        }
        g2d.setColor(textColor);
        if (overlayImage != null) {
            g.drawImage(overlayImage, (imageSize.width - overlayImageSize.width) / 2, sizeMod(10), this);
        } else if (set != null) {
            // draw only if it is not current tab
            if (!drawSet) {
                g2d.setFont(textSetFontBoldMini);
                g2d.drawString(set, sizeMod(5), sizeMod(25));
            }
        }

        if (drawSet && set != null) {
            g2d.setFont(textSetFontBold);
            int w = (int) (getWidth() / 2.0);
            int h = (int) (getHeight() / 2.0);
            int dy = overlayImage == null ? sizeMod(15) : sizeMod(25);
            g2d.translate(w + sizeMod(5), h + dy);
            g2d.rotate(-Math.PI / 2.0);
            g2d.drawString(set, 0, 0);
        }
    }

    public void setCenterColor(Color c) {
        centerTextColor = c;
    }

    private int calculateOffset(Graphics2D g2d) {
        // already gui scaled here
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
        // already scaled calc
        if (topTextOffsetX == -1) { // calculate once
            FontRenderContext frc = g2d.getFontRenderContext();
            int textWidth = (int) textFont.getStringBounds(text, frc).getWidth();
            int neededImageWidth = (topTextImage == null ? 0 : topTextImage.getWidth(this));
            int availableXWidth = imageSize.width - neededImageWidth;
            topTextOffsetX = (availableXWidth - textWidth) / 2 + neededImageWidth;
        }
        return topTextOffsetX;
    }

    /**
     * Overrides textColor for the upper text if non-null.
     * If null, return back to textColor.
     *
     * @param textColor
     */
    public void setTopTextColor(Color textColor) {
        this.topTextColor = textColor;
    }

    public void setOverlayImage(Image image) {
        this.overlayImage = image;
        this.overlayImageSize = new Dimension(image.getWidth(null), image.getHeight(null)); // TODO: need sizeMod?
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
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        if (isEnabled() && observer != null) {
            observer.execute();
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

    public void addTopTextImageRight(Image topTextImage) {
        this.topTextImagesRight.add(topTextImage);
    }

    public void clearTopTextImagesRight() {
        this.topTextImagesRight.clear();
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
     * @param g    The Graphics instance.
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
