package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.ArtRect;
import mage.constants.CardType;
import mage.view.CardView;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Best-effort renderer which adapts any card to the futureshifted frame.
 *
 * It builds on the normal generated-card renderer so rules text, icons,
 * counters, overlays and unusual card data continue to work.
 */
public class FutureCardRenderer extends ModernCardRenderer {

    private static final float TYPE_LINE_Y_FRAC_FUTURE = 0.58f;
    private static final Color RAIL_SHADOW = new Color(0, 0, 0, 150);
    private static final Color INNER_HIGHLIGHT = new Color(255, 255, 255, 95);

    private int manaRailWidth;
    private int railRight;
    private int artX;
    private int artY;
    private int artWidth;
    private int artHeight;

    public FutureCardRenderer(CardView card) {
        super(card);
    }

    @Override
    protected void layout(int cardWidth, int cardHeight) {
        super.layout(cardWidth, cardHeight);
        manaRailWidth = Math.max(boxHeight, (int) (cardWidth * 0.135f));
        typeLineY = (int) (TYPE_LINE_Y_FRAC_FUTURE * cardHeight);
        railRight = totalContentInset + manaRailWidth;
        artX = totalContentInset;
        artY = totalContentInset + boxHeight;
        artWidth = cardWidth - totalContentInset - artX;
        artHeight = typeLineY - artY;
    }

    @Override
    protected void drawArt(Graphics2D g) {
        if (artImage == null || artWidth <= 0 || artHeight <= 0) {
            return;
        }
        Rectangle2D sourceRect = cardView.getArtRect() == null
                ? ArtRect.NORMAL.rect
                : cardView.getArtRect().rect;
        Graphics2D artGraphics = (Graphics2D) g.create();
        try {
            artGraphics.setClip(createArtWindow());
            drawArtIntoRect(artGraphics, artX, artY, artWidth, artHeight, sourceRect, true);
        } finally {
            artGraphics.dispose();
        }
    }

