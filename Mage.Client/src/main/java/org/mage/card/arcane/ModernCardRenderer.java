/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
import mage.constants.CardType;
import mage.view.CardView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/*
    private void cardRendererBasedRender(Graphics2D g) {
        // Prepare for draw
        g.translate(cardXOffset, cardYOffset);
        int cardWidth = this.cardWidth - cardXOffset;
        int cardHeight = this.cardHeight - cardYOffset;

        // AA on
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Renderer
        CardRenderer render = new ModernCardRenderer(gameCard, transformed);
        Image img = imagePanel.getSrcImage();
        if (img != null) {
            render.setArtImage(img);
        }
        render.draw(g, cardWidth, cardHeight);
    }
 */
/**
 * @author stravant@gmail.com
 *
 * Base rendering class for new border cards
 */
public class ModernCardRenderer extends CardRenderer {

    private final static Logger LOGGER = Logger.getLogger(ModernCardRenderer.class);

    ///////////////////////////////////////////////////////////////////////////
    // Textures for modern frame cards
    private static TexturePaint loadBackgroundTexture(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
    }

    private static BufferedImage loadFramePart(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        return CardRendererUtils.toBufferedImage(icon.getImage());
    }

    private static Font loadFont(String name) {
        try {
            return Font.createFont(
                    Font.TRUETYPE_FONT,
                    ModernCardRenderer.class.getResourceAsStream("/cardrender/" + name + ".ttf"));
        } catch (IOException e) {
            LOGGER.info("Failed to load font `" + name + "`, couldn't find resource.");
        } catch (FontFormatException e) {
            LOGGER.info("Failed to load font `" + name + "`, bad format.");
        }
        return new Font("Arial", Font.PLAIN, 1);
    }
    public static final Font BASE_BELEREN_FONT = loadFont("beleren-bold");

    public static final Paint BG_TEXTURE_WHITE = loadBackgroundTexture("white");
    public static final Paint BG_TEXTURE_BLUE = loadBackgroundTexture("blue");
    public static final Paint BG_TEXTURE_BLACK = loadBackgroundTexture("black");
    public static final Paint BG_TEXTURE_RED = loadBackgroundTexture("red");
    public static final Paint BG_TEXTURE_GREEN = loadBackgroundTexture("green");
    public static final Paint BG_TEXTURE_GOLD = loadBackgroundTexture("gold");
    public static final Paint BG_TEXTURE_ARTIFACT = loadBackgroundTexture("artifact");
    public static final Paint BG_TEXTURE_LAND = loadBackgroundTexture("land");
    public static final Paint BG_TEXTURE_VEHICLE = loadBackgroundTexture("vehicle");

    public static final BufferedImage FRAME_INVENTION = loadFramePart("invention_frame");

    public static final Color BORDER_WHITE = new Color(216, 203, 188);
    public static final Color BORDER_BLUE = new Color(20, 121, 175);
    public static final Color BORDER_BLACK = new Color(45, 45, 35);
    public static final Color BORDER_RED = new Color(201, 71, 58);
    public static final Color BORDER_GREEN = new Color(4, 136, 69);
    public static final Color BORDER_GOLD = new Color(255, 228, 124);
    public static final Color BORDER_COLORLESS = new Color(238, 242, 242);
    public static final Color BORDER_LAND = new Color(190, 173, 115);

    public static final Color BOX_WHITE = new Color(244, 245, 239);
    public static final Color BOX_BLUE = new Color(201, 223, 237);
    public static final Color BOX_BLACK = new Color(204, 194, 192);
    public static final Color BOX_RED = new Color(246, 208, 185);
    public static final Color BOX_GREEN = new Color(205, 221, 213);
    public static final Color BOX_GOLD = new Color(223, 195, 136);
    public static final Color BOX_COLORLESS = new Color(220, 228, 232);
    public static final Color BOX_LAND = new Color(220, 215, 213);
    public static final Color BOX_INVENTION = new Color(209, 97, 33);
    public static final Color BOX_VEHICLE = new Color(155, 105, 60);

    public static final Color BOX_WHITE_NIGHT = new Color(169, 160, 145);
    public static final Color BOX_BLUE_NIGHT = new Color(46, 133, 176);
    public static final Color BOX_BLACK_NIGHT = new Color(95, 90, 89);
    public static final Color BOX_RED_NIGHT = new Color(188, 87, 57);
    public static final Color BOX_GREEN_NIGHT = new Color(31, 100, 44);
    public static final Color BOX_GOLD_NIGHT = new Color(171, 134, 70);
    public static final Color BOX_COLORLESS_NIGHT = new Color(118, 147, 158);

    public static final Color LAND_TEXTBOX_WHITE = new Color(248, 232, 188, 244);
    public static final Color LAND_TEXTBOX_BLUE = new Color(189, 212, 236, 244);
    public static final Color LAND_TEXTBOX_BLACK = new Color(174, 164, 162, 244);
    public static final Color LAND_TEXTBOX_RED = new Color(242, 168, 133, 244);
    public static final Color LAND_TEXTBOX_GREEN = new Color(198, 220, 198, 244);
    public static final Color LAND_TEXTBOX_GOLD = new Color(236, 229, 207, 244);

