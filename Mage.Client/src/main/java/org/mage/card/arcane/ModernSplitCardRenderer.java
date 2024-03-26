package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.ArtRect;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.view.CardView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author StravantUser
 */
public class ModernSplitCardRenderer extends ModernCardRenderer {

    public static final Color ADVENTURE_BOX_WHITE = new Color(135, 122, 103);
    public static final Color ADVENTURE_BOX_BLUE = new Color(2, 96, 131);
    public static final Color ADVENTURE_BOX_BLACK = new Color(52, 44, 46);
    public static final Color ADVENTURE_BOX_RED = new Color(126, 61, 42);
    public static final Color ADVENTURE_BOX_GREEN = new Color(9, 51, 30);
    public static final Color ADVENTURE_BOX_GOLD = new Color(118, 92, 42);
    public static final Color ADVENTURE_BOX_COLORLESS = new Color(131, 133, 135);

    static String RULES_MARK_FUSE = "Fuse";
    static String RULES_MARK_AFTERMATH = "Aftermath";

    private static class HalfCardProps {

        int x, y, w, h, cw, ch;

        String name;
        String typeLineString;
        String manaCostString;
        ObjectColor color;
        ArrayList<TextboxRule> rules = new ArrayList<>();
        ArrayList<TextboxRule> keywords = new ArrayList<>();
    }

    private static final List<CardType> ONLY_LAND_TYPE = Arrays.asList(CardType.LAND);

    // Right and left halves of the card content
    private HalfCardProps rightHalf = new HalfCardProps();
    private HalfCardProps leftHalf = new HalfCardProps();

    // Where and how big is the divider between the card halves
    private int dividerAt;
    private int dividerSize;

    // Is fuse / aftermath
    private boolean isFuse = false;
    private boolean isAftermath = false;

    private static String trimAdventure(String rule) {
        if (rule.startsWith("Adventure")) {
            return rule.substring(rule.lastIndexOf("&mdash;") + 8);
        }
        return rule;
    }

    public ModernSplitCardRenderer(CardView view) {
        super(view);

        rightHalf.manaCostString = ManaSymbols.getClearManaCost(cardView.getRightSplitCostsStr());
        leftHalf.manaCostString = ManaSymbols.getClearManaCost(cardView.getLeftSplitCostsStr());

        rightHalf.color = new ObjectColor(cardView.getRightSplitCostsStr());
        leftHalf.color = new ObjectColor(cardView.getLeftSplitCostsStr());

        if (isAdventure()) {
            List<String> trimmedRules = new ArrayList<>();
            for (String rule : view.getRightSplitRules()) {
                trimmedRules.add(trimAdventure(rule));
            }
            parseRules(trimmedRules, rightHalf.keywords, rightHalf.rules);
        } else {
            parseRules(view.getRightSplitRules(), rightHalf.keywords, rightHalf.rules);
        }
        parseRules(view.getLeftSplitRules(), leftHalf.keywords, leftHalf.rules);

        rightHalf.typeLineString = cardView.getRightSplitTypeLine();
        leftHalf.typeLineString = cardView.getLeftSplitTypeLine();

        rightHalf.name = cardView.getRightSplitName();
        leftHalf.name = cardView.getLeftSplitName();

        isFuse = view.getRules().stream().anyMatch(rule -> rule.contains(RULES_MARK_FUSE));
        isAftermath = view.getRightSplitRules().stream().anyMatch(rule -> rule.contains(RULES_MARK_AFTERMATH));

        // It's easier for rendering to swap the card halves here because for aftermath cards
        // they "rotate" in opposite directions making consquence and normal split cards
        // have the "right" vs "left" as the top half.
        // Adventures are treated differently and not rotated at all.
        if (isAdventure()) {
            manaCostString = leftHalf.manaCostString;
            textboxKeywords = leftHalf.keywords;
            textboxRules = leftHalf.rules;
        } else if (!isAftermath()) {
            HalfCardProps tmp = leftHalf;
            leftHalf = rightHalf;
            rightHalf = tmp;
        }
    }

    private boolean isAftermath() {
        return isAftermath;
    }

    private boolean isFuse() {
        return isFuse;
    }