    @Override
    protected void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image,
                             boolean lessOpaqueRulesTextBox) {
        ObjectColor frameColors = getFrameObjectColor();
        Color boxColor = getBoxColor(frameColors, cardView.getCardTypes(), attribs.isTransformed);
        Paint borderPaint = getBorderPaint(frameColors, cardView.getCardTypes(), cardWidth);
        Paint textboxPaint = getTextboxPaint(frameColors, cardView.getCardTypes(), cardWidth,
                lessOpaqueRulesTextBox);

        int bottom = cardHeight - borderWidth * 3;
        int rulesY = typeLineY + boxHeight;
        int rulesHeight = Math.max(0, bottom - rulesY);

        g.setPaint(borderPaint);
        g.drawRect(totalContentInset, totalContentInset,
                contentWidth - 1, bottom - totalContentInset - 1);
        g.setPaint(textboxPaint);
        g.fillRect(totalContentInset + 1, rulesY,
                contentWidth - 2, rulesHeight);

        g.setPaint(borderPaint);
        g.setStroke(new BasicStroke(Math.max(1f, borderWidth * 0.55f)));
        g.draw(createArtWindow());
        g.setStroke(new BasicStroke(1));

        drawFutureRail(g, borderPaint, boxColor);

        int medallionSize = Math.max(10, boxHeight);
        int nameX = totalContentInset + medallionSize / 2;
        CardRendererUtils.drawRoundedBox(g,
                nameX, totalContentInset,
                cardWidth - borderWidth - nameX, boxHeight,
                contentInset, borderPaint, boxColor);
        CardRendererUtils.drawRoundedBox(g,
                borderWidth, typeLineY,
                cardWidth - 2 * borderWidth, boxHeight,
                contentInset, borderPaint, boxColor);

        drawNameLine(g, attribs, cardView.getDisplayName(), "",
                nameX + medallionSize / 2, totalContentInset,
                cardWidth - totalContentInset - nameX - medallionSize / 2, boxHeight);
        drawTypeLine(g, attribs, getCardTypeLine(),
                totalContentInset, typeLineY, contentWidth, boxHeight, true);
        drawFutureManaCost(g, boxColor, borderPaint);
        drawTypeMedallion(g, boxColor, borderPaint, medallionSize);

        g.setColor(RAIL_SHADOW);
        g.fillRect(totalContentInset + 1, rulesY, contentWidth - 2, 1);

        drawRulesText(g, textboxKeywords, textboxRules,
                totalContentInset + 3, rulesY + 3,
                contentWidth - 6, Math.max(0, rulesHeight - 5), false);
        drawBottomRight(g, attribs, borderPaint, boxColor);
    }

    private void drawFutureRail(Graphics2D g, Paint borderPaint, Color boxColor) {
        int left = totalContentInset;
        int top = artY;
        int bottom = typeLineY;

        Path2D.Float rail = new Path2D.Float();
        rail.moveTo(left, top);
        rail.lineTo(getManaCurveTopX(), top);
        appendManaCurve(rail);
        rail.lineTo(left, bottom);
        rail.closePath();
        g.setPaint(borderPaint);
        g.fill(rail);
        g.setColor(RAIL_SHADOW);
        g.draw(rail);

        int inset = Math.max(1, borderWidth / 2);
        Path2D.Float coloredSweep = new Path2D.Float();
        coloredSweep.moveTo(left + inset, top + inset);
        coloredSweep.lineTo(getManaCurveTopX() - inset, top + inset);
        coloredSweep.curveTo(
                getManaCurveControl1X() - inset, top + artHeight * 0.17f,
                getManaCurveControl2X() - inset, top + artHeight * 0.76f,
                getManaCurveBottomX() - inset, bottom - inset);
        coloredSweep.lineTo(left + inset, bottom - inset);
        coloredSweep.closePath();
        g.setColor(new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), 205));
        g.fill(coloredSweep);
        g.setColor(INNER_HIGHLIGHT);
        g.draw(coloredSweep);

        // A second highlight along the inside edge gives the characteristic
        // layered, metallic crescent visible on futureshifted frames.
        Path2D.Float innerHighlight = new Path2D.Float();
        innerHighlight.moveTo(getManaCurveTopX() - inset * 2, top + inset);
        innerHighlight.curveTo(
                getManaCurveControl1X() - inset * 2, top + artHeight * 0.17f,
                getManaCurveControl2X() - inset * 2, top + artHeight * 0.76f,
                getManaCurveBottomX() - inset * 2, bottom - inset);
        g.setStroke(new BasicStroke(Math.max(1f, borderWidth * 0.35f)));
        g.draw(innerHighlight);
        g.setStroke(new BasicStroke(1));
    }

    private Shape createArtWindow() {
        Path2D.Float path = new Path2D.Float();
        path.moveTo(getManaCurveTopX(), artY);
        path.lineTo(cardWidth - totalContentInset, artY);
        path.lineTo(cardWidth - totalContentInset, typeLineY);
        path.lineTo(getManaCurveBottomX(), typeLineY);
        path.curveTo(
                getManaCurveControl2X(), artY + artHeight * 0.76f,
                getManaCurveControl1X(), artY + artHeight * 0.17f,
                getManaCurveTopX(), artY);
        path.closePath();
        return path;
    }

    private void appendManaCurve(Path2D.Float path) {
        path.curveTo(
                getManaCurveControl1X(), artY + artHeight * 0.17f,
                getManaCurveControl2X(), artY + artHeight * 0.76f,
                getManaCurveBottomX(), typeLineY);
    }

    private int getManaCurveTopX() {
        return railRight + Math.max(2, boxHeight / 2);
    }

    private int getManaCurveControl1X() {
        return totalContentInset + Math.max(2, manaRailWidth / 4);
    }

    private int getManaCurveControl2X() {
        return totalContentInset + Math.max(2, manaRailWidth / 3);
    }

    private int getManaCurveBottomX() {
        return railRight;
    }

    private float getManaCurveX(float t) {
        float inverse = 1f - t;
        return inverse * inverse * inverse * getManaCurveTopX()
                + 3f * inverse * inverse * t * getManaCurveControl1X()
                + 3f * inverse * t * t * getManaCurveControl2X()
                + t * t * t * getManaCurveBottomX();
    }

    private void drawTypeMedallion(Graphics2D g, Color boxColor, Paint borderPaint, int size) {
        int x = totalContentInset - size / 4;
        int y = totalContentInset;
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        int unit = Math.max(1, size / 8);

        g.setPaint(borderPaint);
        g.fillOval(x - 1, y - 1, size + 2, size + 2);
        g.setColor(new Color(
                Math.min(255, boxColor.getRed() + 18),
                Math.min(255, boxColor.getGreen() + 18),
                Math.min(255, boxColor.getBlue() + 18)));
        g.fillOval(x, y, size, size);
        g.setColor(new Color(35, 35, 35, 220));
        g.setStroke(new BasicStroke(Math.max(1f, size / 14f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (cardView.getCardTypes().contains(CardType.LAND)) {
            Polygon mountain = new Polygon(
                    new int[]{centerX - 3 * unit, centerX - unit, centerX, centerX + unit, centerX + 3 * unit},
                    new int[]{centerY + 2 * unit, centerY - unit, centerY, centerY - 2 * unit, centerY + 2 * unit}, 5);
            g.drawPolyline(mountain.xpoints, mountain.ypoints, mountain.npoints);
        } else if (cardView.getCardTypes().contains(CardType.CREATURE)) {
            g.fillOval(centerX - 2 * unit, centerY, 4 * unit, 3 * unit);
            g.fillOval(centerX - 3 * unit, centerY - 2 * unit, 2 * unit, 2 * unit);
            g.fillOval(centerX - unit, centerY - 3 * unit, 2 * unit, 2 * unit);
            g.fillOval(centerX + unit, centerY - 2 * unit, 2 * unit, 2 * unit);
        } else if (cardView.getCardTypes().contains(CardType.ARTIFACT)) {
            g.drawLine(centerX - 2 * unit, centerY - 2 * unit, centerX + 2 * unit, centerY - 2 * unit);
            g.drawArc(centerX - 2 * unit, centerY - 2 * unit, 4 * unit, 4 * unit, 180, 180);
            g.drawLine(centerX, centerY, centerX, centerY + 2 * unit);
            g.drawLine(centerX - 2 * unit, centerY + 2 * unit, centerX + 2 * unit, centerY + 2 * unit);
        } else if (cardView.getCardTypes().contains(CardType.ENCHANTMENT)) {
            Polygon star = new Polygon(
                    new int[]{centerX, centerX + unit, centerX + 3 * unit, centerX + unit, centerX,
                            centerX - unit, centerX - 3 * unit, centerX - unit},
                    new int[]{centerY - 3 * unit, centerY - unit, centerY, centerY + unit, centerY + 3 * unit,
                            centerY + unit, centerY, centerY - unit}, 8);
            g.drawPolygon(star);
        } else if (cardView.getCardTypes().contains(CardType.INSTANT)) {
            Polygon bolt = new Polygon(
                    new int[]{centerX + unit, centerX - unit, centerX, centerX - 2 * unit, centerX},
                    new int[]{centerY - 3 * unit, centerY, centerY, centerY + 3 * unit, centerY + unit}, 5);
            g.fillPolygon(bolt);
        } else if (cardView.getCardTypes().contains(CardType.SORCERY)) {
            g.drawOval(centerX - 2 * unit, centerY - 2 * unit, 4 * unit, 4 * unit);
            for (int i = 0; i < 8; i++) {
                double angle = i * Math.PI / 4;
                g.drawLine(centerX + (int) (2.5 * unit * Math.cos(angle)),
                        centerY + (int) (2.5 * unit * Math.sin(angle)),
                        centerX + (int) (3.3 * unit * Math.cos(angle)),
                        centerY + (int) (3.3 * unit * Math.sin(angle)));
            }
        } else if (cardView.getCardTypes().contains(CardType.PLANESWALKER)) {
            g.drawLine(centerX, centerY - 3 * unit, centerX, centerY + 3 * unit);
            g.drawLine(centerX, centerY - unit, centerX - 2 * unit, centerY - 2 * unit);
            g.drawLine(centerX, centerY, centerX + 2 * unit, centerY - 2 * unit);
        } else if (cardView.getCardTypes().contains(CardType.BATTLE)) {
            g.drawRect(centerX - 2 * unit, centerY - 3 * unit, 4 * unit, 5 * unit);
            g.drawLine(centerX - 2 * unit, centerY - unit, centerX, centerY + 3 * unit);
            g.drawLine(centerX + 2 * unit, centerY - unit, centerX, centerY + 3 * unit);
        } else {
            g.drawOval(centerX - 2 * unit, centerY - 2 * unit, 4 * unit, 4 * unit);
            g.drawLine(centerX, centerY - unit, centerX, centerY + unit);
            g.fillOval(centerX - unit / 2, centerY + 2 * unit, Math.max(1, unit), Math.max(1, unit));
        }
        g.setStroke(new BasicStroke(1));
    }

    private void drawFutureManaCost(Graphics2D g, Color boxColor, Paint borderPaint) {
        if (cardView.isAbility() || cardView.isFaceDown() || manaCostString.trim().isEmpty()) {
            return;
        }

        String[] symbols = manaCostString.trim().split("\\s+");
        int diameter = Math.max(10, Math.min(boxHeight, manaRailWidth - 3));
        int gap = Math.max(1, diameter / 7);
        int available = Math.max(diameter, artHeight - 4);
        int step = diameter + gap;
        if (symbols.length > 1 && symbols.length * step > available) {
            step = Math.max(3, (available - diameter) / (symbols.length - 1));
        }

        int y = artY + 2;
        Object oldAntialias = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (String symbol : symbols) {
            if (symbol.isEmpty() || y + diameter > typeLineY - 1) {
                break;
            }
            float symbolCenterY = y + diameter / 2f;
            float curveProgress = Math.max(0f, Math.min(1f,
                    (symbolCenterY - artY) / Math.max(1f, artHeight)));
            int x = Math.round(getManaCurveX(curveProgress) - diameter * 0.72f);

            g.setPaint(borderPaint);
            g.fillOval(x - 1, y - 1, diameter + 2, diameter + 2);
            g.setColor(boxColor);
            g.fillOval(x, y, diameter, diameter);
            BufferedImage mana = ManaSymbols.getSizedManaSymbol(symbol, Math.max(8, diameter - 3));
            if (mana != null) {
                int mx = x + (diameter - mana.getWidth()) / 2;
                int my = y + (diameter - mana.getHeight()) / 2;
                g.drawImage(mana, mx, my, null);
            } else {
                g.setColor(MANA_ICONS_TEXT_COLOR);
                g.setFont(boxTextFontNarrow);
                int textWidth = g.getFontMetrics().stringWidth(symbol);
                g.drawString(symbol, x + (diameter - textWidth) / 2,
                        y + diameter - Math.max(2, diameter / 5));
            }
            y += step;
        }

        if (oldAntialias != null) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntialias);
        }
    }
}