    public static final Color TEXTBOX_WHITE = new Color(252, 249, 244, 244);
    public static final Color TEXTBOX_BLUE = new Color(229, 238, 247, 244);
    public static final Color TEXTBOX_BLACK = new Color(241, 241, 240, 244);
    public static final Color TEXTBOX_RED = new Color(243, 224, 217, 244);
    public static final Color TEXTBOX_GREEN = new Color(217, 232, 223, 244);
    public static final Color TEXTBOX_GOLD = new Color(240, 234, 209, 244);
    public static final Color TEXTBOX_COLORLESS = new Color(219, 229, 233, 244);
    public static final Color TEXTBOX_LAND = new Color(218, 214, 212, 244);

    public static final Color ERROR_COLOR = new Color(255, 0, 255);

    ///////////////////////////////////////////////////////////////////////////
    // Layout metrics for modern border cards
    // How far the main box, art, and name / type line are inset from the
    // card border. That is, the width of background texture that shows around
    // the edge of the card.
    protected int contentInset;

    // Helper: The total inset from card edge to rules box etc.
    // = borderWidth + contentInset
    protected int totalContentInset;

    // Width of the content region of the card
    // = cardWidth - 2 x totalContentInset
    protected int contentWidth;

    // How tall the name / type lines and P/T box are
    protected static final float BOX_HEIGHT_FRAC = 0.065f; // x cardHeight
    protected static final int BOX_HEIGHT_MIN = 16;
    protected int boxHeight;

    // How far down the card is the type line placed?
    protected static final float TYPE_LINE_Y_FRAC = 0.57f; // x cardHeight
    protected static final float TYPE_LINE_Y_FRAC_TOKEN = 0.70f;
    protected static final float TYPE_LINE_Y_FRAC_FULL_ART = 0.74f;
    protected int typeLineY;

    // Possible sizes of rules text font
    protected static final int[] RULES_TEXT_FONT_SIZES = {24, 18, 15, 12, 9};

    // How large is the box text, and how far is it down the boxes
    protected int boxTextHeight;
    protected int boxTextOffset;
    protected Font boxTextFont;
    protected Font boxTextFontNarrow;

    // How large is the P/T text, and how far is it down the boxes
    protected int ptTextHeight;
    protected int ptTextOffset;
    protected Font ptTextFont;

    // Processed mana cost string
    protected final String manaCostString;

    public ModernCardRenderer(CardView card, boolean isTransformed) {
        // Pass off to parent
        super(card, isTransformed);

        // Mana cost string
        manaCostString = ManaSymbols.getStringManaCost(cardView.getManaCost());
    }

    @Override
    protected void layout(int cardWidth, int cardHeight) {
        // Pass to parent
        super.layout(cardWidth, cardHeight);

        // Content inset, just equal to border width
        contentInset = borderWidth;

        // Total content inset helper
        totalContentInset = borderWidth + contentInset;

        // Content width
        contentWidth = cardWidth - 2 * totalContentInset;

        // Box height
        boxHeight = (int) Math.max(
                BOX_HEIGHT_MIN,
                BOX_HEIGHT_FRAC * cardHeight);

        // Type line at
        typeLineY = (int) (getTypeLineYFrac() * cardHeight);

        // Box text height
        boxTextHeight = getTextHeightForBoxHeight(boxHeight);
        boxTextOffset = (boxHeight - boxTextHeight) / 2;
        // Not using Beleren for now because it looks bad at small font sizes. Maybe we want to in the future?
        //boxTextFont = BASE_BELEREN_FONT.deriveFont(Font.PLAIN, boxTextHeight);
        boxTextFont = new Font("Arial", Font.PLAIN, boxTextHeight);
        boxTextFontNarrow = new Font("Arial Narrow", Font.PLAIN, boxTextHeight);

        // Box text height
        ptTextHeight = getPTTextHeightForLineHeight(boxHeight);
        ptTextOffset = (boxHeight - ptTextHeight) / 2;
        // Beleren font does work well for numbers though
        ptTextFont = BASE_BELEREN_FONT.deriveFont(Font.PLAIN, ptTextHeight);
    }

    @Override
    protected void drawBorder(Graphics2D g) {
        // Selection Borders
        Color borderColor;
        if (isSelected) {
            borderColor = Color.green;
        } else if (isChoosable) {
            borderColor = new Color(250, 250, 0, 230);
        } else if (cardView.isPlayable()) {
            borderColor = new Color(153, 102, 204, 200);
        } else if (cardView.isCanAttack()) {
            borderColor = new Color(0, 0, 255, 230);
        } else {
            borderColor = Color.BLACK;
        }

        // Draw border as one rounded rectangle
        g.setColor(borderColor);
        g.fillRoundRect(0, 0, cardWidth, cardHeight, cornerRadius, cornerRadius);

        /* // Separate selection highlight border from card itself. Not used right now
        if (borderColor != null) {
            float hwidth = borderWidth / 2.0f;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));
            RoundRectangle2D.Float rect
                    = new RoundRectangle2D.Float(
                            hwidth, hwidth,
                            cardWidth - borderWidth, cardHeight - borderWidth,
                            cornerRadius, cornerRadius);
            g2.draw(rect);
            g2.dispose();
        }
         */
    }