    @Override
    protected void layout(int cardWidth, int cardHeight) {
        // Pass to parent
        super.layout(cardWidth, cardHeight);

        // Decide size of divider
        if (isAftermath()) {
            dividerSize = borderWidth;
            dividerAt = (int) (cardHeight * 0.54);
        } else {
            int availHeight = cardHeight - totalContentInset - 3 * borderWidth;
            dividerSize = borderWidth * 2;
            dividerAt = (int) (totalContentInset + availHeight * 0.5 - borderWidth);
        }

        // Decide size of each halves box
        rightHalf.x = leftHalf.x = totalContentInset;
        rightHalf.w = leftHalf.w = cardWidth - 2 * totalContentInset;
        leftHalf.y = totalContentInset;
        leftHalf.h = dividerAt - totalContentInset;
        rightHalf.y = dividerAt + dividerSize;
        rightHalf.h = cardHeight - rightHalf.y - borderWidth * 3;

        // Content width / height (Exchanged from width / height if the card part is rotated)
        if (isAftermath()) {
            leftHalf.cw = leftHalf.w;
            leftHalf.ch = leftHalf.h;
        } else {
            leftHalf.cw = leftHalf.h;
            leftHalf.ch = leftHalf.w;
        }
        rightHalf.cw = rightHalf.h;
        rightHalf.ch = rightHalf.w;

        // Fuse space
        if (isFuse()) {
            rightHalf.ch -= boxHeight;
            leftHalf.ch -= boxHeight;
        }
    }

    @Override
    protected void drawBackground(Graphics2D g) {
        if (cardView.isFaceDown()) {
            drawCardBackTexture(g);
        } if (isAdventure()) {
            super.drawBackground(g);
        } else {
            { // Left half background (top of the card)
                // Set texture to paint the left with
                g.setPaint(getBackgroundPaint(leftHalf.color, cardView.getCardTypes(), cardView.getSubTypes()));

                // Draw main part (most of card)
                g.fillRoundRect(
                        borderWidth, borderWidth,
                        cardWidth - 2 * borderWidth, leftHalf.h + contentInset - borderWidth - 2 * cornerRadius + (cornerRadius - 1),
                        cornerRadius - 1, cornerRadius - 1);

                // Draw the M15 rounded "swoosh" at the bottom
                g.fillRoundRect(
                        borderWidth, dividerAt - borderWidth - 4 * cornerRadius,
                        cardWidth - 2 * borderWidth, cornerRadius * 4,
                        cornerRadius * 2, cornerRadius * 2);

                // Draw the cutout into the "swoosh" for the textbox to lie over
                g.fillRect(
                        borderWidth + contentInset, dividerAt - 2 * borderWidth,
                        cardWidth - borderWidth * 2 - contentInset * 2, borderWidth * 2);
            }

            { // Right half background (bottom half of the card)
                // Set texture to paint the right with
                g.setPaint(getBackgroundPaint(rightHalf.color, cardView.getCardTypes(), cardView.getSubTypes()));

                // Draw the M15 rounded "swoosh"es at the top and bottom
                g.fillRoundRect(
                        borderWidth, dividerAt + dividerSize + borderWidth,
                        cardWidth - 2 * borderWidth, rightHalf.h - 2 * borderWidth,
                        cornerRadius * 2, cornerRadius * 2);

                // Draw the cutout into the "swoosh" for the textbox to lie over
                g.fillRect(
                        borderWidth + contentInset, dividerAt + dividerSize,
                        cardWidth - borderWidth * 2 - contentInset * 2, rightHalf.h);
            }
        }
    }

