package org.mage.card.arcane;

import mage.MageInt;
import mage.ObjectColor;
import mage.cards.ArtRect;
import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.constants.SubType;
import mage.view.CardView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mage.card.arcane.ManaSymbols.getSizedManaSymbol;
import static org.mage.card.arcane.ModernCardResourceLoader.*;

/**
 * @author stravant@gmail.com, JayDi85, Jmlundeen
 * <p>
 * Base rendering class for old border cards
 * M15 frame style, for another styles see https://www.mtg.onl/evolution-of-magic-token-card-frame-design/
 */
public class RetroCardRenderer extends CardRenderer {

    private static final Logger LOGGER = Logger.getLogger(RetroCardRenderer.class);
    private static final GlowText glowTextRenderer = new GlowText();
    public static final Color MANA_ICONS_TEXT_COLOR = Color.DARK_GRAY; // text color of missing mana icons in IMAGE render mode

    // public static final Font BASE_BELEREN_FONT = loadFont("beleren-bold");

    public static final BufferedImage BG_IMG_WHITE = loadBackgroundImage("white_retro");
    public static final BufferedImage BG_IMG_BLUE = loadBackgroundImage("blue_retro");
    public static final BufferedImage BG_IMG_BLACK = loadBackgroundImage("black");
    public static final BufferedImage BG_IMG_RED = loadBackgroundImage("red_retro");
    public static final BufferedImage BG_IMG_GREEN = loadBackgroundImage("green_retro");
    public static final BufferedImage BG_IMG_GOLD = loadBackgroundImage("gold_retro");
    public static final BufferedImage BG_IMG_ARTIFACT = loadBackgroundImage("artifact_retro");
    public static final BufferedImage BG_IMG_LAND = loadBackgroundImage("land_retro");
    public static final BufferedImage BG_IMG_COLORLESS = loadBackgroundImage("colorless_retro");

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

    protected int frameInset;

    // Width of the content region of the card
    // = cardWidth - 2 x totalContentInset
    protected int contentWidth;

    // Width of art / text box
    protected int innerContentWidth;
    // X position of inside content
    private int innerContentStart;


    // How tall the name / type lines and P/T box are
    protected static final float BOX_HEIGHT_FRAC = 0.065f; // x cardHeight
    protected static final int BOX_HEIGHT_MIN = 16;
    protected int boxHeight;

    // How far down the card is the type line placed?
    protected static final float TYPE_LINE_Y_FRAC = 0.54f; // x cardHeight
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
    protected String manaCostString;

    // Is an adventure or omen
    protected boolean isCardWithSpellOption = false;

    public RetroCardRenderer(CardView card) {
        // Pass off to parent
        super(card);

        // Mana cost string
        manaCostString = ManaSymbols.getClearManaCost(cardView.getManaCostStr());
    }

    protected boolean isCardWithSpellOption() {
        return isCardWithSpellOption;
    }

    @Override
    protected void layout(int cardWidth, int cardHeight) {
        // Pass to parent
        super.layout(cardWidth, cardHeight);

        borderWidth = (int) Math.max(
                BORDER_WIDTH_MIN,
                0.042 * cardWidth);

        frameInset = (int) Math.max(
                BORDER_WIDTH_MIN,
                0.012 * cardWidth);

        // Content inset, just equal to border width
        contentInset = borderWidth - frameInset;

        // Total content inset helper
        totalContentInset = borderWidth + contentInset;

        // Content width
        contentWidth = cardWidth - 2 * totalContentInset;

        // Box height
        boxHeight = (int) Math.max(
                BOX_HEIGHT_MIN,
                BOX_HEIGHT_FRAC * cardHeight);

        // Art / text box size
        innerContentWidth = contentWidth - boxHeight + frameInset * 2;
        innerContentStart = totalContentInset + boxHeight / 2 - frameInset;

        // Type line at
        typeLineY = (int) (TYPE_LINE_Y_FRAC * cardHeight);

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
    }

    @Override
    protected void drawBackground(Graphics2D g) {
        if (cardView.isFaceDown()) {
            // just draw a brown rectangle
            drawCardBackTexture(g);
        } else {
            BufferedImage bg = getBackgroundTexture(cardView.getColor(), cardView.getCardTypes());
            if (bg == null) {
                return;
            }
            int bgw = bg.getWidth();
            int bgh = bg.getHeight();

            Rectangle bgRect = new Rectangle(borderWidth , borderWidth,
                    cardWidth - borderWidth * 2, cardHeight - borderWidth * 2);
            Area area = new Area(bgRect);
            g.setClip(area);
            g.drawImage(bg, 0, 0, cardWidth, cardHeight, 0, 0, bgw, bgh, BOX_BLUE, null);
        }
    }

