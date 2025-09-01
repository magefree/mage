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

import static org.mage.card.arcane.ModernCardResourceLoader.*;

/**
 * @author stravant@gmail.com, JayDi85, Jmlundeen
 * <p>
 * Base rendering class for old border cards
 */
public class RetroCardRenderer extends CardRenderer {

    public static final Color MANA_ICONS_TEXT_COLOR = Color.DARK_GRAY; // text color of missing mana icons in IMAGE render mode

    public static final BufferedImage BG_IMG_WHITE = loadBackgroundImage("white_retro");
    public static final BufferedImage BG_IMG_BLUE = loadBackgroundImage("blue_retro");
    public static final BufferedImage BG_IMG_BLACK = loadBackgroundImage("black_retro");
    public static final BufferedImage BG_IMG_RED = loadBackgroundImage("red_retro");
    public static final BufferedImage BG_IMG_GREEN = loadBackgroundImage("green_retro");
    public static final BufferedImage BG_IMG_GOLD = loadBackgroundImage("gold_retro");
    public static final BufferedImage BG_IMG_ARTIFACT = loadBackgroundImage("artifact_retro");
    public static final BufferedImage BG_IMG_LAND = loadBackgroundImage("land_retro");
    public static final BufferedImage BG_IMG_COLORLESS = loadBackgroundImage("colorless_retro");


    public static final Color BORDER_WHITE = new Color(168, 159, 156);
    public static final Color BORDER_BLUE = new Color(29, 107, 124);
    public static final Color BORDER_BLACK = new Color(77, 71, 73);
    public static final Color BORDER_RED = new Color(200, 71, 58);
    public static final Color BORDER_GREEN = new Color(91, 136, 62, 255);
    public static final Color BORDER_GOLD = new Color(244, 172, 65);
    public static final Color BORDER_COLORLESS = new Color(208, 212, 212);
    public static final Color BORDER_LAND = new Color(190, 173, 115);

    public static final Color BOX_WHITE = new Color(248, 247, 245);
    public static final Color BOX_BLUE = new Color(200, 226, 235);
    public static final Color BOX_BLACK = new Color(230, 191, 134);
    public static final Color BOX_RED = new Color(203, 141, 117);
    public static final Color BOX_GREEN = new Color(234, 187, 134);
    public static final Color BOX_GOLD = new Color(154, 142, 145);
    public static final Color BOX_COLORLESS = new Color(190, 183, 178);
    public static final Color BOX_LAND = new Color(220, 215, 213);
    public static final Color BOX_INVENTION = new Color(209, 97, 33);
    public static final Color BOX_VEHICLE = new Color(155, 105, 60);

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

    public static final Color TEXTBOX_WHITE = new Color(248, 247, 245);
    public static final Color TEXTBOX_BLUE = new Color(200, 226, 235);
    public static final Color TEXTBOX_BLACK = new Color(230, 191, 134);
    public static final Color TEXTBOX_RED = new Color(203, 141, 117);
    public static final Color TEXTBOX_GREEN = new Color(234, 187, 134);
    public static final Color TEXTBOX_GOLD = new Color(154, 142, 145);
    public static final Color TEXTBOX_COLORLESS = new Color(190, 183, 178);
    public static final Color TEXTBOX_LAND = new Color(211, 151, 92, 255);

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

    // dimensions of art / text box
    protected int innerContentWidth;
    protected int artHeight;

    // How tall the name / type lines and P/T box are
    protected static final float BOX_HEIGHT_FRAC = 0.051f; // x cardHeight
    protected static final float PT_BOX_HEIGHT_FRAC = 0.065f; // x cardHeight
    protected static final int BOX_HEIGHT_MIN = 8;
    protected int boxHeight;
    protected int ptBoxHeight;

    // How far down the card is the type line placed?
    protected static final float TYPE_LINE_Y_FRAC = 0.525f; // x cardHeight
    protected int typeLineY;

    // The left and right frame inset
    protected static final float INSET_WIDTH_FRAC = .055f;
    protected int insetWidth;

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

    // Inset frame colors
    protected Color frameTopRightColor;
    protected Color frameBottomLeftColor;

    public RetroCardRenderer(CardView card) {
        // Pass off to parent
        super(card);

        // Mana cost string
        manaCostString = ManaSymbols.getClearManaCost(cardView.getManaCostStr());
    }