    @Override
    protected void drawArt(Graphics2D g) {
        if (isAdventure) {
            super.drawArt(g);
        } else if (artImage != null) {
            if (isAftermath()) {
                Rectangle2D topRect = ArtRect.AFTERMATH_TOP.rect;
                int topLineY = (int) (leftHalf.ch * TYPE_LINE_Y_FRAC);
                drawArtIntoRect(g,
                        leftHalf.x, leftHalf.y + boxHeight, leftHalf.cw, topLineY - boxHeight,
                        topRect, false);

                Rectangle2D bottomRect = ArtRect.AFTERMATH_BOTTOM.rect;
                int bottomLineY = (rightHalf.ch - boxHeight) / 2;
                drawArtIntoRect(g,
                        rightHalf.x + rightHalf.w - bottomLineY, rightHalf.y, bottomLineY - boxHeight, rightHalf.h,
                        bottomRect, false);

            } else {
                // NOTE: Art rects are reversed here, that is on purpose because we swap the left / right half
                // of split cards for rendering for consistency between aftermath and normal split
                Rectangle2D topRect = ArtRect.SPLIT_RIGHT.rect;
                int topLineY = (int) (leftHalf.ch * TYPE_LINE_Y_FRAC);
                drawArtIntoRect(g,
                        leftHalf.x + boxHeight, leftHalf.y, topLineY - boxHeight, leftHalf.h,
                        topRect, false);

                Rectangle2D bottomRect = ArtRect.SPLIT_LEFT.rect;
                int bottomLineY = (int) (rightHalf.ch * TYPE_LINE_Y_FRAC);
                drawArtIntoRect(g,
                        rightHalf.x + boxHeight, rightHalf.y, bottomLineY - boxHeight, rightHalf.h,
                        bottomRect, false);
            }
        }
    }

    protected void drawSplitHalfFrame(Graphics2D g, CardPanelAttributes attribs, HalfCardProps half, int typeLineY) {
        // Get the border paint
        Color boxColor = getBoxColor(half.color, cardView.getCardTypes(), attribs.isTransformed);
        Paint textboxPaint = getTextboxPaint(half.color, cardView.getCardTypes(), cardWidth, false);
        Paint borderPaint = getBorderPaint(half.color, cardView.getCardTypes(), cardWidth);

        // Draw main frame
        g.setPaint(borderPaint);
        g.drawRect(
                0, 0,
                half.cw - 1, half.ch - 1);

        // Background of textbox
        g.setPaint(textboxPaint);
        g.fillRect(
                1, typeLineY,
                half.cw - 2, half.ch - typeLineY - 1);

        // Draw the name line box
        CardRendererUtils.drawRoundedBox(g,
                -borderWidth, 0,
                half.cw + 2 * borderWidth, boxHeight,
                contentInset,
                borderPaint, boxColor);

        // Draw the type line box
        CardRendererUtils.drawRoundedBox(g,
                -borderWidth, typeLineY,
                half.cw + 2 * borderWidth, boxHeight - 4,
                contentInset,
                borderPaint, boxColor);

        // Draw the name line
        drawNameLine(g, attribs, half.name, half.manaCostString,
                0, 0,
                half.cw, boxHeight);

        // Draw the type line
        drawTypeLine(g, attribs, half.typeLineString,
                0, typeLineY,
                half.cw, boxHeight - 4, true);

        // Draw the textbox rules
        drawRulesText(g, half.keywords, half.rules,
                2, typeLineY + boxHeight + 2 - 4,
                half.cw - 4, half.ch - typeLineY - boxHeight, false);
    }

    private Graphics2D getUnmodifiedHalfContext(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(leftHalf.x, leftHalf.y);
        return g2;
    }

    private Graphics2D getAftermathHalfContext(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(rightHalf.x, rightHalf.y);
        g2.rotate(Math.PI / 2);
        g2.translate(0, -rightHalf.w);
        return g2;
    }

    private Graphics2D getLeftHalfContext(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(leftHalf.x, leftHalf.y);
        g2.rotate(-Math.PI / 2);
        g2.translate(-leftHalf.cw, 0);
        return g2;
    }

    private Graphics2D getRightHalfContext(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(rightHalf.x, rightHalf.y);
        g2.rotate(-Math.PI / 2);
        g2.translate(-rightHalf.cw, 0);
        return g2;
    }

