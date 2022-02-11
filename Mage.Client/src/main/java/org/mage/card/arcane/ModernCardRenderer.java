package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.ArtRect;
import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.constants.SubType;
import mage.util.SubTypes;
import mage.view.CardView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mage.card.arcane.ManaSymbols.getSizedManaSymbol;


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
 * @author stravant@gmail.com, JayDi85
 * <p>
 * Base rendering class for new border cards
 */
public class ModernCardRenderer extends CardRenderer {

    private static final Logger LOGGER = Logger.getLogger(ModernCardRenderer.class);
    private static final GlowText glowTextRenderer = new GlowText();
    public static final Color MANA_ICONS_TEXT_COLOR = Color.DARK_GRAY; // text color of missing mana icons in IMAGE render mode

    ///////////////////////////////////////////////////////////////////////////
    // Textures for modern frame cards
    private static TexturePaint loadBackgroundTexture(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
    }

    private static BufferedImage loadBackgroundImage(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return img;
    }

    private static BufferedImage loadFramePart(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        return CardRendererUtils.toBufferedImage(icon.getImage());
    }

    private static Font loadFont(String name) {
        try (InputStream in = ModernCardRenderer.class.getResourceAsStream("/cardrender/" + name + ".ttf")) {
            return Font.createFont(
                    Font.TRUETYPE_FONT, in);
        } catch (IOException e) {
            LOGGER.info("Failed to load font `" + name + "`, couldn't find resource.");
        } catch (FontFormatException e) {
            LOGGER.info("Failed to load font `" + name + "`, bad format.");
        }
        return new Font("Arial", Font.PLAIN, 1);
    }

    // public static final Font BASE_BELEREN_FONT = loadFont("beleren-bold");

    public static final Paint BG_TEXTURE_WHITE = loadBackgroundTexture("white");
    public static final Paint BG_TEXTURE_BLUE = loadBackgroundTexture("blue");
    public static final Paint BG_TEXTURE_BLACK = loadBackgroundTexture("black");
    public static final Paint BG_TEXTURE_RED = loadBackgroundTexture("red");
    public static final Paint BG_TEXTURE_GREEN = loadBackgroundTexture("green");
    public static final Paint BG_TEXTURE_GOLD = loadBackgroundTexture("gold");
    public static final Paint BG_TEXTURE_ARTIFACT = loadBackgroundTexture("artifact");
    public static final Paint BG_TEXTURE_LAND = loadBackgroundTexture("land");
    public static final Paint BG_TEXTURE_VEHICLE = loadBackgroundTexture("vehicle");

    public static final BufferedImage BG_IMG_WHITE = loadBackgroundImage("white");
    public static final BufferedImage BG_IMG_BLUE = loadBackgroundImage("blue");
    public static final BufferedImage BG_IMG_BLACK = loadBackgroundImage("black");
    public static final BufferedImage BG_IMG_RED = loadBackgroundImage("red");
    public static final BufferedImage BG_IMG_GREEN = loadBackgroundImage("green");
    public static final BufferedImage BG_IMG_GOLD = loadBackgroundImage("gold");
    public static final BufferedImage BG_IMG_ARTIFACT = loadBackgroundImage("artifact");
    public static final BufferedImage BG_IMG_LAND = loadBackgroundImage("land");
    public static final BufferedImage BG_IMG_VEHICLE = loadBackgroundImage("vehicle");
    public static final BufferedImage BG_IMG_COLORLESS = loadBackgroundImage("colorless");
    public static final BufferedImage BG_IMG_EXPEDITION = loadBackgroundImage("expedition");

    public static final BufferedImage FRAME_INVENTION = loadFramePart("invention_frame");

    public static final Color BORDER_WHITE = new Color(216, 203, 188);
    public static final Color BORDER_BLUE = new Color(20, 121, 175);
    public static final Color BORDER_BLACK = new Color(45, 45, 35);
    public static final Color BORDER_RED = new Color(201, 71, 58);
    public static final Color BORDER_GREEN = new Color(4, 136, 69);
    public static final Color BORDER_GOLD = new Color(255, 228, 124);
    public static final Color BORDER_COLORLESS = new Color(208, 212, 212);
    public static final Color BORDER_LAND = new Color(190, 173, 115);

    public static final Color BOX_WHITE = new Color(244, 245, 239);
    public static final Color BOX_BLUE = new Color(201, 223, 237);
    public static final Color BOX_BLACK = new Color(204, 194, 192);
    public static final Color BOX_RED = new Color(246, 208, 185);
    public static final Color BOX_GREEN = new Color(205, 221, 213);
    public static final Color BOX_GOLD = new Color(223, 195, 136);
    public static final Color BOX_COLORLESS = new Color(200, 208, 212);
    public static final Color BOX_LAND = new Color(220, 215, 213);
    public static final Color BOX_INVENTION = new Color(209, 97, 33);
    public static final Color BOX_VEHICLE = new Color(155, 105, 60);

    public static final Color BOX_UST_WHITE = new Color(240, 240, 220);
    public static final Color BOX_UST_BLUE = new Color(10, 100, 180);
    public static final Color BOX_UST_BLACK = new Color(28, 30, 28);
    public static final Color BOX_UST_RED = new Color(229, 74, 32);
    public static final Color BOX_UST_GREEN = new Color(7, 130, 53);

    public static final Color BOX_WHITE_NIGHT = new Color(169, 160, 145);
    public static final Color BOX_BLUE_NIGHT = new Color(46, 133, 176);
    public static final Color BOX_BLACK_NIGHT = new Color(95, 90, 89);
    public static final Color BOX_RED_NIGHT = new Color(188, 87, 57);
    public static final Color BOX_GREEN_NIGHT = new Color(31, 100, 44);
    public static final Color BOX_GOLD_NIGHT = new Color(171, 134, 70);
    public static final Color BOX_COLORLESS_NIGHT = new Color(118, 147, 158);

    public static final Color LAND_TEXTBOX_WHITE = new Color(248, 232, 188, 234);
    public static final Color LAND_TEXTBOX_BLUE = new Color(189, 212, 236, 234);
    public static final Color LAND_TEXTBOX_BLACK = new Color(174, 164, 162, 234);
    public static final Color LAND_TEXTBOX_RED = new Color(242, 168, 133, 234);
    public static final Color LAND_TEXTBOX_GREEN = new Color(198, 220, 198, 234);
    public static final Color LAND_TEXTBOX_GOLD = new Color(236, 229, 207, 234);