    @Override
    protected void drawBackground(Graphics2D g) {
        // Draw background, in 3 parts

        if (cardView.isFaceDown()) {
            // Just draw a brown rectangle
            drawCardBack(g);
        } else {
            // Set texture to paint with
            g.setPaint(getBackgroundPaint(cardView.getColor(), cardView.getCardTypes(), cardView.getSubTypes()));

            // Draw main part (most of card)
            g.fillRoundRect(
                    borderWidth, borderWidth,
                    cardWidth - borderWidth * 2, cardHeight - borderWidth * 4 - cornerRadius * 2,
                    cornerRadius - 1, cornerRadius - 1);

            // Draw the M15 rounded "swoosh" at the bottom
            g.fillRoundRect(
                    borderWidth, cardHeight - borderWidth * 4 - cornerRadius * 4,
                    cardWidth - borderWidth * 2, cornerRadius * 4,
                    cornerRadius * 2, cornerRadius * 2);

            // Draw the cutout into the "swoosh" for the textbox to lie over
            g.fillRect(
                    borderWidth + contentInset, cardHeight - borderWidth * 5,
                    cardWidth - borderWidth * 2 - contentInset * 2, borderWidth * 2);
        }
    }

    /**
     * Get the region to slice out of a source art image for the card
     *
     * @return
     */
    private Rectangle2D getArtRect() {
        Rectangle2D rect;
        if (useInventionFrame()) {
            rect = new Rectangle2D.Float(0, 0, 1, 1);
        } else if (cardView.getFrameStyle().isFullArt() || (cardView.isToken())) {
            rect = new Rectangle2D.Float(.079f, .11f, .84f, .63f);
        } else {
            rect = new Rectangle2D.Float(.079f, .11f, .84f, .42f);
        }
        return rect;
    }

    private float getTypeLineYFrac() {
        if (cardView.isToken() && cardView.getCardNumber() == null) {
            return TYPE_LINE_Y_FRAC_TOKEN;
        } else if (cardView.getFrameStyle().isFullArt()) {
            return TYPE_LINE_Y_FRAC_FULL_ART;
        } else {
            return TYPE_LINE_Y_FRAC;
        }
    }

    protected boolean isSourceArtFullArt() {
        int color = artImage.getRGB(0, artImage.getHeight() / 2);
        return (((color & 0x00FF0000) > 0x00200000)
                || ((color & 0x0000FF00) > 0x00002000)
                || ((color & 0x000000FF) > 0x00000020));
    }

    private boolean useInventionFrame() {
        if (cardView.getFrameStyle() != FrameStyle.KLD_INVENTION) {
            return false;
        } else if (artImage == null) {
            return true;
        } else {
            return isSourceArtFullArt();
        }
    }

    @Override
    protected void drawArt(Graphics2D g) {
        if (artImage != null && !cardView.isFaceDown()) {
            Rectangle2D artRect = getArtRect();

            // Perform a process to make sure that the art is scaled uniformly to fill the frame, cutting
            // off the minimum amount necessary to make it completely fill the frame without "squashing" it.
            double fullCardImgWidth = artImage.getWidth();
            double fullCardImgHeight = artImage.getHeight();
            double artWidth = artRect.getWidth() * fullCardImgWidth;
            double artHeight = artRect.getHeight() * fullCardImgHeight;
            double targetWidth = contentWidth - 2;
            double targetHeight = typeLineY - totalContentInset - boxHeight;
            double targetAspect = targetWidth / targetHeight;
            if (useInventionFrame()) {
                // No adjustment to art
            } else if (targetAspect * artHeight < artWidth) {
                // Trim off some width
                artWidth = targetAspect * artHeight;
            } else {
                // Trim off some height
                artHeight = artWidth / targetAspect;
            }
            try {
                BufferedImage subImg
                        = artImage.getSubimage(
                                (int) (artRect.getX() * fullCardImgWidth), (int) (artRect.getY() * fullCardImgHeight),
                                (int) artWidth, (int) artHeight);
                if (useInventionFrame()) {
                    g.drawImage(subImg,
                            borderWidth, borderWidth,
                            cardWidth - 2 * borderWidth, cardHeight - 2 * borderWidth,
                            null);
                } else {
                    g.drawImage(subImg,
                            totalContentInset + 1, totalContentInset + boxHeight,
                            (int) targetWidth, (int) targetHeight,
                            null);
                }
            } catch (RasterFormatException e) {
                // At very small card sizes we may encounter a problem with rounding error making the rect not fit
            }
        }
    }

    @Override
    protected void drawFrame(Graphics2D g) {
        // Get the card colors to base the frame on
        ObjectColor frameColors = getFrameObjectColor();

        // Get the border paint
        Color boxColor = getBoxColor(frameColors, cardView.getCardTypes(), isTransformed);
        Paint textboxPaint = getTextboxPaint(frameColors, cardView.getCardTypes(), cardWidth);
        Paint borderPaint = getBorderPaint(frameColors, cardView.getCardTypes(), cardWidth);

        // Special colors
        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            boxColor = BOX_INVENTION;
        }