    @Override
    protected void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image, boolean lessOpaqueRulesTextBox) {
        if (isAdventure()) {
            super.drawFrame(g, attribs, image, lessOpaqueRulesTextBox);

            CardPanelAttributes adventureAttribs = new CardPanelAttributes(
                    attribs.cardWidth, attribs.cardHeight, attribs.isChoosable,
                    attribs.isSelected, true);

            // Draw the adventure name line box
            g.setPaint(getBoxColor(rightHalf.color, cardView.getCardTypes(), true));
            g.fillRect(totalContentInset, typeLineY + boxHeight + 1,
                    contentWidth / 2 - 1, boxHeight - 2);

            // Draw the adventure type line box
            g.setPaint(getAdventureBoxColor(rightHalf.color));
            g.fillRect(totalContentInset , typeLineY + boxHeight * 2 - 1,
                    contentWidth / 2 - 1, boxHeight - 2);

            // Draw the adventure text box
            g.setPaint(getTextboxPaint(rightHalf.color, cardView.getCardTypes(), cardWidth, lessOpaqueRulesTextBox));
            g.fillRect(totalContentInset, typeLineY + boxHeight * 3 - 3,
                    contentWidth / 2 - 1, cardHeight - borderWidth * 3 - typeLineY - boxHeight * 3 + 2);

            // Draw the adventure name line
            drawNameLine(g, adventureAttribs, rightHalf.name, rightHalf.manaCostString,
                    totalContentInset + 2, typeLineY + boxHeight,
                    contentWidth / 2 - 8, boxHeight - 2);

            // Draw the adventure type line
            drawTypeLine(g, adventureAttribs, rightHalf.typeLineString,
                    totalContentInset + 2, typeLineY + boxHeight * 2 - 2,
                    contentWidth / 2 - 8, boxHeight - 2, true);

            // Draw the adventure textbox rules
            drawRulesText(g, rightHalf.keywords, rightHalf.rules,
                    totalContentInset + 3, typeLineY + boxHeight * 3 - 1,
                    contentWidth / 2 - 8, cardHeight - borderWidth * 3 - typeLineY - boxHeight * 3 + 2, false);
        } else if (isAftermath()) {
            drawSplitHalfFrame(getUnmodifiedHalfContext(g), attribs, leftHalf, (int) (leftHalf.ch * TYPE_LINE_Y_FRAC));
            drawSplitHalfFrame(getAftermathHalfContext(g), attribs, rightHalf, (rightHalf.ch - boxHeight) / 2);
        } else {
            drawSplitHalfFrame(getLeftHalfContext(g), attribs, leftHalf, (int) (leftHalf.ch * TYPE_LINE_Y_FRAC));
            drawSplitHalfFrame(getRightHalfContext(g), attribs, rightHalf, (int) (rightHalf.ch * TYPE_LINE_Y_FRAC));
            if (isFuse()) {
                Graphics2D g2 = getRightHalfContext(g);
                int totalFuseBoxWidth = rightHalf.cw * 2 + 2 * borderWidth + dividerSize;
                Paint boxColor = getTextboxPaint(cardView.getColor(), ONLY_LAND_TYPE, totalFuseBoxWidth, false);
                Paint borderPaint = getBorderPaint(cardView.getColor(), ONLY_LAND_TYPE, totalFuseBoxWidth);
                CardRendererUtils.drawRoundedBox(g2,
                        -borderWidth, rightHalf.ch,
                        totalFuseBoxWidth, boxHeight,
                        contentInset,
                        borderPaint, boxColor);
                drawNameLine(g2, attribs, SplitCard.FUSE_RULE, "",
                        0, rightHalf.ch,
                        totalFuseBoxWidth - 2 * borderWidth, boxHeight);
            }
        }
    }

    protected Color getAdventureBoxColor(ObjectColor colors) {
        if (colors.isMulticolored()) {
            return ADVENTURE_BOX_GOLD;
        } else if (colors.isColorless()) {
            return ADVENTURE_BOX_COLORLESS;
        } else if (colors.isWhite()) {
            return ADVENTURE_BOX_WHITE;
        } else if (colors.isBlue()) {
            return ADVENTURE_BOX_BLUE;
        } else if (colors.isBlack()) {
            return ADVENTURE_BOX_BLACK;
        } else if (colors.isRed()) {
            return ADVENTURE_BOX_RED;
        } else if (colors.isGreen()) {
            return ADVENTURE_BOX_GREEN;
        } else {
            return ERROR_COLOR;
        }
    }
}