    public static final Color LAND_SPIRAL_TEXTBOX_WHITE = new Color(248, 232, 188, 220);
    public static final Color LAND_SPIRAL_TEXTBOX_BLUE = new Color(189, 212, 236, 220);
    public static final Color LAND_SPIRAL_TEXTBOX_BLACK = new Color(174, 164, 162, 220);
    public static final Color LAND_SPIRAL_TEXTBOX_RED = new Color(242, 168, 133, 220);
    public static final Color LAND_SPIRAL_TEXTBOX_GREEN = new Color(198, 220, 198, 220);

    public static final Color TEXTBOX_WHITE = new Color(252, 249, 244, 234);
    public static final Color TEXTBOX_BLUE = new Color(229, 238, 247, 234);
    public static final Color TEXTBOX_BLACK = new Color(241, 241, 240, 234);
    public static final Color TEXTBOX_RED = new Color(243, 224, 217, 234);
    public static final Color TEXTBOX_GREEN = new Color(217, 232, 223, 234);
    public static final Color TEXTBOX_GOLD = new Color(240, 234, 209, 234);
    public static final Color TEXTBOX_COLORLESS = new Color(199, 209, 213, 234);
    public static final Color TEXTBOX_LAND = new Color(218, 214, 212, 234);

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

    public ModernCardRenderer(CardView card) {
        // Pass off to parent
        super(card);

        // Mana cost string
        manaCostString = ManaSymbols.getClearManaCost(cardView.getManaCostStr());
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
        boxTextFont = new Font("Arial", Font.PLAIN, boxTextHeight);
        boxTextFontNarrow = new Font("Arial Narrow", Font.PLAIN, boxTextHeight);

        // Box text height
        ptTextHeight = getPTTextHeightForLineHeight(boxHeight);
        ptTextOffset = (boxHeight - ptTextHeight) / 2;
        ptTextFont = new Font("Arial", Font.BOLD, ptTextHeight);
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
            borderColor = new Color(255, 50, 50, 230);
        } else if (cardView.isCanBlock()) {
            borderColor = new Color(255, 50, 50, 230);
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
            if (cardView.getFrameStyle() == FrameStyle.UST_FULL_ART_BASIC) {
                return;
            }

            boolean isExped = false;
            if (cardView.getExpansionSetCode().equals("EXP")) {
                isExped = true;
            }
            BufferedImage bg = getBackgroundImage(cardView.getColor(), cardView.getCardTypes(), cardView.getSubTypes(), isExped);
            if (bg == null) {
                return;
            }
            int bgw = bg.getWidth();
            int bgh = bg.getHeight();

            // Draw main part (most of card)
            RoundRectangle2D rr = new RoundRectangle2D.Double(borderWidth, borderWidth,
                    cardWidth - borderWidth * 2, cardHeight - borderWidth * 4 - cornerRadius * 2,
                    cornerRadius - 1, cornerRadius - 1);
            Area a = new Area(rr);

            RoundRectangle2D rr2 = new RoundRectangle2D.Double(borderWidth, cardHeight - borderWidth * 4 - cornerRadius * 4,
                    cardWidth - borderWidth * 2, cornerRadius * 4,
                    cornerRadius * 2, cornerRadius * 2);
            a.add(new Area(rr2));

            // Draw the M15 rounded "swoosh" at the bottom
            Rectangle r = new Rectangle(borderWidth + contentInset, cardHeight - borderWidth * 5, cardWidth - borderWidth * 2 - contentInset * 2, borderWidth * 2);
            a.add(new Area(r));
            g.setClip(a);
            g.drawImage(bg, 0, 0, cardWidth, cardHeight, 0, 0, bgw, bgh, BOX_BLUE, null);
            g.setClip(null);
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
        } else if (isZendikarFullArtLand()) {
            rect = new Rectangle2D.Float(.079f, .11f, .84f, .84f);
        } else if (isUnstableFullArtLand()) {
            rect = new Rectangle2D.Float(.0f, .0f, 1.0f, 1.0f);
        } else if (cardView.getFrameStyle().isFullArt() || (cardView.isToken())) {
            rect = new Rectangle2D.Float(.079f, .11f, .84f, .63f);
        } else {
            rect = ArtRect.NORMAL.rect;
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

    private boolean isZendikarFullArtLand() {
        return cardView.getFrameStyle() == FrameStyle.BFZ_FULL_ART_BASIC || cardView.getFrameStyle() == FrameStyle.ZEN_FULL_ART_BASIC;
    }

    private boolean isUnstableFullArtLand() {
        return cardView.getFrameStyle() == FrameStyle.UST_FULL_ART_BASIC;
    }

    private boolean isOriginalDualLand() {
        return cardView.getFrameStyle() == FrameStyle.LEA_ORIGINAL_DUAL_LAND_ART_BASIC;
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
        if ((artImage != null || faceArtImage != null) && !cardView.isFaceDown()) {

            boolean useFaceArt = false;
            if (faceArtImage != null && !isZendikarFullArtLand()) {
                useFaceArt = true;
            }

            // Invention rendering, art fills the entire frame
            if (useInventionFrame()) {
                useFaceArt = false;
                drawArtIntoRect(g,
                        borderWidth, borderWidth,
                        cardWidth - 2 * borderWidth, cardHeight - 2 * borderWidth,
                        getArtRect(), false);
            }

            boolean shouldPreserveAspect = true;
            Rectangle2D sourceRect = getArtRect();

            if (cardView.getMageObjectType() == MageObjectType.SPELL) {
                useFaceArt = false;
                ArtRect rect = cardView.getArtRect();
                if (rect == ArtRect.SPLIT_FUSED) {
                    // Special handling for fused, draw the art from both halves stacked on top of one and other
                    // each filling half of the art rect
                    drawArtIntoRect(g,
                            totalContentInset + 1, totalContentInset + boxHeight,
                            contentWidth - 2, (typeLineY - totalContentInset - boxHeight) / 2,
                            ArtRect.SPLIT_LEFT.rect, useInventionFrame());
                    drawArtIntoRect(g,
                            totalContentInset + 1, totalContentInset + boxHeight + (typeLineY - totalContentInset - boxHeight) / 2,
                            contentWidth - 2, (typeLineY - totalContentInset - boxHeight) / 2,
                            ArtRect.SPLIT_RIGHT.rect, useInventionFrame());
                    return;
                } else if (rect != ArtRect.NORMAL) {
                    sourceRect = rect.rect;
                    shouldPreserveAspect = false;
                }
            }

            // Normal drawing of art from a source part of the card frame into the rect
            if (useFaceArt) {
                int alternate_height = cardHeight - boxHeight * 2 - totalContentInset;
                drawFaceArtIntoRect(g,
                        totalContentInset + 1, totalContentInset + boxHeight,
                        contentWidth - 2, typeLineY - totalContentInset - boxHeight,
                        alternate_height,
                        sourceRect, shouldPreserveAspect);
            } else if (!isZendikarFullArtLand()) {
                drawArtIntoRect(g,
                        totalContentInset + 1, totalContentInset + boxHeight,
                        contentWidth - 2, typeLineY - totalContentInset - boxHeight,
                        sourceRect, shouldPreserveAspect);
            }
        }
    }

    @Override
    protected void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image, boolean lessOpaqueRulesTextBox) {
        // Get the card colors to base the frame on
        ObjectColor frameColors = getFrameObjectColor();

        // Get the border paint
        Color boxColor = getBoxColor(frameColors, cardView.getCardTypes(), attribs.isTransformed);
        Color additionalBoxColor = getAdditionalBoxColor(frameColors, cardView.getCardTypes(), attribs.isTransformed);
        Paint textboxPaint = getTextboxPaint(frameColors, cardView.getCardTypes(), cardWidth, lessOpaqueRulesTextBox);
        Paint borderPaint = getBorderPaint(frameColors, cardView.getCardTypes(), cardWidth);

        // Special colors
        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            boxColor = BOX_INVENTION;
        }