        // Draw the main card content border
        g.setPaint(borderPaint);
        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            g.drawImage(FRAME_INVENTION, 0, 0, cardWidth, cardHeight, null);
            g.drawRect(
                    totalContentInset, typeLineY,
                    contentWidth - 1, cardHeight - borderWidth * 3 - typeLineY - 1);
        } else {
            g.drawRect(
                    totalContentInset, totalContentInset,
                    contentWidth - 1, cardHeight - borderWidth * 3 - totalContentInset - 1);
        }

        // Draw the textbox fill
        if (useInventionFrame()) {
            g.setPaint(new Color(255, 255, 255, 150));
        } else {
            g.setPaint(textboxPaint);
        }
        g.fillRect(
                totalContentInset + 1, typeLineY,
                contentWidth - 2, cardHeight - borderWidth * 3 - typeLineY - 1);

        // If it's a planeswalker, extend the textbox left border by some
        if (cardView.getCardTypes().contains(CardType.PLANESWALKER)) {
            g.setPaint(borderPaint);
            g.fillRect(
                    totalContentInset, typeLineY + boxHeight,
                    cardWidth / 16, cardHeight - typeLineY - boxHeight - borderWidth * 3);
        }

        if (cardView.getFrameStyle() != FrameStyle.KLD_INVENTION) {
            // Draw a shadow highlight at the right edge of the content frame
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(
                    totalContentInset - 1, totalContentInset,
                    1, cardHeight - borderWidth * 3 - totalContentInset - 1);

            // Draw a shadow highlight separating the card art and rest of frame
            g.drawRect(
                    totalContentInset + 1, totalContentInset + boxHeight,
                    contentWidth - 3, typeLineY - totalContentInset - boxHeight - 1);
        }

        // Draw the name line box
        CardRendererUtils.drawRoundedBox(g,
                borderWidth, totalContentInset,
                cardWidth - 2 * borderWidth, boxHeight,
                contentInset,
                borderPaint, boxColor);

        // Draw the type line box
        CardRendererUtils.drawRoundedBox(g,
                borderWidth, typeLineY,
                cardWidth - 2 * borderWidth, boxHeight,
                contentInset,
                borderPaint, boxColor);

        // Draw a small separator between the type line and box, and shadow
        // at the left of the texbox, and above the name line
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(
                totalContentInset - 1, totalContentInset - 1,
                contentWidth + 1, 1);
        g.fillRect(
                totalContentInset + 1, typeLineY + boxHeight,
                contentWidth - 2, 1);
        g.fillRect(
                cardWidth - totalContentInset - 1, typeLineY + boxHeight,
                1, cardHeight - borderWidth * 3 - typeLineY - boxHeight);

        // Draw the transform circle
        int nameOffset = drawTransformationCircle(g, borderPaint);

        // Draw the name line
        drawNameLine(g,
                totalContentInset + nameOffset, totalContentInset,
                contentWidth - nameOffset, boxHeight);

        // Draw the type line
        drawTypeLine(g,
                totalContentInset, typeLineY,
                contentWidth, boxHeight);

        // Draw the textbox rules
        drawRulesText(g,
                totalContentInset + 2, typeLineY + boxHeight + 2,
                contentWidth - 4, cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3);

        // Draw the bottom right stuff
        drawBottomRight(g, borderPaint, boxColor);
    }

    // Draw the name line
    protected void drawNameLine(Graphics2D g, int x, int y, int w, int h) {
        // Width of the mana symbols
        int manaCostWidth;
        if (cardView.isAbility()) {
            manaCostWidth = 0;
        } else {
            manaCostWidth = CardRendererUtils.getManaCostWidth(manaCostString, boxTextHeight);
        }

        // Available width for name. Add a little bit of slop so that one character
        // can partially go underneath the mana cost
        int availableWidth = w - manaCostWidth + 2;

        // Draw the name
        String nameStr;
        if (cardView.isFaceDown()) {
            if (cardView instanceof PermanentView && ((PermanentView) cardView).isManifested()) {
                nameStr = "Manifest: " + cardView.getName();
            } else {
                nameStr = "Morph: " + cardView.getName();
            }
        } else {
            nameStr = cardView.getName();
        }
        if (!nameStr.isEmpty()) {
            AttributedString str = new AttributedString(nameStr);
            str.addAttribute(TextAttribute.FONT, boxTextFont);
            TextMeasurer measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
            int breakIndex = measure.getLineBreakIndex(0, availableWidth);
            if (breakIndex < nameStr.length()) {
                str = new AttributedString(nameStr);
                str.addAttribute(TextAttribute.FONT, boxTextFontNarrow);
                measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
                breakIndex = measure.getLineBreakIndex(0, availableWidth);
            }
            if (breakIndex > 0) {
                TextLayout layout = measure.getLayout(0, breakIndex);
                g.setColor(getBoxTextColor());
                layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);
            }
        }

        // Draw the mana symbols
        if (!cardView.isAbility() && !cardView.isFaceDown()) {
            ManaSymbols.draw(g, manaCostString, x + w - manaCostWidth, y + boxTextOffset, boxTextHeight);
        }
    }

    // Draw the type line (color indicator, types, and expansion symbol)
    protected void drawTypeLine(Graphics2D g, int x, int y, int w, int h) {
        // Draw expansion symbol
        int expansionSymbolWidth;
        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_SET_SYMBOL, "false").equals("false")) {
            if (cardView.isAbility()) {
                expansionSymbolWidth = 0;
            } else {
                expansionSymbolWidth = drawExpansionSymbol(g, x, y, w, h);
            }
        } else {
            expansionSymbolWidth = 0;
        }

        // Draw type line text
        int availableWidth = w - expansionSymbolWidth + 1;
        String types = getCardTypeLine();
        g.setFont(boxTextFont);

        // Replace "Legendary" in type line if there's not enough space
        if (g.getFontMetrics().stringWidth(types) > availableWidth) {
            types = types.replace("Legendary", "L.");
        }

        if (!types.isEmpty()) {
            AttributedString str = new AttributedString(types);
            str.addAttribute(TextAttribute.FONT, boxTextFont);
            TextMeasurer measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
            int breakIndex = measure.getLineBreakIndex(0, availableWidth);
            if (breakIndex < types.length()) {
                str = new AttributedString(types);
                str.addAttribute(TextAttribute.FONT, boxTextFontNarrow);
                measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
                breakIndex = measure.getLineBreakIndex(0, availableWidth);
            }
            if (breakIndex > 0) {
                TextLayout layout = measure.getLayout(0, breakIndex);
                g.setColor(getBoxTextColor());
                layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);
            }
        }
    }

    // Draw the P/T and/or Loyalty boxes
    protected void drawBottomRight(Graphics2D g, Paint borderPaint, Color fill) {
        // No bottom right for abilities
        if (cardView.isAbility()) {
            return;
        }

        // Where to start drawing the things
        int curY = cardHeight - (int) (0.03f * cardHeight);

        // Width of the boxes
        int partWidth = (int) Math.max(30, 0.20f * cardWidth);

        // Is it a creature?
        boolean isVehicle = cardView.getSubTypes().contains("Vehicle");
        if (cardView.getCardTypes().contains(CardType.CREATURE) || isVehicle) {
            int x = cardWidth - borderWidth - partWidth;

            // Draw PT box
            CardRendererUtils.drawRoundedBox(g,
                    x, curY - boxHeight,
                    partWidth, boxHeight,
                    contentInset,
                    borderPaint,
                    isVehicle ? BOX_VEHICLE : fill);

            // Draw shadow line top
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(
                    x + contentInset, curY - boxHeight - 1,
                    partWidth - 2 * contentInset, 1);

            // Draw text
            Color textColor;
            if (isVehicle) {
                boolean isAnimated = !(cardView instanceof PermanentView) || cardView.getCardTypes().contains(CardType.CREATURE);
                if (isAnimated) {
                    textColor = Color.white;
                } else {
                    textColor = new Color(180, 180, 180);
                }

            } else {
                textColor = getBoxTextColor();
            }
            g.setColor(textColor);
            g.setFont(ptTextFont);
            String ptText = cardView.getPower() + '/' + cardView.getToughness();
            int ptTextWidth = g.getFontMetrics().stringWidth(ptText);
            g.drawString(ptText,
                    x + (partWidth - ptTextWidth) / 2, curY - ptTextOffset - 1);

            // Advance
            curY -= boxHeight;
        }

        // Is it a walker? (But don't draw the box if it's a non-permanent view
        // of a walker without a starting loyalty (EG: Arlin Kord's flipped side).
        if (cardView.getCardTypes().contains(CardType.PLANESWALKER)
                && (cardView instanceof PermanentView || !cardView.getStartingLoyalty().equals("0"))) {
            // Draw the PW loyalty box
            int w = partWidth;
            int h = partWidth / 2;
            int x = cardWidth - partWidth - borderWidth;
            int y = curY - h;

            Polygon symbol = new Polygon(
                    new int[]{
                        x + w / 2,
                        (int) (x + w * 0.9),
                        x + w,
                        (int) (x + w * 0.6),
                        x + w / 2,
                        (int) (x + w * 0.4),
                        x,
                        (int) (x + w * 0.1),},
                    new int[]{
                        y + h,
                        (int) (y + 0.8 * h),
                        y,
                        (int) (y - 0.2 * h),
                        y,
                        (int) (y - 0.2 * h),
                        y,
                        (int) (y + 0.8 * h),},
                    8);

            // Draw + stroke
            g.setColor(Color.black);
            g.fillPolygon(symbol);
            g.setColor(new Color(200, 200, 200));
            g.setStroke(new BasicStroke(2));
            g.drawPolygon(symbol);
            g.setStroke(new BasicStroke(1));

            // Loyalty number
            String loyalty;
            if (cardView instanceof PermanentView) {
                loyalty = cardView.getLoyalty();
            } else {
                loyalty = cardView.getStartingLoyalty();
            }

            g.setFont(ptTextFont);
            g.setColor(Color.white);
            int loyaltyWidth = g.getFontMetrics().stringWidth(loyalty);
            g.drawString(loyalty, x + (w - loyaltyWidth) / 2, y + ptTextHeight + (h - ptTextHeight) / 2);

            // Advance
            curY -= (int) (1.2 * y);
        }

        // does it have damage on it?
        if ((cardView instanceof PermanentView) && ((PermanentView) cardView).getDamage() > 0) {
            int x = cardWidth - partWidth - borderWidth;
            int y = curY - boxHeight;
            String damage = String.valueOf(((PermanentView) cardView).getDamage());
            g.setFont(ptTextFont);
            int txWidth = g.getFontMetrics().stringWidth(damage);
            g.setColor(Color.red);
            g.fillRect(x, y, partWidth, boxHeight);
            g.setColor(Color.white);
            g.drawRect(x, y, partWidth, boxHeight);
            g.drawString(damage, x + (partWidth - txWidth) / 2, curY - 1);
        }
    }

    // Draw the card's textbox in a given rect
    protected boolean loyaltyAbilityColorToggle = false;

    private static class RuleLayout {

        public List<AttributedString> attributedRules;
        public int remainingHeight;
        public boolean fits;
        public Font font;
        public Font fontItalic;
    }

    /**
     * Figure out if a given text size will work for laying out the rules in a
     * card textbox
     */
    protected RuleLayout layoutRules(Graphics2D g, List<TextboxRule> rules, int w, int h, int fontSize) {
        // The fonts to try
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        Font fontItalic = new Font("Arial", Font.ITALIC, fontSize);

        // Get the total height of the rules
        List<AttributedString> attributedRules = new ArrayList<>();
        boolean fits = true;
        int remaining = h;
        for (TextboxRule rule : rules) {
            AttributedString attributed = rule.generateAttributedString(font, fontItalic);
            attributedRules.add(attributed);
            remaining -= drawSingleRule(g, attributed, rule, 0, 0, w, remaining, /*doDraw=*/ false);
            if (remaining < 0) {
                fits = false;
                break;
            }
        }

        // Return the information
        RuleLayout layout = new RuleLayout();
        layout.attributedRules = attributedRules;
        layout.remainingHeight = remaining;
        layout.fits = fits;
        layout.font = font;
        layout.fontItalic = fontItalic;
        return layout;
    }

    protected void drawRulesText(Graphics2D g, int x, int y, int w, int h) {
        // Gather all rules to render
        List<TextboxRule> allRules = new ArrayList<>(textboxRules);

        // Add the keyword rule if there are any keywords
        if (!textboxKeywords.isEmpty()) {
            String keywordRulesString = getKeywordRulesString();
            TextboxRule keywordsRule = new TextboxRule(keywordRulesString, new ArrayList<TextboxRule.AttributeRegion>());
            allRules.add(0, keywordsRule);
        }

        // Basic mana draw mana symbol in textbox (for basic lands)
        if (allRules.size() == 1 && (allRules.get(0) instanceof TextboxBasicManaRule) && cardView.getCardTypes().contains(CardType.LAND)) {
            drawBasicManaTextbox(g, x, y, w, h, ((TextboxBasicManaRule) allRules.get(0)).getBasicManaSymbol());
            return;
        }

        // Go through possible font sizes in descending order to find the best fit
        RuleLayout bestLayout = null;
        for (int fontSize : RULES_TEXT_FONT_SIZES) {
            bestLayout = layoutRules(g, allRules, w, h, fontSize);

            // Stop, we found a good fit
            if (bestLayout.fits) {
                break;
            }
        }

        // Nothing to draw
        if (bestLayout == null) {
            return;
        }

        // Do we have room for additional padding between the parts of text?
        // If so, calculate the padding based on how much space was left over
        int padding;
        if (bestLayout.fits) {
            padding = (int) (((float) bestLayout.remainingHeight) / (1 + allRules.size()));
        } else {
            // When the text doesn't fit to begin with there's no room for padding
            padding = 0;
        }

        // Do the actual draw
        loyaltyAbilityColorToggle = false;
        g.setColor(Color.black);
        int curY = y + padding;
        for (int i = 0; i < bestLayout.attributedRules.size(); ++i) {
            AttributedString attributedRule = bestLayout.attributedRules.get(i);
            TextboxRule rule = allRules.get(i);
            int adv = drawSingleRule(g, attributedRule, rule, x, curY, w, h, true);
            curY += adv + padding;
            h -= adv;
            if (h < 0) {
                break;
            }
        }
    }

    // Draw a basic mana symbol
    private void drawBasicManaTextbox(Graphics2D g, int x, int y, int w, int h, String symbol) {
        String symbs = symbol;
        int symbHeight = (int) (0.8 * h);
        int manaCostWidth = CardRendererUtils.getManaCostWidth(symbs, symbHeight);
        ManaSymbols.draw(g, symbs, x + (w - manaCostWidth) / 2, y + (h - symbHeight) / 2, symbHeight);
    }

    // Get the first line of the textbox, the keyword string
    private String getKeywordRulesString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < textboxKeywords.size(); ++i) {
            builder.append(textboxKeywords.get(i).text);
            if (i != textboxKeywords.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    // Draw a single rule and returns the amount vertically advanced by, but
    // only if doDraw is true. If doDraw is false, just returns the vertical
    // advance if the rule were to be drawn.
    private int drawSingleRule(Graphics2D g, AttributedString text, TextboxRule rule, int x, int y, int w, int h, boolean doDraw) {
        // Inset, in case we are a leveler or loyalty ability
        int inset = 0;
        if (rule != null && rule.type == TextboxRuleType.LOYALTY) {
            inset = cardWidth / 12;
        }
        int availWidth = w - inset;

        FontRenderContext frc = g.getFontRenderContext();
        AttributedCharacterIterator textIter = text.getIterator();
        LineBreakMeasurer measure = new LineBreakMeasurer(textIter, frc);
        float yPos = y;
        float remain = h;
        AttributedCharacterIterator newLineCheck = text.getIterator();
        while (measure.getPosition() < textIter.getEndIndex()) {
            // Advance iterator to next line break
            newLineCheck.setIndex(measure.getPosition());
            char ch;
            while ((ch = newLineCheck.next()) != CharacterIterator.DONE) {
                if (ch == '\n') {
                    break;
                }
            }

            // Get the text layout
            TextLayout layout = measure.nextLayout(availWidth, newLineCheck.getIndex(), false);
            float ascent = layout.getAscent();
            yPos += ascent;
            remain -= ascent;
            if (remain < 0) {
                break;
            }
            if (doDraw) {
                g.setColor(Color.black);
                layout.draw(g, x + inset, yPos);
            }
            yPos += layout.getDescent() + layout.getLeading() - 2;
        }

        // Advance
        int advance = ((int) Math.ceil(yPos)) - y;

        // Is it a loyalty ability?
        if (rule != null && rule.type == TextboxRuleType.LOYALTY) {
            TextboxLoyaltyRule loyaltyRule = (TextboxLoyaltyRule) rule;
            Polygon symbol;
            int symbolWidth = (x + inset) - borderWidth - 4;
            int symbolHeight = (int) (0.7f * symbolWidth);
            if (symbolHeight > advance) {
                advance = symbolHeight;
            }
            int symbolX = x - borderWidth;
            int symbolY = y + (advance - symbolHeight) / 2;
            if (doDraw) {
                if (loyaltyRule.loyaltyChange < 0 || loyaltyRule.loyaltyChange == TextboxLoyaltyRule.MINUS_X) {
                    symbol = new Polygon(
                            new int[]{
                                symbolX,
                                symbolX + symbolWidth,
                                symbolX + symbolWidth,
                                symbolX + symbolWidth / 2,
                                symbolX,},
                            new int[]{
                                symbolY,
                                symbolY,
                                symbolY + symbolHeight - 3,
                                symbolY + symbolHeight + 3,
                                symbolY + symbolHeight - 3,},
                            5);
                } else if (loyaltyRule.loyaltyChange > 0) {
                    symbol = new Polygon(
                            new int[]{
                                symbolX,
                                symbolX + symbolWidth / 2,
                                symbolX + symbolWidth,
                                symbolX + symbolWidth,
                                symbolX,},
                            new int[]{
                                symbolY + 3,
                                symbolY - 3,
                                symbolY + 3,
                                symbolY + symbolHeight,
                                symbolY + symbolHeight,},
                            5);
                } else {
                    symbol = new Polygon(
                            new int[]{
                                symbolX,
                                symbolX + symbolWidth,
                                symbolX + symbolWidth,
                                symbolX,},
                            new int[]{
                                symbolY,
                                symbolY,
                                symbolY + symbolHeight,
                                symbolY + symbolHeight,},
                            4);
                }
                g.setColor(new Color(0, 0, 0, 128));
                g.fillRect(x + 2, y + advance + 1, w - 2, 1);
                g.setColor(Color.black);
                g.fillPolygon(symbol);
                g.setColor(new Color(200, 200, 200));
                g.setStroke(new BasicStroke(2));
                g.drawPolygon(symbol);
                g.setStroke(new BasicStroke(1));
                g.setColor(Color.white);
                g.setFont(boxTextFont);
                String loyaltyString = loyaltyRule.getChangeString();
                int textWidth = g.getFontMetrics().stringWidth(loyaltyString);
                g.drawString(loyaltyString,
                        symbolX + (symbolWidth - textWidth) / 2,
                        symbolY + symbolHeight - (symbolHeight - boxTextHeight) / 2);

                advance += 3;
                loyaltyAbilityColorToggle = !loyaltyAbilityColorToggle;
            }
        }

        return advance;
    }

    // Draw the transformation circle if there is one, and return the
    // horizontal width taken up into the content space by it.
    protected boolean isNightCard() {
        return isTransformed;
    }

    protected boolean isTransformCard() {
        return cardView.canTransform() || isTransformed;
    }

    protected int drawTransformationCircle(Graphics2D g, Paint borderPaint) {
        int transformCircleOffset = 0;
        if (isTransformCard()) {
            transformCircleOffset = boxHeight - contentInset;
            g.setPaint(borderPaint);
            g.drawOval(borderWidth, totalContentInset, boxHeight - 1, boxHeight - 1);
            g.setColor(Color.black);
            g.fillOval(borderWidth + 1, totalContentInset + 1, boxHeight - 2, boxHeight - 2);
            g.setColor(Color.white);
            if (isTransformed) {
                g.fillArc(borderWidth + 3, totalContentInset + 3, boxHeight - 6, boxHeight - 6, 90, 270);
                g.setColor(Color.black);
                g.fillArc(borderWidth + 3 + 3, totalContentInset + 3, boxHeight - 6 - 3, boxHeight - 6, 90, 270);
            } else {
                g.fillOval(borderWidth + 3, totalContentInset + 3, boxHeight - 6, boxHeight - 6);
            }
        }
        return transformCircleOffset;
    }

    // Get the text height for a given box height
    protected static int getTextHeightForBoxHeight(int h) {
        if (h < 15) {
            return h - 3;
        } else {
            return (int) Math.ceil(.6 * h);
        }
    }

    protected static int getPTTextHeightForLineHeight(int h) {
        return h - 4;
    }

    // Determine the color of the name / type line text
    protected Color getBoxTextColor() {
        if (isTransformed) {
            return Color.white;
        } else if (cardView.isAbility()) {
            return Color.white;
        } else {
            return Color.black;
        }
    }

    // Determine the colors to base the frame on
    protected ObjectColor getFrameObjectColor() {
        // TODO: Take into account devoid, land frame colors, etc
        return cardView.getColor().union(cardView.getFrameColor());
    }

    // Determine which background paint to use from a set of colors
    // and the current card.
    protected static Paint getBackgroundPaint(ObjectColor colors, Collection<CardType> types, Collection<String> subTypes) {
        if (subTypes.contains("Vehicle")) {
            return BG_TEXTURE_VEHICLE;
        } else if (types.contains(CardType.LAND)) {
            return BG_TEXTURE_LAND;
        } else if (types.contains(CardType.ARTIFACT)) {
            return BG_TEXTURE_ARTIFACT;
        } else if (colors.isMulticolored()) {
            return BG_TEXTURE_GOLD;
        } else if (colors.isWhite()) {
            return BG_TEXTURE_WHITE;
        } else if (colors.isBlue()) {
            return BG_TEXTURE_BLUE;
        } else if (colors.isBlack()) {
            return BG_TEXTURE_BLACK;
        } else if (colors.isRed()) {
            return BG_TEXTURE_RED;
        } else if (colors.isGreen()) {
            return BG_TEXTURE_GREEN;
        } else {
            // Colorless
            return new Color(71, 86, 101);
        }
    }

    // Get the box color for the given colors
    protected Color getBoxColor(ObjectColor colors, Collection<CardType> types, boolean isNightCard) {
        if (cardView.isAbility()) {
            return Color.BLACK;
        } else if (colors.getColorCount() == 2 && types.contains(CardType.LAND)) {
            // Special case for two color lands. Boxes should be normal land colored
            // rather than multicolor. Three or greater color lands use a multi-color
            // box as normal.
            return BOX_LAND;
        } else if (colors.isMulticolored()) {
            return isNightCard ? BOX_GOLD_NIGHT : BOX_GOLD;
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return BOX_LAND;
            } else {
                return isNightCard ? BOX_COLORLESS_NIGHT : BOX_COLORLESS;
            }
        } else if (colors.isWhite()) {
            return isNightCard ? BOX_WHITE_NIGHT : BOX_WHITE;
        } else if (colors.isBlue()) {
            return isNightCard ? BOX_BLUE_NIGHT : BOX_BLUE;
        } else if (colors.isBlack()) {
            return isNightCard ? BOX_BLACK_NIGHT : BOX_BLACK;
        } else if (colors.isRed()) {
            return isNightCard ? BOX_RED_NIGHT : BOX_RED;
        } else if (colors.isGreen()) {
            return isNightCard ? BOX_GREEN_NIGHT : BOX_GREEN;
        } else {
            return ERROR_COLOR;
        }
    }

    // Get the border color for a single color
    protected static Color getBorderColor(ObjectColor color) {
        if (color.isWhite()) {
            return BORDER_WHITE;
        } else if (color.isBlue()) {
            return BORDER_BLUE;
        } else if (color.isBlack()) {
            return BORDER_BLACK;
        } else if (color.isRed()) {
            return BORDER_RED;
        } else if (color.isGreen()) {
            return BORDER_GREEN;
        } else {
            return ERROR_COLOR;
        }
    }

    // Determine the border paint to use, based on an ObjectColors
    protected static Paint getBorderPaint(ObjectColor colors, Collection<CardType> types, int width) {
        if (colors.isMulticolored()) {
            if (colors.getColorCount() == 2) {
                List<ObjectColor> twoColors = colors.getColors();

                // Two-color frames look better if we use a whiter white
                // than the normal white frame color for them, as the normal
                // white border color is very close to the gold background
                // color.
                Color color1, color2;
                if (twoColors.get(0).isWhite()) {
                    color1 = new Color(240, 240, 240);
                } else {
                    color1 = getBorderColor(twoColors.get(0));
                }
                if (twoColors.get(1).isWhite()) {
                    color2 = new Color(240, 240, 240);
                } else {
                    color2 = getBorderColor(twoColors.get(1));
                }

                // Special case for two colors, gradient paint
                return new LinearGradientPaint(
                        0, 0, width, 0,
                        new float[]{0.4f, 0.6f},
                        new Color[]{color1, color2});
            } else {
                return BORDER_GOLD;
            }
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return BORDER_LAND;
            } else {
                return BORDER_COLORLESS;
            }
        } else {
            return getBorderColor(colors);
        }
    }

    // Determine the textbox color for a single color
    protected static Color getTextboxColor(ObjectColor color) {
        if (color.isWhite()) {
            return TEXTBOX_WHITE;
        } else if (color.isBlue()) {
            return TEXTBOX_BLUE;
        } else if (color.isBlack()) {
            return TEXTBOX_BLACK;
        } else if (color.isRed()) {
            return TEXTBOX_RED;
        } else if (color.isGreen()) {
            return TEXTBOX_GREEN;
        } else {
            return ERROR_COLOR;
        }
    }

    // Determine the land textbox color for a single color. Uses the same colors as the
    // type / name line.
    protected static Color getLandTextboxColor(ObjectColor color) {
        if (color.isWhite()) {
            return LAND_TEXTBOX_WHITE;
        } else if (color.isBlue()) {
            return LAND_TEXTBOX_BLUE;
        } else if (color.isBlack()) {
            return LAND_TEXTBOX_BLACK;
        } else if (color.isRed()) {
            return LAND_TEXTBOX_RED;
        } else if (color.isGreen()) {
            return LAND_TEXTBOX_GREEN;
        } else {
            return ERROR_COLOR;
        }
    }

    // Determine the border paint to use, based on an ObjectColors
    protected static Paint getTextboxPaint(ObjectColor colors, Collection<CardType> types, int width) {
        if (colors.isMulticolored()) {
            if (colors.getColorCount() == 2) {
                List<ObjectColor> twoColors = colors.getColors();
                Color[] translatedColors;
                if (types.contains(CardType.LAND)) {
                    translatedColors = new Color[]{
                        getLandTextboxColor(twoColors.get(0)),
                        getLandTextboxColor(twoColors.get(1))
                    };
                } else {
                    translatedColors = new Color[]{
                        getTextboxColor(twoColors.get(0)),
                        getTextboxColor(twoColors.get(1))
                    };
                }

                // Special case for two colors, gradient paint
                return new LinearGradientPaint(
                        0, 0, width, 0,
                        new float[]{0.4f, 0.6f},
                        translatedColors);
            } else if (types.contains(CardType.LAND)) {
                return LAND_TEXTBOX_GOLD;
            } else {
                return TEXTBOX_GOLD;
            }
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return TEXTBOX_LAND;
            } else {
                return TEXTBOX_COLORLESS;
            }
        } else if (types.contains(CardType.LAND)) {
            return getLandTextboxColor(colors);
        } else {
            return getTextboxColor(colors);
        }
    }
}
