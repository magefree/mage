package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.ArtRect;
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

    private static class HalfCardProps {

        int x, y, w, h, cw, ch;

        String name;
        String typeLineString;
        String manaCostString;
        ObjectColor color;
        List<TextboxRule> rules = new ArrayList<>();
        List<TextboxRule> keywords = new ArrayList<>();
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

    public ModernSplitCardRenderer(CardView view) {
        super(view);

        rightHalf.manaCostString = ManaSymbols.getClearManaCost(cardView.getRightSplitCostsStr());
        leftHalf.manaCostString = ManaSymbols.getClearManaCost(cardView.getLeftSplitCostsStr());

        rightHalf.color = new ObjectColor(cardView.getRightSplitCostsStr());
        leftHalf.color = new ObjectColor(cardView.getLeftSplitCostsStr());

        parseRules(view.getRightSplitRules(), rightHalf.keywords, rightHalf.rules);
        parseRules(view.getLeftSplitRules(), leftHalf.keywords, leftHalf.rules);

        rightHalf.typeLineString = cardView.getRightSplitTypeLine();
        leftHalf.typeLineString = cardView.getLeftSplitTypeLine();

        rightHalf.name = cardView.getRightSplitName();
        leftHalf.name = cardView.getLeftSplitName();

        isFuse = view.getRules().stream().anyMatch(rule -> rule.contains("Fuse"));
        isAftermath = view.getRightSplitRules().stream().anyMatch(rule -> rule.contains("Aftermath"));

        // It's easier for rendering to swap the card halves here because for aftermath cards
        // they "rotate" in opposite directions making consquence and normal split cards
        // have the "right" vs "left" as the top half.
        if (!isAftermath()) {
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
            drawCardBack(g);
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
        if (artImage != null && !cardView.isFaceDown()) {
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
        if (isAftermath()) {
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
                drawNameLine(g2, attribs, "Fuse (You may cast both halves from your hand)", "",
                        0, rightHalf.ch,
                        totalFuseBoxWidth - 2 * borderWidth, boxHeight);
            }
        }
    }
}