    @Override
    protected void layout(int cardWidth, int cardHeight) {
        // Pass to parent
        super.layout(cardWidth, cardHeight);

        borderWidth = (int) Math.max(
                BORDER_WIDTH_MIN,
                0.048 * cardWidth);

        frameInset = (int) Math.max(
                BORDER_WIDTH_MIN,
                0.012 * cardWidth);

        insetWidth = (int) Math.max(
                BORDER_WIDTH_MIN,
                INSET_WIDTH_FRAC * cardWidth);

        // Content inset, just equal to border width
        contentInset = borderWidth + insetWidth;

        // Total content inset helper
        totalContentInset = borderWidth + contentInset;

        // Content width
        contentWidth = cardWidth - 2 * totalContentInset;

        // Box height
        boxHeight = (int) Math.max(
                BOX_HEIGHT_MIN,
                BOX_HEIGHT_FRAC * cardHeight);
        ptBoxHeight = (int) Math.max(
                BOX_HEIGHT_MIN * 2,
                PT_BOX_HEIGHT_FRAC * cardHeight);

        // Type line at
        typeLineY = (int) (TYPE_LINE_Y_FRAC * cardHeight);

        // Art / text box size
        innerContentWidth = (int) (cardWidth * .8f);
        if (innerContentWidth < 160) {
            innerContentWidth += 2;
        }
        artHeight = typeLineY - (borderWidth + boxHeight);

        // Box text height
        boxTextHeight = getTextHeightForBoxHeight(boxHeight);
        boxTextOffset = (boxHeight - boxTextHeight) / 2;
        boxTextFont = new Font("Arial", Font.PLAIN, boxTextHeight);
        boxTextFontNarrow = new Font("Arial Narrow", Font.PLAIN, boxTextHeight);

        // Box text height
        ptTextHeight = getPTTextHeightForLineHeight(ptBoxHeight);
        ptTextOffset = (ptBoxHeight - ptTextHeight) / 2;
        ptTextFont = new Font("Arial", Font.BOLD, ptTextHeight);

        // Inset Frame Colors
        frameTopRightColor = getFrameColor(true);
        frameBottomLeftColor = getFrameColor(false);
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

            boolean shouldPreserveAspect = true;
            Rectangle2D sourceRect = ArtRect.RETRO.rect;
            if (cardView.getFrameStyle() != FrameStyle.RETRO) {
                sourceRect = new Rectangle2D.Double(sourceRect.getX(), sourceRect.getY() + .01, sourceRect.getWidth(), sourceRect.getHeight());
            }

            if (cardView.getMageObjectType() == MageObjectType.SPELL) {
                ArtRect rect = cardView.getArtRect();
                if (rect != ArtRect.NORMAL) {
                    sourceRect = rect.rect;
                }
            }

            // Normal drawing of art from a source part of the card frame into the rect
            drawArtIntoRect(g,
                    contentInset + frameInset, borderWidth + boxHeight + frameInset,
                    innerContentWidth - frameInset * 2, artHeight - frameInset * 2,
                    sourceRect, shouldPreserveAspect);

        }
    }

    @Override
    protected void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image, boolean lessOpaqueRulesTextBox) {
        // Get the card colors to base the frame on
        ObjectColor frameColors = getFrameObjectColor();

        // Get the border paint
        Color boxColor = getBoxColor(frameColors, cardView.getCardTypes());
        Paint textboxPaint = getTextboxPaint(frameColors, cardView.getCardTypes(), cardWidth, lessOpaqueRulesTextBox);
        Paint borderPaint = getBorderPaint(frameColors, cardView.getCardTypes(), cardWidth);

        // Special colors
        if (cardView.getFrameStyle() == FrameStyle.KLD_INVENTION) {
            boxColor = BOX_INVENTION;
        }

        // Draw the textbox fill
        drawTextboxBackground(g, textboxPaint, frameColors, borderPaint, isOriginalDualLand());

        drawInsetFrame(g, contentInset, borderWidth + boxHeight,
                innerContentWidth, artHeight, borderPaint, cardView.getCardTypes().contains(CardType.LAND));

        drawTypeLine(g, attribs, getCardTypeLine(),
                contentInset, typeLineY,
                innerContentWidth, boxHeight);

        // Draw the transform circle
        int nameOffset = drawTransformationCircle(g, attribs, borderPaint);

        // Draw the name line
        drawNameLine(g, attribs, cardView.getDisplayName(), manaCostString,
                contentInset + nameOffset, borderWidth,
                innerContentWidth);

        // Draw the textbox rules
        drawRulesText(g, textboxKeywords, textboxRules,
                contentInset + 2, typeLineY + boxHeight + 2,
                innerContentWidth - 4, (int) ((cardHeight - borderWidth * 2) * 0.32f));

        // Draw the bottom right stuff
        drawBottomRight(g, attribs, borderPaint, boxColor);
    }

    private void drawInsetFrame(Graphics2D g2, int x, int y, int width, int height, Paint borderPaint, boolean isLand) {

        // Outer and inner bounds
        int x1 = x + width;
        int y1 = y + height;

        int xi0 = x + frameInset;
        int yi0 = y + frameInset;
        int xi1 = x + width - frameInset;
        int yi1 = y + height - frameInset;

        // Colors for visual effect (you can customize this)
        Color topColor = frameTopRightColor;
        Color leftColor = frameBottomLeftColor;

        // Top trapezoid
        Path2D top = new Path2D.Double();
        top.moveTo(x, y);
        top.lineTo(x1, y);
        top.lineTo(xi1, yi0);
        top.lineTo(xi0, yi0);
        top.closePath();

        // Left trapezoid
        Path2D left = new Path2D.Double();
        left.moveTo(x, y);
        left.lineTo(xi0, yi0);
        left.lineTo(xi0, yi1);
        left.lineTo(x, y1);
        left.closePath();

        // Right trapezoid
        Path2D right = new Path2D.Double();
        right.moveTo(x1, y);
        right.lineTo(x1, y1);
        right.lineTo(xi1, yi1);
        right.lineTo(xi1, yi0);
        right.closePath();

        // Bottom trapezoid
        Path2D bottom = new Path2D.Double();
        bottom.moveTo(x, y1);
        bottom.lineTo(x1, y1);
        bottom.lineTo(xi1, yi1);
        bottom.lineTo(xi0, yi1);
        bottom.closePath();

        if (isLand) {
            g2.draw(top);
            g2.draw(left);
            g2.draw(right);
            g2.draw(bottom);
            if (cardView.getFrameColor().getColorCount() > 1) {
                g2.setColor(BORDER_LAND);
            } else {
                g2.setPaint(borderPaint);
            }
        } else {
            g2.setColor(topColor);
            g2.fill(top);
            g2.fill(right);
            g2.setColor(leftColor);
            g2.fill(left);
            g2.fill(bottom);
            g2.setColor(CardRendererUtils.abitdarker(topColor));
        }
        x = x - 1;
        y = y - 1;
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRect(x, y, x1 - x, y1 - y);
        g2.setStroke(new BasicStroke(1));
    }

    private void drawTextboxBackground(Graphics2D g, Paint textboxPaint, ObjectColor frameColors, Paint borderPaint, boolean isOriginalDual) {
        g.setPaint(textboxPaint);
        int x = contentInset;
        int backgroundHeight = (int) ((cardHeight - borderWidth * 2) * 0.33f);
        if (cardView.getCardTypes().contains(CardType.LAND)) {

            // Analysis of LEA Duals (Scrubland) gives 16.5 height of unit of 'spirals' in the text area
            int height_of_spiral = (int) ((backgroundHeight - frameInset * 2) * 0.06);

            List<ObjectColor> twoColors = frameColors.getColors();
            g.setPaint(borderPaint);
            g.fillRect(x, typeLineY + boxHeight + 1, innerContentWidth, backgroundHeight);
            g.setColor(Color.black);
            g.drawRect(x, typeLineY + boxHeight + 1, innerContentWidth, backgroundHeight);
            g.setPaint(textboxPaint);

            if (twoColors.size() <= 2) {
                if (isOriginalDual && twoColors.size() == 2) {
                    g.setPaint(getSpiralLandTextboxColor(twoColors.get(0), twoColors.get(1), false));
                }
                g.fillRect(x + frameInset, typeLineY + boxHeight + 1 + frameInset, innerContentWidth - frameInset * 2, backgroundHeight - frameInset * 2);
                g.setColor(Color.black);
                g.drawRect(x + frameInset, typeLineY + boxHeight + 1 + frameInset, innerContentWidth - frameInset * 2, backgroundHeight - frameInset * 2);

            }
            if (frameColors.getColorCount() >= 3) {
                g.fillRect(x, typeLineY + boxHeight + 1, innerContentWidth - 4, backgroundHeight);
            }
            if (frameColors.getColorCount() == 2) {
                if (isOriginalDual) {
                    for (int i = 0; i < 8; i++) {
                        int offset = height_of_spiral * i;
                        int inset = frameInset + offset;
                        int width = innerContentWidth - frameInset * 2 - height_of_spiral * i * 2;
                        int height = backgroundHeight - frameInset * 2 - height_of_spiral * i * 2;

                        boolean useFirstColor = (i % 2 == 0);
                        g.setPaint(getSpiralLandTextboxColor(twoColors.get(0), twoColors.get(1), useFirstColor));
                        g.fillRect(
                                x + inset,
                                typeLineY + boxHeight + 1 + inset,
                                width,
                                height
                        );
                    }
                }
            }
        } else {
            g.fillRect(
                    x, typeLineY + boxHeight,
                    innerContentWidth, backgroundHeight);
            g.setColor(Color.black);
            g.drawRect(x, typeLineY + boxHeight,
                    innerContentWidth, backgroundHeight);
        }
    }

    // Draw the name line
    protected void drawNameLine(Graphics2D g, CardPanelAttributes attribs, String baseName, String manaCost, int x, int y, int w) {
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
                int drawY = y + boxTextOffset + boxTextHeight - 1;

                // Draw main text
                g.setColor(getBoxTextColor(attribs));
                layout.draw(g, x, drawY);
            }
        }

        // Draw the mana symbols
        if (!cardView.isAbility() && !cardView.isFaceDown()) {
            ManaSymbols.draw(g, manaCost, x + w - manaCostWidth, y + boxTextOffset, boxTextHeight, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
        }
    }

    // Draw the type line (color indicator, types, and expansion symbol)
    protected void drawTypeLine(Graphics2D g, CardPanelAttributes attribs, String baseTypeLine, int x, int y, int w, int h) {
        // Draw expansion symbol
        int expansionSymbolWidth = 0;
        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_SET_SYMBOL, "false").equals("false")) {
            expansionSymbolWidth = drawExpansionSymbol(g, x, y, w, h);
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
                g.setColor(getBoxTextColor(attribs));
                layout.draw(g, x, y + (float) (h - boxTextHeight) / 2 + boxTextHeight - 1);
            }
        }
    }

    // Draw the P/T and/or Loyalty boxes
    protected void drawBottomRight(Graphics2D g, CardPanelAttributes attribs, Paint borderPaint, Color fill) {
        // No bottom right for abilities
        if (cardView.isAbility()) {
            return;
        }

        int contentInset = borderWidth - frameInset;

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
                    x, curY - ptBoxHeight,
                    partBoxWidth, ptBoxHeight,
                    contentInset,
                    borderPaint,
                    isVehicle ? BOX_VEHICLE : fill);

            // Draw text
            Color defaultTextColor = Color.black;
            boolean defaultTextLight = cardView.getColor().isMulticolored() || cardView.getColor().equals(ObjectColor.RED)
                    || cardView.getColor().equals(ObjectColor.COLORLESS);
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
            curY -= ptBoxHeight;
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
            int y = curY - ptBoxHeight;
            String damage = String.valueOf(((PermanentView) cardView).getDamage());
            g.setFont(ptTextFont);
            int txWidth = g.getFontMetrics().stringWidth(damage);
            g.setColor(Color.red);
            g.fillRect(x, y, partBoxWidth, ptBoxHeight);
            g.setColor(Color.white);
            g.drawRect(x, y, partBoxWidth, ptBoxHeight);
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

    protected void drawRulesText(Graphics2D g, List<TextboxRule> keywords, List<TextboxRule> rules, int x, int y, int w, int h) {
        // Gather all rules to render
        List<TextboxRule> allRules = new ArrayList<>(rules);

        // Lands have an inset frame and need to adjust their x and y
        if (cardView.getCardTypes().contains(CardType.LAND)) {
            x = x + frameInset;
            y = y + frameInset;
            h = h - frameInset;
            w = w - frameInset;
        }

        // Add the keyword rule if there are any keywords
        if (!keywords.isEmpty()) {
            String keywordRulesString = getKeywordRulesString(keywords);
            TextboxRule keywordsRule = new TextboxRule(keywordRulesString, new ArrayList<>());
            allRules.add(0, keywordsRule);
        }

        // Basic mana draw mana symbol in textbox (for basic lands)
        if ((allRules.size() == 1 && (allRules.get(0) instanceof TextboxBasicManaRule) && cardView.isLand())) {
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
        ManaSymbols.draw(g, symbs, x + (w - manaCostWidth) / 2, y + (h - symbHeight) / 2, symbHeight, RetroCardRenderer.MANA_ICONS_TEXT_COLOR, 2);
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
            int symbolX = x - borderWidth - frameInset;
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
                if (y + advance + 1 <=  yPos) {
                    g.fillRect(x + 2, y + advance + 1, w - 2, 1);
                }
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
        int y = totalContentInset / 2 + frameInset;
        int height = (boxHeight - boxTextHeight) / 2 + boxTextHeight - 1;
        if (isTransformCard(attribs)) {
            transformCircleOffset = height - contentInset * 2;
            g.setPaint(borderPaint);
            g.drawOval(borderWidth, y, height - 1, height - 1);
            g.setColor(Color.black);
            g.fillOval(borderWidth + 1, y + 1, height - 2, height - 2);
            g.setColor(Color.white);
            if (attribs.isTransformed) {
                g.fillArc(borderWidth + 3, y + 3, height - 6, height - 6, 90, 270);
                g.setColor(Color.black);
                g.fillArc(borderWidth + 3 + 3, y + 3, height - 6 - 3, height - 6, 90, 270);
            } else {
                g.fillOval(borderWidth + 3, y + 3, height - 6, height - 6);
            }
        }
        return transformCircleOffset;
    }

    // Get the text height for a given box height
    protected static int getTextHeightForBoxHeight(int h) {
        return Math.max(10, (int) Math.ceil(.95 * h));
    }

    protected static int getPTTextHeightForLineHeight(int h) {
        return h - 4;
    }

    protected Color getFrameColor(boolean isTop) {
        ObjectColor color = cardView.getColor();
        if (color.isMulticolored()) {
            return isTop ? new Color(118, 98, 43) : new Color(228, 221, 182);
        } else if (color.isBlack()) {
            return isTop ? new Color(34, 34, 34) : new Color(122, 121, 113);
        } else if (color.isWhite()) {
            return isTop ? new Color(187, 171, 144) : new Color(253, 247, 226);
        } else if (color.isBlue()) {
            return isTop ? new Color(40, 112, 110) : new Color(146, 202, 219);
        } else if (color.isGreen()) {
            return isTop ? new Color(59, 79, 46) : new Color(122, 154, 106);
        } else if (color.isRed()) {
            return isTop ? new Color(117, 57, 25) : new Color(245, 146, 107);
        } else if (cardView.getCardTypes().contains(CardType.ARTIFACT)) {
            return isTop ? new Color(85, 68, 32) : new Color(152, 124, 107);
        } else if (cardView.getCardTypes().contains(CardType.LAND)) {
            return isTop ? new Color(73, 55, 30) : new Color(140, 107, 52);
        } else {
            return isTop ? new Color(139, 130, 130) : new Color(165, 165, 169);
        }
    }

    // Determine the color of the name / type line text
    protected Color getBoxTextColor(CardPanelAttributes attribs) {
        if (!cardView.getColor().isMulticolored() && cardView.getColor().isWhite()) {
            return Color.black;
        } else if (attribs.isTransformed) {
            return Color.white;
        } else if (cardView.isAbility()) {
            return Color.white;
        } else {
            return Color.white;
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
        } else if (types.contains(CardType.ARTIFACT)) {
            return BG_IMG_ARTIFACT;
        } else {
            // Colorless
            return BG_IMG_COLORLESS;
        }
    }
    // Get the box color for the given colors
    protected Color getBoxColor(ObjectColor colors, Collection<CardType> types) {
        if (cardView.isAbility()) {
            return Color.BLACK;
        } else if (colors.getColorCount() == 2 && types.contains(CardType.LAND)) {
            // Special case for two color lands. Boxes should be normal land colored
            // rather than multicolor. Three or greater color lands use a multi-color
            // box as normal.
            return BOX_LAND;
        } else if (colors.isMulticolored()) {
            return BOX_GOLD;
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return BOX_LAND;
            } else {
                return BOX_COLORLESS;
            }
        } else if (colors.isWhite()) {
            return BOX_WHITE;
        } else if (colors.isBlue()) {
            return BOX_BLUE;
        } else if (colors.isBlack()) {
            return BOX_BLACK;
        } else if (colors.isRed()) {
            return BOX_RED;
        } else if (colors.isGreen()) {
            return BOX_GREEN;
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
            if (colors.getColorCount() == 2 && types.contains(CardType.LAND)) {
                List<ObjectColor> twoColors = colors.getColors();
                Color[] translatedColors;
                translatedColors = new Color[]{
                        getLessOpaqueColor(getLandTextboxColor(twoColors.get(0)), lessOpaqueRulesTextBox),
                        getLessOpaqueColor(getLandTextboxColor(twoColors.get(1)), lessOpaqueRulesTextBox)
                };
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