        // Is this a Zendikar or Unstable land
        boolean isZenUst = isZendikarFullArtLand() || isUnstableFullArtLand();
        boolean isOriginalDual = isOriginalDualLand();

        // Draw the main card content border
        g.setPaint(borderPaint);

        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            g.drawImage(FRAME_INVENTION, 3, 3, cardWidth - 6, cardHeight - 6, null);
            g.drawRect(
                    totalContentInset, typeLineY,
                    contentWidth - 1, cardHeight - borderWidth * 3 - typeLineY - 1);
        } else if (!isZenUst) {
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

        if (!isZenUst) {
            if (cardView.getCardTypes().contains(CardType.LAND)) {
                int total_height_of_box = cardHeight - borderWidth * 3 - typeLineY - 2 - boxHeight;

                // Analysis of LEA Duals (Scrubland) gives 16.5 height of unit of 'spirals' in the text area
                int height_of_spiral = (int) Math.round(total_height_of_box / 16.5);
                int total_height_spiral = total_height_of_box;

                List<ObjectColor> twoColors = frameColors.getColors();

                if (twoColors.size() <= 2) {
                    if (isOriginalDual && twoColors.size() == 2) {
                        g.setPaint(getSpiralLandTextboxColor(twoColors.get(0), twoColors.get(1), false));
                    }
                    g.fillRect(totalContentInset + 1, typeLineY + boxHeight + 1, contentWidth - 2, total_height_of_box);
                }
                if (frameColors.getColorCount() >= 3) {
                    g.fillRect(totalContentInset + 1, typeLineY + boxHeight + 1, contentWidth - 2, total_height_of_box);
                }
                if (frameColors.getColorCount() == 2) {
                    if (isOriginalDual) {
                        g.setPaint(getSpiralLandTextboxColor(twoColors.get(0), twoColors.get(1), true));

                        // Horizontal bars
                        g.fillRect(totalContentInset + 1                     , typeLineY + boxHeight + 1                                           , contentWidth - 2                      , height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 2*height_of_spiral, typeLineY + boxHeight + 1 + 2*height_of_spiral                      , contentWidth - 2 - 4*height_of_spiral , height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 4*height_of_spiral, typeLineY + boxHeight + 1 + 4*height_of_spiral                      , contentWidth - 2 - 8*height_of_spiral , height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 6*height_of_spiral, typeLineY + boxHeight + 1 + 6*height_of_spiral                      , contentWidth - 2 - 12*height_of_spiral, height_of_spiral);

                        g.fillRect(totalContentInset + 1 + 6*height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 7*height_of_spiral, contentWidth - 2 - 12*height_of_spiral, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 4*height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 5*height_of_spiral, contentWidth - 2 - 8*height_of_spiral , height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 2*height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 3*height_of_spiral, contentWidth - 2 - 4*height_of_spiral , height_of_spiral);
                        g.fillRect(totalContentInset + 1                     , typeLineY + boxHeight + 1 + total_height_of_box - height_of_spiral  , contentWidth - 2                      , height_of_spiral);

                        // Vertical bars
                        g.fillRect(totalContentInset + 1                     , typeLineY + boxHeight + 1                     , height_of_spiral, total_height_spiral - 1                      );
                        g.fillRect(totalContentInset + 1 + 2*height_of_spiral, typeLineY + boxHeight + 1 + 2*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 4*height_of_spiral );
                        g.fillRect(totalContentInset + 1 + 4*height_of_spiral, typeLineY + boxHeight + 1 + 4*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 8*height_of_spiral );
                        g.fillRect(totalContentInset + 1 + 6*height_of_spiral, typeLineY + boxHeight + 1 + 6*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 12*height_of_spiral);

                        g.fillRect(totalContentInset + contentWidth - 7*height_of_spiral, typeLineY + boxHeight + 1 + 6*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 12*height_of_spiral);
                        g.fillRect(totalContentInset + contentWidth - 5*height_of_spiral, typeLineY + boxHeight + 1 + 4*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 8*height_of_spiral );
                        g.fillRect(totalContentInset + contentWidth - 3*height_of_spiral, typeLineY + boxHeight + 1 + 2*height_of_spiral, height_of_spiral, total_height_spiral - 1 - 4*height_of_spiral );
                        g.fillRect(totalContentInset + contentWidth - 1*height_of_spiral, typeLineY + boxHeight + 1 + 0*height_of_spiral, height_of_spiral, total_height_spiral - 1                      );
                    }
                }
            } else {
                g.fillRect(
                    totalContentInset + 1, typeLineY,
                    contentWidth - 2, cardHeight - borderWidth * 3 - typeLineY - 1);
            }
        }

        // If it's a planeswalker, extend the textbox left border by some
        if (cardView.isPlanesWalker()) {
            g.setPaint(borderPaint);
            g.fillRect(
                    totalContentInset, typeLineY + boxHeight,
                    cardWidth / 16, cardHeight - typeLineY - boxHeight - borderWidth * 3);
        }

        if (cardView.getFrameStyle() != FrameStyle.KLD_INVENTION && !isZenUst) {
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
        if (!isZenUst) {
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
            // Draw the type line
            drawTypeLine(g, attribs, getCardTypeLine(),
                    totalContentInset, typeLineY,
                    contentWidth, boxHeight, true);
        }

        // Draw the transform circle
        int nameOffset = drawTransformationCircle(g, attribs, borderPaint);

        // Draw the name line
        drawNameLine(g, attribs, cardView.getDisplayName(), manaCostString,
                totalContentInset + nameOffset, totalContentInset,
                contentWidth - nameOffset, boxHeight);

        // Draw the textbox rules
        if (isZendikarFullArtLand()) {
            int x = totalContentInset;
            int y = typeLineY + boxHeight + (cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3) / 2 - contentInset;
            int w = contentWidth;
            int h = boxHeight - 4;

            if (cardView.getFrameStyle() == FrameStyle.ZEN_FULL_ART_BASIC) {
                // Draw curved lines (old Zendikar land style) - bigger (around 6%) inset on curve on bottom than inset (around 4.5%) on top...
                int x2 = x + contentWidth;
                int y2 = y;
                int thisy = totalContentInset + boxHeight;
                drawZendikarCurvedFace(g, image, x, thisy, x2, y2,
                        boxColor, borderPaint);
            } else if (cardView.getFrameStyle() == FrameStyle.BFZ_FULL_ART_BASIC) {
                // Draw curved lines (BFZ land style)
                int y2 = y;
                int yb = totalContentInset + boxHeight;
                int topxdelta = 45 * contentWidth / 1000;
                int endydelta = 60 * (totalContentInset + y2) / 265;
                int x2 = x + contentWidth;

                // Curve ends at 60 out of 265
                drawBFZCurvedFace(g, image, x, yb, x2, y2,
                        topxdelta, endydelta,
                        boxColor, borderPaint);
            }

            // If an expedition, needs the rules box to be visible.
            if (cardView.getExpansionSetCode().equals("EXP")) {
                // Draw a small separator between the type line and box, and shadow
                // at the left of the texbox, and above the name line
                g.setPaint(textboxPaint);
                float alpha = 0.55f;
                AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                Composite origc = g.getComposite();
                g.setComposite(comp);
                g.setBackground(new Color(155, 0, 0, 150));

                g.fillRect(
                        totalContentInset + 1, typeLineY - boxHeight,
                        contentWidth - 2, cardHeight - borderWidth * 3 - typeLineY - 1);

                g.setComposite(origc);

                g.fillRect(
                        totalContentInset - 1, totalContentInset - 1,
                        contentWidth + 1, 1);

                g.fillRect(
                        totalContentInset + 1, typeLineY - boxHeight,
                        contentWidth - 2, 1);

                drawRulesText(g, textboxKeywords, textboxRules,
                        totalContentInset + 2, typeLineY - boxHeight,
                        contentWidth - 4, cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3, true);
            }

            CardRendererUtils.drawZendikarLandBox(g,
                    x, y, w, h,
                    contentInset,
                    borderPaint, boxColor);
            drawTypeLine(g, attribs, getCardSuperTypeLine(),
                    totalContentInset + contentInset, typeLineY + boxHeight + (cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3) / 2 - contentInset,
                    contentWidth / 2 - boxHeight, boxHeight - 4, false);
            drawTypeLine(g, attribs, getCardSubTypeLine(),
                    totalContentInset + 4 * contentWidth / 7 + boxHeight, typeLineY + boxHeight + (cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3) / 2 - contentInset,
                    3 * contentWidth / 7 - boxHeight - contentInset, boxHeight - 4, true);
            drawRulesText(g, textboxKeywords, textboxRules,
                    x, y,
                    w, h, false);
        } else if (isUnstableFullArtLand()) {
            int x = 0;
            int y = 0;
            int w = cardWidth;
            int h = cardHeight;

            // Curve ends at 60 out of 265
            drawUSTCurves(g, image, x, y, w, h,
                    0, 0,
                    additionalBoxColor, borderPaint);
        } else if (!isZenUst) {
            drawRulesText(g, textboxKeywords, textboxRules,
                    totalContentInset + 2, typeLineY + boxHeight + 2,
                    contentWidth - 4, cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3, false);
        }

        // Draw the bottom right stuff
        drawBottomRight(g, attribs, borderPaint, boxColor);
    }

    public void drawZendikarCurvedFace(Graphics2D g2, BufferedImage image, int x, int y, int x2, int y2,
                                       Color boxColor, Paint paint) {

        BufferedImage artToUse = faceArtImage;
        boolean hadToUseFullArt = false;
        if (faceArtImage == null) {
            if (artImage == null) {
                return;
            }
            hadToUseFullArt = true;
            artToUse = artImage;
        }
        int srcW = artToUse.getWidth();
        int srcH = artToUse.getHeight();

        if (hadToUseFullArt) {
            // Get a box based on the standard scan from gatherer.
            // Width = 185/223 pixels (centered)
            // Height = 220/310, 38 pixels from top
            int subx = 19 * srcW / 223;
            int suby = 38 * srcH / 310;
            artToUse = artImage.getSubimage(subx, suby, 185 * srcW / 223, 220 * srcH / 310);
        }

        Path2D.Double curve = new Path2D.Double();

        int ew = x2 - x;
        int eh = 700 * (y2 - y) / 335;
        Arc2D arc = new Arc2D.Double(x, y - 197 * eh / 700, ew, eh, 0, 360, Arc2D.OPEN);
        Arc2D innerarc = new Arc2D.Double(x + 1, y - 197 * eh / 700 + 1, ew - 2, eh - 2, 0, 360, Arc2D.OPEN);

        curve.append(new Rectangle2D.Double(x, y, x2 - x, y2 - y), false);
        g2.setClip(new Rectangle2D.Double(x, y, x2 - x, y2 - y));
        g2.setClip(arc);

        Rectangle2D r = curve.getBounds2D();
        g2.drawImage(artToUse, x, y, x2 - x, y2 - y, null);
        g2.setClip(null);
        g2.setClip(new Rectangle2D.Double(x, y, x2 - x, y2 - y));

        g2.setColor(CardRendererUtils.abitdarker(boxColor));
        g2.draw(arc);
        g2.setColor(Color.black);
        g2.draw(innerarc);

        g2.setClip(null);
    }

    public void drawBFZCurvedFace(Graphics2D g2, BufferedImage image, int x, int y, int x2, int y2,
                                  int topxdelta, int endydelta,
                                  Color boxColor, Paint paint) {
        BufferedImage artToUse = faceArtImage;
        boolean hadToUseFullArt = false;
        if (faceArtImage == null) {
            if (artImage == null) {
                return;
            }
            hadToUseFullArt = true;
            artToUse = artImage;
        }
        int srcW = artToUse.getWidth();
        int srcH = artToUse.getHeight();

        if (hadToUseFullArt) {
            // Get a box based on the standard scan from gatherer.
            // Width = 185/223 pixels (centered)
            // Height = 220/310, 38 pixels from top
            int subx = 19 * srcW / 223;
            int suby = 38 * srcH / 310;
            artToUse = artImage.getSubimage(subx, suby, 185 * srcW / 223, 220 * srcH / 310);
        }

        Path2D.Double curve = new Path2D.Double();
        curve.moveTo(x + topxdelta, y);
        curve.quadTo(x, y + endydelta / 2, x, y + endydelta);
        curve.lineTo(x, y2);
        curve.lineTo(x2, y2);
        curve.lineTo(x2, y + endydelta);
        curve.quadTo(x2, y + endydelta / 2, x2 - topxdelta, y);
        curve.lineTo(x + topxdelta, y);

        Path2D.Double innercurve = new Path2D.Double();
        innercurve.moveTo(x + topxdelta, y + 1);
        innercurve.quadTo(x + 1, y + endydelta / 2, x + 1, y + endydelta);
        innercurve.lineTo(x + 1, y2 - 1);
        innercurve.lineTo(x2 - 1, y2 - 1);
        innercurve.lineTo(x2 - 1, y + endydelta);
        innercurve.quadTo(x2 - 1, y + endydelta / 2, x2 - topxdelta, y + 1);
        innercurve.lineTo(x + topxdelta, y + 1);

        Rectangle2D r = curve.getBounds2D();
        int minX = (int) r.getX();

        g2.setClip(curve);
        g2.drawImage(artToUse, minX, y, (x2 - x) + (x - minX) * 2, y2 - y, null);

        g2.setClip(null);
        g2.setColor(CardRendererUtils.abitdarker(boxColor));
        g2.setPaint(paint);
        g2.draw(curve);

        g2.setColor(Color.black);
        g2.draw(innercurve);
    }

    public void drawUSTCurves(Graphics2D g2, BufferedImage image, int x, int y, int x2, int y2,
                              int topxdelta, int endydelta,
                              Color boxColor, Paint paint) {
        BufferedImage artToUse = artImage;

        int srcW = x2;
        int srcH = y2;
        if (artToUse != null) {
            srcW = artToUse.getWidth();
            srcH = artToUse.getHeight();
        }

        g2.setPaint(paint);

        // Dimensions:  534 height, 384 width, 34 offset at top, 41 offset at bottom.  Curve at bottom right is from an ellipse: 245 high, 196 wide, with center offset from
        // right side by 36  (so top left is at: (width - 159, height - 41 -196)  center at: 41+127 = width - 36, height - 168)
        int scan_width = 384;
        int scan_height = 534;
        int scan_ew = 196;
        int scan_eh = 254;
        int offset_ew = 159;
        int offset_eh = 41;
        int middle_ew = 52;
        int middle_eh = 26;

        // Bottom left side arc
        int ex = (offset_ew - scan_ew) * x2 / scan_width;
        int ey = y2 - (offset_eh + scan_eh) * y2 / scan_height;
        int bot_ey = y2 - offset_eh * y2 / scan_height;
        int ew = scan_ew * x2 / scan_width;
        int eh = scan_eh * y2 / scan_height;
        int end_curve_ex = ex + ew / 2;

        Arc2D arc = new Arc2D.Double(ex, ey, ew, eh, 180, 90, Arc2D.OPEN);

        // Bottom right side arc
        ex = x2 - offset_ew * x2 / scan_width;
        ey = y2 - (offset_eh + scan_eh) * y2 / scan_height;
        bot_ey = y2 - offset_eh * y2 / scan_height;
        Arc2D arc2 = new Arc2D.Double(ex, ey, ew, eh, 270, 90, Arc2D.OPEN);

        // Middle bump.. 52x26
        int mid_ex = x2 / 2 - middle_ew * x2 / (scan_width * 2);
        int mid_ey = bot_ey - middle_eh * y2 / (scan_height * 2);
        int end_mid_ex = x2 / 2 + middle_ew * x2 / (scan_width * 2);

        Arc2D arc3 = new Arc2D.Double(mid_ex, mid_ey, middle_ew * x2 / scan_width, middle_eh * y2 / scan_height, 180, -180, Arc2D.OPEN);

        Path2D.Double curve = new Path2D.Double();
        curve.moveTo(0, 0);
        curve.lineTo(0, bot_ey);
        curve.append(arc, true);
        curve.lineTo(mid_ex, bot_ey);
        curve.append(arc3, true);
        curve.lineTo(x2 - ew / 2, bot_ey);
        curve.append(arc2, true);
        curve.lineTo(x2, 0);
        curve.lineTo(0, 0);

        g2.setClip(curve);
        if (artToUse != null) {
            artToUse = artImage.getSubimage(0, 0, srcW, srcH);
            g2.drawImage(artToUse, 0, 0, x2, y2, null);
        }

        g2.setClip(null);
        g2.setStroke(new BasicStroke(3));
        g2.draw(arc);
        g2.draw(new Rectangle(end_curve_ex, bot_ey, mid_ex - end_curve_ex, 0));
        g2.draw(arc3);
        g2.draw(new Rectangle(end_mid_ex, bot_ey, mid_ex - end_curve_ex, 0));
        g2.draw(arc2);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(boxColor);
    }

    // Draw the name line
    protected void drawNameLine(Graphics2D g, CardPanelAttributes attribs, String baseName, String manaCost, int x, int y, int w, int h) {
        // Width of the mana symbols
        int manaCostWidth;
        if (cardView.isAbility()) {
            manaCostWidth = 0;
        } else {
            manaCostWidth = CardRendererUtils.getManaCostWidth(manaCost, boxTextHeight);
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
            nameStr = baseName;
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
                g.setColor(getBoxTextColor(attribs));
                layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);
            }
        }

        // Draw the mana symbols
        if (!cardView.isAbility() && !cardView.isFaceDown()) {
            ManaSymbols.draw(g, manaCost, x + w - manaCostWidth, y + boxTextOffset, boxTextHeight, ModernCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
        }
    }

    // Draw the type line (color indicator, types, and expansion symbol)
    protected void drawTypeLine(Graphics2D g, CardPanelAttributes attribs, String baseTypeLine, int x, int y, int w, int h, boolean withSymbol) {
        // Draw expansion symbol
        int expansionSymbolWidth = 0;
        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_SET_SYMBOL, "false").equals("false")) {
            if (cardView.isAbility()) {
                expansionSymbolWidth = 0;
            } else if (withSymbol) {
                expansionSymbolWidth = drawExpansionSymbol(g, x, y, w, h);
            }
        } else {
            expansionSymbolWidth = 0;
        }

        // Draw type line text
        int availableWidth = w - expansionSymbolWidth + 1;
        String types = baseTypeLine;
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
                g.setColor(getBoxTextColor(attribs));
                layout.draw(g, x, y + (h - boxTextHeight) / 2 + boxTextHeight - 1);
            }
        }
    }

    public void paintOutlineTextByGlow(Graphics2D g, String text, Color color, int x, int y) {
        GlowText label = new GlowText();
        label.setGlow(Color.black, 6, 3);
        label.setText(text);
        label.setFont(g.getFont().deriveFont(Font.BOLD));
        label.setForeground(color);
        Dimension ptSize = label.getPreferredSize();
        label.setSize(ptSize.width, ptSize.height);
        g.drawImage(label.getGlowImage(), x, y, null);
    }

    public void paintOutlineTextByStroke(Graphics2D g, String text, Color color, int x, int y) {
        // https://stackoverflow.com/a/35222059/1276632
        Color outlineColor = Color.black;
        Color fillColor = color;
        BasicStroke outlineStroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        // remember original settings
        Color originalColor = g.getColor();
        Stroke originalStroke = g.getStroke();
        RenderingHints originalHints = g.getRenderingHints();

        // create a glyph vector from your text
        GlyphVector glyphVector = g.getFont().createGlyphVector(g.getFontRenderContext(), text);
        // get the shape object
        Shape textShape = glyphVector.getOutline(x, y);

        // activate anti aliasing for text rendering (if you want it to look nice)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(outlineColor);
        g.setStroke(outlineStroke);
        g.draw(textShape); // draw outline

        g.setColor(fillColor);
        g.fill(textShape); // fill the shape

        // reset to original settings after painting
        g.setColor(originalColor);
        g.setStroke(originalStroke);
        g.setRenderingHints(originalHints);
    }

    // Draw the P/T and/or Loyalty boxes
    protected void drawBottomRight(Graphics2D g, CardPanelAttributes attribs, Paint borderPaint, Color fill) {
        // No bottom right for abilities
        if (cardView.isAbility()) {
            return;
        }

        // Where to start drawing the things
        int curY = cardHeight - (int) (0.03f * cardHeight);

        // Width of the boxes
        int partBoxWidth = (int) Math.max(30, 0.20f * cardWidth);

        // Is it a creature?
        boolean isVehicle = cardView.getSubTypes().contains(SubType.VEHICLE);
        if (cardView.isCreature() || isVehicle) {

            // draws p/t by parts
            int ptDeviderSpace = 1;  // Arial font is too narrow for devider (2/2) and needs extra space
            String ptText1 = cardView.getPower();
            String ptText2 = "/";
            String ptText3 = CardRendererUtils.getCardLifeWithDamage(cardView);
            int ptTextWidth1 = g.getFontMetrics(ptTextFont).stringWidth(ptText1);
            int ptTextWidth2 = g.getFontMetrics(ptTextFont).stringWidth(ptText2) + 2 * ptDeviderSpace;
            int ptTextWidth3 = g.getFontMetrics(ptTextFont).stringWidth(ptText3);

            // PT max size
            int ptContentWidth = contentInset + ptTextWidth1 + ptDeviderSpace + ptTextWidth2 + ptDeviderSpace + ptTextWidth3 + contentInset;
            partBoxWidth = Math.max(ptContentWidth, partBoxWidth);

            int x = cardWidth - borderWidth - partBoxWidth;

            // Draw PT box
            CardRendererUtils.drawRoundedBox(g,
                    x, curY - boxHeight,
                    partBoxWidth, boxHeight,
                    contentInset,
                    borderPaint,
                    isVehicle ? BOX_VEHICLE : fill);

            // Draw shadow line top
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(
                    x + contentInset, curY - boxHeight - 1,
                    partBoxWidth - 2 * contentInset, 1);

            // Draw text
            Color defaultTextColor;
            boolean defaultTextLight;
            if (isVehicle) {
                boolean isAnimated = !(cardView instanceof PermanentView) || cardView.isCreature();
                if (isAnimated) {
                    defaultTextColor = Color.white;
                } else {
                    defaultTextColor = new Color(180, 180, 180);
                }
                defaultTextLight = true;
            } else {
                defaultTextColor = getBoxTextColor(attribs);
                defaultTextLight = !defaultTextColor.equals(Color.black);
            }
            g.setColor(defaultTextColor);
            g.setFont(ptTextFont);

            // draws
            int ptEmptySpace = (partBoxWidth - ptContentWidth) / 2;
            int ptPosStart1 = x + contentInset + ptEmptySpace;
            int ptPosStart2 = ptPosStart1 + ptTextWidth1 + ptDeviderSpace;
            int ptPosStart3 = ptPosStart2 + ptTextWidth2 + ptDeviderSpace;
            // p
            g.setColor(CardRendererUtils.getCardTextColor(cardView.getOriginalCard().getPower(), false, defaultTextColor, defaultTextLight));
            g.drawString(ptText1, ptPosStart1, curY - ptTextOffset - 1); // left
            // /
            g.setColor(defaultTextColor);
            g.drawString(ptText2, ptPosStart2, curY - ptTextOffset - 1); // center
            // t
            g.setColor(CardRendererUtils.getCardTextColor(cardView.getOriginalCard().getToughness(), CardRendererUtils.isCardWithDamage(cardView), defaultTextColor, defaultTextLight));
            g.drawString(ptText3, ptPosStart3, curY - ptTextOffset - 1); // right
            //
            g.setColor(defaultTextColor);

            // Advance
            curY -= boxHeight;
        }

        // Is it a walker? (But don't draw the box if it's a non-permanent view
        // of a walker without a starting loyalty (EG: Arlin Kord's flipped side).
        if (cardView.isPlanesWalker()
                && (cardView instanceof PermanentView || !cardView.getStartingLoyalty().equals("0"))) {
            // Draw the PW loyalty box
            int w = partBoxWidth;
            int h = partBoxWidth / 2;
            int x = cardWidth - partBoxWidth - borderWidth;
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
            int x = cardWidth - partBoxWidth - borderWidth;
            int y = curY - boxHeight;
            String damage = String.valueOf(((PermanentView) cardView).getDamage());
            g.setFont(ptTextFont);
            int txWidth = g.getFontMetrics().stringWidth(damage);
            g.setColor(Color.red);
            g.fillRect(x, y, partBoxWidth, boxHeight);
            g.setColor(Color.white);
            g.drawRect(x, y, partBoxWidth, boxHeight);
            g.drawString(damage, x + (partBoxWidth - txWidth) / 2, curY - 1);
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

    protected void drawRulesText(Graphics2D g, List<TextboxRule> keywords, List<TextboxRule> rules, int x, int y, int w, int h, boolean forceRules) {
        // Gather all rules to render
        List<TextboxRule> allRules = new ArrayList<>(rules);

        // Add the keyword rule if there are any keywords
        if (!keywords.isEmpty()) {
            String keywordRulesString = getKeywordRulesString(keywords);
            TextboxRule keywordsRule = new TextboxRule(keywordRulesString, new ArrayList<>());
            allRules.add(0, keywordsRule);
        }

        if (isUnstableFullArtLand()) {
            return;
        }

        // Basic mana draw mana symbol in textbox (for basic lands)
        if (!forceRules && (allRules.size() == 1 && (allRules.get(0) instanceof TextboxBasicManaRule) && cardView.isLand() || isZendikarFullArtLand())) {
            if (!isZendikarFullArtLand()) {
                drawBasicManaTextbox(g, x, y, w, h, ((TextboxBasicManaRule) allRules.get(0)).getBasicManaSymbol());
                return;
            } else // Big circle in the middle for Zendikar lands
                if (allRules.size() == 1) {
                    // Size of mana symbol = 9/4 * h, 3/4h above line
                    if (allRules.get(0) instanceof TextboxBasicManaRule) {
                        drawBasicManaSymbol(g, x + w / 2 - 9 * h / 8 + 1, y - 3 * h / 4, 9 * h / 4, 9 * h / 4, ((TextboxBasicManaRule) allRules.get(0)).getBasicManaSymbol());
                    } else {
                        drawBasicManaSymbol(g, x + w / 2 - h - h / 8, y - 3 * h / 4, 9 * h / 4, 9 * h / 4, cardView.getFrameColor().toString());
                    }
                    return;
                } else {
                    if (allRules.size() > 1) {
                        drawBasicManaSymbol(g, x + w / 2 - h - h / 8, y - 3 * h / 4, 9 * h / 4, 9 * h / 4, cardView.getFrameColor().toString());
                    }
                    return;
                }
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
        ManaSymbols.draw(g, symbs, x + (w - manaCostWidth) / 2, y + (h - symbHeight) / 2, symbHeight, ModernCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
    }

    private void drawBasicManaSymbol(Graphics2D g, int x, int y, int w, int h, String symbol) {
        String symbs = symbol;
        if (getSizedManaSymbol(symbol) != null) {
            ManaSymbols.draw(g, symbs, x, y, w, ModernCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
        }
        if (symbol.length() == 2) {
            String symbs2 = "" + symbol.charAt(1) + symbol.charAt(0);
            if (getSizedManaSymbol(symbs2) != null) {
                ManaSymbols.draw(g, symbs2, x, y, w, ModernCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
            }
        }
    }

    // Get the first line of the textbox, the keyword string
    private static String getKeywordRulesString(List<TextboxRule> keywords) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keywords.size(); ++i) {
            builder.append(keywords.get(i).text);
            if (i != keywords.size() - 1) {
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
        if (availWidth < 0) {
            return 0;
        }

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

    protected boolean isTransformCard(CardPanelAttributes attribs) {
        return cardView.canTransform() || attribs.isTransformed;
    }

    protected int drawTransformationCircle(Graphics2D g, CardPanelAttributes attribs, Paint borderPaint) {
        int transformCircleOffset = 0;
        if (isTransformCard(attribs)) {
            transformCircleOffset = boxHeight - contentInset;
            g.setPaint(borderPaint);
            g.drawOval(borderWidth, totalContentInset, boxHeight - 1, boxHeight - 1);
            g.setColor(Color.black);
            g.fillOval(borderWidth + 1, totalContentInset + 1, boxHeight - 2, boxHeight - 2);
            g.setColor(Color.white);
            if (attribs.isTransformed) {
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
    protected Color getBoxTextColor(CardPanelAttributes attribs) {
        if (attribs.isTransformed) {
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
    protected static Paint getBackgroundPaint(ObjectColor colors, Collection<CardType> types, SubTypes subTypes) {
        if (subTypes.contains(SubType.VEHICLE)) {
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

    // Determine which background image to use from a set of colors
    // and the current card.
    protected static BufferedImage getBackgroundImage(ObjectColor colors, Collection<CardType> types, SubTypes subTypes, boolean isExped) {
        if (subTypes.contains(SubType.VEHICLE)) {
            return BG_IMG_VEHICLE;
        } else if (types.contains(CardType.LAND)) {
            if (isExped) {
                return BG_IMG_EXPEDITION;
            }
            return BG_IMG_LAND;
        } else if (types.contains(CardType.ARTIFACT)) {
            return BG_IMG_ARTIFACT;
        } else if (colors.isMulticolored()) {
            return BG_IMG_GOLD;
        } else if (colors.isWhite()) {
            return BG_IMG_WHITE;
        } else if (colors.isBlue()) {
            return BG_IMG_BLUE;
        } else if (colors.isBlack()) {
            return BG_IMG_BLACK;
        } else if (colors.isRed()) {
            return BG_IMG_RED;
        } else if (colors.isGreen()) {
            return BG_IMG_GREEN;
        } else {
            // Colorless
            return BG_IMG_COLORLESS;
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

    protected Color getAdditionalBoxColor(ObjectColor colors, Collection<CardType> types, boolean isNightCard) {
        if (isUnstableFullArtLand()) {
            if (colors.isWhite()) {
                return BOX_UST_WHITE;
            } else if (colors.isBlue()) {
                return BOX_UST_BLUE;
            } else if (colors.isBlack()) {
                return BOX_UST_BLACK;
            } else if (colors.isRed()) {
                return BOX_UST_RED;
            } else if (colors.isGreen()) {
                return BOX_UST_GREEN;
            }
        }
        return getBoxColor(colors, types, isNightCard);
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

    // Determine the land textbox color for the spiral colours
    protected static Color getSpiralLandTextboxColor(ObjectColor color, ObjectColor secondColor, boolean firstOne) {
        // Absolutely mental, but the coloring for the spirals is as follows (reading from biggest box in):
        // WG WU BW RW UG BU BG RB RG RU
        boolean white = color.isWhite() || secondColor.isWhite();
        boolean blue = color.isBlue() || secondColor.isBlue();
        boolean black = color.isBlack() || secondColor.isBlack();
        boolean red = color.isRed() || secondColor.isRed();
        boolean green = color.isGreen() || secondColor.isGreen();

        if (white && green) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_WHITE : LAND_SPIRAL_TEXTBOX_GREEN;
        }
        if (white && blue) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_WHITE : LAND_SPIRAL_TEXTBOX_BLUE;
        }
        if (black && white) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_BLACK : LAND_SPIRAL_TEXTBOX_WHITE;
        }
        if (red && white) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_RED : LAND_SPIRAL_TEXTBOX_WHITE;
        }
        if (blue && green) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_BLUE : LAND_SPIRAL_TEXTBOX_GREEN;
        }
        if (black && blue) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_BLACK : LAND_SPIRAL_TEXTBOX_BLUE;
        }
        if (black && green) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_BLACK : LAND_SPIRAL_TEXTBOX_GREEN;
        }
        if (red && black) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_RED : LAND_SPIRAL_TEXTBOX_BLACK;
        }
        if (red && green) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_RED : LAND_SPIRAL_TEXTBOX_GREEN;
        }
        if (red && blue) {
            return firstOne ? LAND_SPIRAL_TEXTBOX_RED : LAND_SPIRAL_TEXTBOX_BLUE;
        }

        return getLandTextboxColor(color);
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

    private static Color getLessOpaqueColor(Color color, boolean lessOpaqueRulesTextBox) {
        if (lessOpaqueRulesTextBox) {
            Color lessOpaque = new Color (color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() - 50);
            return lessOpaque;
        }
        return color;
    }

    // Determine the border paint to use, based on an ObjectColors
    protected static Paint getTextboxPaint(ObjectColor colors, Collection<CardType> types, int width, boolean lessOpaqueRulesTextBox) {
        if (colors.isMulticolored()) {
            if (colors.getColorCount() == 2) {
                List<ObjectColor> twoColors = colors.getColors();
                Color[] translatedColors;
                if (types.contains(CardType.LAND)) {
                    translatedColors = new Color[]{
                            getLessOpaqueColor(getLandTextboxColor(twoColors.get(0)), lessOpaqueRulesTextBox),
                            getLessOpaqueColor(getLandTextboxColor(twoColors.get(1)), lessOpaqueRulesTextBox)
                    };
                } else {
                    translatedColors = new Color[]{
                            getLessOpaqueColor(getTextboxColor(twoColors.get(0)), lessOpaqueRulesTextBox),
                            getLessOpaqueColor(getTextboxColor(twoColors.get(1)), lessOpaqueRulesTextBox)
                    };
                }

                // Special case for two colors, gradient paint
                return new LinearGradientPaint(
                        0, 0, width, 0,
                        new float[]{0.4f, 0.6f},
                        translatedColors);
            } else if (types.contains(CardType.LAND)) {
                return getLessOpaqueColor(LAND_TEXTBOX_GOLD, lessOpaqueRulesTextBox);
            } else {
                return getLessOpaqueColor(TEXTBOX_GOLD, lessOpaqueRulesTextBox);
            }
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return getLessOpaqueColor(TEXTBOX_LAND, lessOpaqueRulesTextBox);
            } else {
                return getLessOpaqueColor(TEXTBOX_COLORLESS, lessOpaqueRulesTextBox);
            }
        } else if (types.contains(CardType.LAND)) {
            return getLessOpaqueColor(getLandTextboxColor(colors), lessOpaqueRulesTextBox);
        } else {
            return getLessOpaqueColor(getTextboxColor(colors), lessOpaqueRulesTextBox);
        }
    }
}