    private boolean isOriginalDualLand() {
        return cardView.getFrameStyle() == FrameStyle.LEA_ORIGINAL_DUAL_LAND_ART_BASIC;
    }

    @Override
    protected void drawArt(Graphics2D g) {
        if (artImage != null) {

            boolean shouldPreserveAspect = false;
            Rectangle2D sourceRect = ArtRect.RETRO.rect;

            if (cardView.getMageObjectType() == MageObjectType.SPELL) {
                ArtRect rect = cardView.getArtRect();
                if (rect != ArtRect.NORMAL) {
                    sourceRect = rect.rect;
                }
            }

            // Normal drawing of art from a source part of the card frame into the rect
            drawArtIntoRect(g,
                    totalContentInset + boxHeight / 2, totalContentInset + boxHeight / 2,
                    contentWidth - boxHeight, typeLineY - borderWidth * 3,
                    sourceRect, shouldPreserveAspect);

        }
    }

    @Override
    protected void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image, boolean lessOpaqueRulesTextBox) {
        // Get the card colors to base the frame on
        ObjectColor frameColors = getFrameObjectColor();

        // Get the border paint
        Color boxColor = getBoxColor(frameColors, cardView.getCardTypes(), attribs.isTransformed);
        Paint textboxPaint = getTextboxPaint(frameColors, cardView.getCardTypes(), cardWidth, lessOpaqueRulesTextBox);
        Paint borderPaint = getBorderPaint(frameColors, cardView.getCardTypes(), cardWidth);

        // Special colors
        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            boxColor = BOX_INVENTION;
        }

        // Draw the textbox fill
        drawTextboxBackground(g, textboxPaint, frameColors, isOriginalDualLand());

        drawInsetFrame(g, innerContentStart, innerContentStart,
                innerContentWidth, typeLineY - borderWidth * 3 + frameInset * 2);

        drawTypeLine(g, attribs, getCardTypeLine(),
                innerContentStart, typeLineY,
                contentWidth, boxHeight, true);

        // Draw the transform circle
        int nameOffset = drawTransformationCircle(g, attribs, borderPaint);

        // Draw the name line
        drawNameLine(g, attribs, cardView.getDisplayName(), manaCostString,
                innerContentStart + nameOffset, totalContentInset / 2 - frameInset,
                contentWidth - nameOffset - borderWidth - frameInset, boxHeight);

        // Draw the textbox rules
        drawRulesText(g, textboxKeywords, textboxRules,
                innerContentStart + 2, typeLineY + boxHeight + 2,
                innerContentWidth - 4, cardHeight - typeLineY - boxHeight - 4 - borderWidth * 3, false);

        // Draw the bottom right stuff
        drawBottomRight(g, attribs, borderPaint, boxColor);
    }

    private void drawInsetFrame(Graphics2D g2, int x, int y, int width, int height) {

        // Outer and inner bounds
        int x0 = x;
        int y0 = y;
        int x1 = x + width;
        int y1 = y + height;

        int xi0 = x + frameInset;
        int yi0 = y + frameInset;
        int xi1 = x + width - frameInset;
        int yi1 = y + height - frameInset;

        // Colors for visual effect (you can customize this)
        Color topColor = new Color(85, 68, 32);
        Color leftColor = new Color(152, 124, 107);
        Color rightColor = new Color(85, 68, 32);
        Color bottomColor = new Color(152, 124, 107);

        // Top trapezoid
        g2.setColor(topColor);
        Path2D top = new Path2D.Double();
        top.moveTo(x0, y0);
        top.lineTo(x1, y0);
        top.lineTo(xi1, yi0);
        top.lineTo(xi0, yi0);
        top.closePath();
        g2.fill(top);

        // Left trapezoid
        g2.setColor(leftColor);
        Path2D left = new Path2D.Double();
        left.moveTo(x0, y0);
        left.lineTo(xi0, yi0);
        left.lineTo(xi0, yi1);
        left.lineTo(x0, y1);
        left.closePath();
        g2.fill(left);

        // Right trapezoid
        g2.setColor(rightColor);
        Path2D right = new Path2D.Double();
        right.moveTo(x1, y0);
        right.lineTo(x1, y1);
        right.lineTo(xi1, yi1);
        right.lineTo(xi1, yi0);
        right.closePath();
        g2.fill(right);

        // Bottom trapezoid
        g2.setColor(bottomColor);
        Path2D bottom = new Path2D.Double();
        bottom.moveTo(x0, y1);
        bottom.lineTo(x1, y1);
        bottom.lineTo(xi1, yi1);
        bottom.lineTo(xi0, yi1);
        bottom.closePath();
        g2.fill(bottom);
    }

    private void drawTextboxBackground(Graphics2D g, Paint textboxPaint, ObjectColor frameColors, boolean isOriginalDual) {
            g.setPaint(textboxPaint);
        int x = innerContentStart;
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
                    g.fillRect(x, typeLineY + boxHeight + 1, innerContentWidth - 2, total_height_of_box);
                }
                if (frameColors.getColorCount() >= 3) {
                    g.fillRect(x, typeLineY + boxHeight + 1, innerContentWidth - 2, total_height_of_box);
                }
                if (frameColors.getColorCount() == 2) {
                    if (isOriginalDual) {
                        g.setPaint(getSpiralLandTextboxColor(twoColors.get(0), twoColors.get(1), true));

                        // Horizontal bars
                        g.fillRect(x, typeLineY + boxHeight + 1, innerContentWidth - 2, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 2 * height_of_spiral, typeLineY + boxHeight + 1 + 2 * height_of_spiral, innerContentWidth - 2 - 4 * height_of_spiral, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 4 * height_of_spiral, typeLineY + boxHeight + 1 + 4 * height_of_spiral, innerContentWidth - 2 - 8 * height_of_spiral, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 6 * height_of_spiral, typeLineY + boxHeight + 1 + 6 * height_of_spiral, innerContentWidth - 2 - 12 * height_of_spiral, height_of_spiral);

                        g.fillRect(totalContentInset + 1 + 6 * height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 7 * height_of_spiral, innerContentWidth - 2 - 12 * height_of_spiral, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 4 * height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 5 * height_of_spiral, innerContentWidth - 2 - 8 * height_of_spiral, height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 2 * height_of_spiral, typeLineY + boxHeight + 1 + total_height_of_box - 3 * height_of_spiral, innerContentWidth - 2 - 4 * height_of_spiral, height_of_spiral);
                        g.fillRect(x, typeLineY + boxHeight + 1 + total_height_of_box - height_of_spiral, innerContentWidth - 2, height_of_spiral);

                        // Vertical bars
                        g.fillRect(x, typeLineY + boxHeight + 1, height_of_spiral, total_height_spiral - 1);
                        g.fillRect(totalContentInset + 1 + 2 * height_of_spiral, typeLineY + boxHeight + 1 + 2 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 4 * height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 4 * height_of_spiral, typeLineY + boxHeight + 1 + 4 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 8 * height_of_spiral);
                        g.fillRect(totalContentInset + 1 + 6 * height_of_spiral, typeLineY + boxHeight + 1 + 6 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 12 * height_of_spiral);

                        g.fillRect(totalContentInset + innerContentWidth - 7 * height_of_spiral, typeLineY + boxHeight + 1 + 6 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 12 * height_of_spiral);
                        g.fillRect(totalContentInset + innerContentWidth - 5 * height_of_spiral, typeLineY + boxHeight + 1 + 4 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 8 * height_of_spiral);
                        g.fillRect(totalContentInset + innerContentWidth - 3 * height_of_spiral, typeLineY + boxHeight + 1 + 2 * height_of_spiral, height_of_spiral, total_height_spiral - 1 - 4 * height_of_spiral);
                        g.fillRect(totalContentInset + innerContentWidth - 1 * height_of_spiral, typeLineY + boxHeight + 1 + 0 * height_of_spiral, height_of_spiral, total_height_spiral - 1);
                    }
                }
            } else {
                g.fillRect(
                        x, typeLineY + boxHeight,
                        innerContentWidth, cardHeight / 3 - boxHeight / 2);
            }
    }

    private void drawMainFrame(Graphics2D g, Paint borderPaint) {
        g.setPaint(borderPaint);
        g.drawRect(
                totalContentInset, totalContentInset,
                contentWidth - 1, cardHeight - borderWidth * 3 - totalContentInset - 1);
    }

    public void drawZendikarCurvedFace(Graphics2D g2, BufferedImage image, int x, int y, int x2, int y2,
                                       Color boxColor, Paint paint) {
        if (artImage == null) {
            return;
        }

        BufferedImage artToUse = artImage;
        int srcW = artToUse.getWidth();
        int srcH = artToUse.getHeight();

        // Get a box based on the standard scan from gatherer.
        // Width = 185/223 pixels (centered)
        // Height = 220/310, 38 pixels from top
        int subx = 19 * srcW / 223;
        int suby = 38 * srcH / 310;
        artToUse = artImage.getSubimage(subx, suby, 185 * srcW / 223, 220 * srcH / 310);

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
        if (artImage == null) {
            return;
        }
        BufferedImage artToUse = artImage;
        int srcW = artToUse.getWidth();
        int srcH = artToUse.getHeight();

        // Get a box based on the standard scan from gatherer.
        // Width = 185/223 pixels (centered)
        // Height = 220/310, 38 pixels from top
        int subx = 19 * srcW / 223;
        int suby = 38 * srcH / 310;
        artToUse = artImage.getSubimage(subx, suby, 185 * srcW / 223, 220 * srcH / 310);

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

    public void drawBorderlessCurves(Graphics2D g2, int w, int h, Color boxColor) {
        BufferedImage artToUse = artImage;

        int srcW = w;
        int srcH = h;
        if (artToUse != null) {
            srcW = artToUse.getWidth();
            srcH = artToUse.getHeight();
        }

        g2.setPaint(java.awt.Color.black);

        // Dimensions:  534 height, 384 width, 34 offset at top, 41 offset at bottom.  Curve at bottom right is from an ellipse: 245 high, 196 wide, with center offset from
        // right side by 36  (so top left is at: (width - 159, height - 41 -196)  center at: 41+127 = width - 36, height - 168)
        int scan_width = 384;
        int scan_height = 534;
        int scan_ew = 174;
        int scan_eh = 254;
        int offset_ew = 160;
        int offset_eh = 41;
        int middle_ew = 52;
        int middle_eh = 26;

        // Bottom left side arc
        int ex = (offset_ew - scan_ew) * w / scan_width;
        int ey = h - (offset_eh + scan_eh) * h / scan_height;
        int bot_ey = h - offset_eh * h / scan_height;
        int ew = scan_ew * w / scan_width;
        int eh = scan_eh * h / scan_height;
        int end_curve_ex = ex + ew / 2;

        Arc2D arc = new Arc2D.Double(ex, ey, ew, eh, 180, 90, Arc2D.OPEN);

        // Bottom right side arc
        ex = w - offset_ew * w / scan_width;
        ey = h - (offset_eh + scan_eh) * h / scan_height;
        bot_ey = h - offset_eh * h / scan_height;
        Arc2D arc2 = new Arc2D.Double(ex, ey, ew, eh, 270, 90, Arc2D.OPEN);

        // Middle bump.. 52x26
        int mid_ex = w / 2 - middle_ew * w / (scan_width * 2);
        int mid_ey = bot_ey - middle_eh * h / (scan_height * 2);
        int end_mid_ex = w / 2 + middle_ew * w / (scan_width * 2);

        Arc2D arc3 = new Arc2D.Double(mid_ex, mid_ey, middle_ew * w / scan_width, middle_eh * h / scan_height, 180, -180, Arc2D.OPEN);

        Path2D.Double curve = new Path2D.Double();
        curve.moveTo(0, 0);
        curve.lineTo(0, bot_ey);
        curve.append(arc, true);
        curve.lineTo(mid_ex, bot_ey);
        curve.append(arc3, true);
        curve.lineTo(w - ew / 2, bot_ey);
        curve.append(arc2, true);
        curve.lineTo(w, 0);
        curve.lineTo(0, 0);

        g2.setClip(curve);
        if (artToUse != null) {
            artToUse = artImage.getSubimage(0, 0, srcW, srcH);
            g2.drawImage(artToUse, 0, 0, w, h, null);
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

    public void drawCardAndBlackBorder(Graphics2D g2, int w, int h, Color boxColor) {
        BufferedImage artToUse = artImage;
        int srcW = w;
        int srcH = h;
        if (artToUse != null) {
            srcW = artToUse.getWidth();
            srcH = artToUse.getHeight();
        }

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
        Rectangle r = new Rectangle(borderWidth + contentInset, cardHeight - borderWidth * 6 - 1, cardWidth - borderWidth * 2 - contentInset * 2, borderWidth * 2);
        a.add(new Area(r));
        g2.setClip(a);
        if (artToUse != null) {
            artToUse = artImage.getSubimage(0, 0, srcW, srcH);
            g2.drawImage(artToUse, 0, 0, w, h, null);
        }
        g2.setClip(null);
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
        if (!baseName.isEmpty()) {
            AttributedString str = new AttributedString(baseName);
            str.addAttribute(TextAttribute.FONT, boxTextFont);
            TextMeasurer measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
            int breakIndex = measure.getLineBreakIndex(0, availableWidth);
            if (breakIndex < baseName.length()) {
                str = new AttributedString(baseName);
                str.addAttribute(TextAttribute.FONT, boxTextFontNarrow);
                measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
                breakIndex = measure.getLineBreakIndex(0, availableWidth);
            }
            if (breakIndex > 0) {
                TextLayout layout = measure.getLayout(0, breakIndex);
                g.setColor(Color.white);
                layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);
            }
        }

        // Draw the mana symbols
        if (!cardView.isAbility() && !cardView.isFaceDown()) {
            ManaSymbols.draw(g, manaCost, x + w - manaCostWidth, y + boxTextOffset, boxTextHeight, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
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
            types = types.replace("Token", "T.");
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
                g.setColor(Color.white);
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
        if (cardView.showPT()) {

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
            g.setColor(new Color(180, 180, 180));
            g.fillRect(
                    x + contentInset, curY - boxHeight - 1,
                    partBoxWidth - 2 * contentInset, 1);

            // Draw text
            Color defaultTextColor = new Color(180, 180, 180);
            boolean defaultTextLight = true;
            g.setFont(ptTextFont);

            // real PT info
            MageInt currentPower = cardView.getOriginalPower();
            MageInt currentToughness = cardView.getOriginalToughness();

            // draws
            int ptEmptySpace = (partBoxWidth - ptContentWidth) / 2;
            int ptPosStart1 = x + contentInset + ptEmptySpace;
            int ptPosStart2 = ptPosStart1 + ptTextWidth1 + ptDeviderSpace;
            int ptPosStart3 = ptPosStart2 + ptTextWidth2 + ptDeviderSpace;
            // p
            g.setColor(CardRendererUtils.getCardTextColor(currentPower, false, defaultTextColor, defaultTextLight));
            g.drawString(ptText1, ptPosStart1, curY - ptTextOffset - 1); // left
            // /
            g.setColor(defaultTextColor);
            g.drawString(ptText2, ptPosStart2, curY - ptTextOffset - 1); // center
            // t
            g.setColor(CardRendererUtils.getCardTextColor(currentToughness, CardRendererUtils.isCardWithDamage(cardView), defaultTextColor, defaultTextLight));
            g.drawString(ptText3, ptPosStart3, curY - ptTextOffset - 1); // right
            //
            g.setColor(defaultTextColor);

            // Advance
            curY -= boxHeight;
        }

        // Is it a walker? (But don't draw the box if it's a non-permanent view
        // of a walker without a starting loyalty (EG: Arlin Kord's flipped side).
        if (cardView.isPlaneswalker()
                && (cardView instanceof PermanentView || !cardView.getStartingLoyalty().equals("0"))) {
            // Draw the PW loyalty box
            int w = partBoxWidth;
            int h = partBoxWidth / 2;
            int x = cardWidth - partBoxWidth - borderWidth;
            int y = curY - h;

            Polygon symbol = new Polygon();
            symbol.addPoint(x + w / 2, y + h);
            symbol.addPoint((int) (x + w * 0.9), (int) (y + 0.8 * h));
            symbol.addPoint(x + w, y);
            symbol.addPoint((int) (x + w * 0.6), (int) (y - 0.2 * h));
            symbol.addPoint(x + w / 2, y);
            symbol.addPoint((int) (x + w * 0.4), (int) (y - 0.2 * h));
            symbol.addPoint(x, y);
            symbol.addPoint((int) (x + w * 0.1), (int) (y + 0.8 * h));

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

        // Is it a battle?
        if (cardView.isBattle()
                && (cardView instanceof PermanentView || !cardView.getStartingDefense().equals("0"))) {
            // Draw the PW loyalty box
            int w = 3 * partBoxWidth / 4;
            int h = 3 * partBoxWidth / 4;
            int x = cardWidth - w - borderWidth;
            int y = curY - h;

            Polygon symbol = new Polygon();
            symbol.addPoint(x + (0 * w) / 80, y + (2 * h) / 80);
            symbol.addPoint(x + (12 * w) / 80, y + (30 * h) / 80);
            symbol.addPoint(x + (3 * w) / 80, y + (40 * h) / 80);
            symbol.addPoint(x + (12 * w) / 80, y + (50 * h) / 80);
            symbol.addPoint(x + (0 * w) / 80, y + (78 * h) / 80);
            symbol.addPoint(x + (30 * w) / 80, y + (71 * h) / 80);
            symbol.addPoint(x + (40 * w) / 80, y + (80 * h) / 80);
            symbol.addPoint(x + (50 * w) / 80, y + (71 * h) / 80);
            symbol.addPoint(x + (80 * w) / 80, y + (78 * h) / 80);
            symbol.addPoint(x + (68 * w) / 80, y + (50 * h) / 80);
            symbol.addPoint(x + (77 * w) / 80, y + (40 * h) / 80);
            symbol.addPoint(x + (68 * w) / 80, y + (30 * h) / 80);
            symbol.addPoint(x + (80 * w) / 80, y + (2 * h) / 80);
            symbol.addPoint(x + (48 * w) / 80, y + (9 * h) / 80);
            symbol.addPoint(x + (40 * w) / 80, y + (0 * h) / 80);
            symbol.addPoint(x + (32 * w) / 80, y + (9 * h) / 80);


            // Draw + stroke
            g.setColor(Color.black);
            g.fillPolygon(symbol);
            g.setColor(new Color(200, 200, 200));
            g.setStroke(new BasicStroke(2));
            g.drawPolygon(symbol);
            g.setStroke(new BasicStroke(1));

            // Loyalty number
            String defense;
            if (cardView instanceof PermanentView) {
                defense = cardView.getDefense();
            } else {
                defense = cardView.getStartingDefense();
            }

            g.setFont(ptTextFont);
            g.setColor(Color.white);
            int defenseWidth = g.getFontMetrics().stringWidth(defense);
            g.drawString(defense, x + 1 + (w - defenseWidth) / 2, y - 1 + ptTextHeight + (h - ptTextHeight) / 2);

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

        // Basic mana draw mana symbol in textbox (for basic lands)
        if (!forceRules && (allRules.size() == 1 && (allRules.get(0) instanceof TextboxBasicManaRule) && cardView.isLand())) {
                int xPosOne = x + w / 2 - 9 * h / 8 + 1;
                int xPosTwo = x + w / 2 - h - h / 8;
                int radius = 9 * h / 4;
                int yPos = y - 3 * h / 4;
                if (allRules.size() == 1) {
                    // Size of mana symbol = 9/4 * h, 3/4h above line
                    if (allRules.get(0) instanceof TextboxBasicManaRule) {
                        drawBasicManaSymbol(g, xPosOne, yPos, radius, radius, ((TextboxBasicManaRule) allRules.get(0)).getBasicManaSymbol());
                    } else {
                        drawBasicManaSymbol(g, xPosTwo, yPos, radius, radius, cardView.getFrameColor().toString());
                    }
                } else {
                    if (allRules.size() > 1) {
                        drawBasicManaSymbol(g, xPosTwo, yPos, radius, radius, cardView.getFrameColor().toString());
                    }
                }
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
        ManaSymbols.draw(g, symbs, x + (w - manaCostWidth) / 2, y + (h - symbHeight) / 2, symbHeight, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
    }

    private void drawBasicManaSymbol(Graphics2D g, int x, int y, int w, int h, String symbol) {
        String symbs = symbol;
        if (getSizedManaSymbol(symbol) != null) {
            ManaSymbols.draw(g, symbs, x, y, w, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
        }
        if (symbol.length() == 2) {
            String symbs2 = "" + symbol.charAt(1) + symbol.charAt(0);
            if (getSizedManaSymbol(symbs2) != null) {
                ManaSymbols.draw(g, symbs2, x, y, w, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
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

    // Determine which background image to use from a set of colors
    // and the current card.
    protected static BufferedImage getBackgroundTexture(ObjectColor colors, Collection<CardType> types) {
        if (types.contains(CardType.LAND)) {
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
            Color lessOpaque = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() - 50);
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
