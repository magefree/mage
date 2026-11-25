package org.mage.card.arcane;

import mage.ObjectColor;
import mage.cards.ArtRect;
import mage.constants.CardType;
import mage.view.CardView;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
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
 *
 * @author DoingOutstanding, JayDi85
 */
public class FutureCardRenderer extends ModernCardRenderer {

    private static final float TYPE_LINE_Y_FRAC_FUTURE = 0.58f;
    private static final int MANA_SLOTS = 6;
    private static final Color RAIL_SHADOW = new Color(0, 0, 0, 150);
    private static final Color INNER_HIGHLIGHT = new Color(255, 255, 255, 95);

    private int manaRailWidth;
    private int railRight;
    private int artY;
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
        artY = totalContentInset + boxHeight;
        artHeight = typeLineY - artY;
    }

    @Override
    protected void drawArt(Graphics2D g) {
        if (artImage == null || contentWidth <= 0 || artHeight <= 0) {
            return;
        }
        Rectangle2D sourceRect = cardView.getArtRect() == null
                ? ArtRect.NORMAL.rect
                : cardView.getArtRect().rect;
        Graphics2D artGraphics = (Graphics2D) g.create();
        try {
            artGraphics.setClip(createArtWindow());
            drawArtIntoRect(artGraphics, totalContentInset, artY, contentWidth, artHeight, sourceRect, true);
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

        Color railColor = frameColors.getColorCount() == 1 ? getBorderColor(frameColors) : boxColor;
        Color barColor = new Color((railColor.getRed() + boxColor.getRed()) / 2,
                (railColor.getGreen() + boxColor.getGreen()) / 2,
                (railColor.getBlue() + boxColor.getBlue()) / 2);

        int left = totalContentInset;
        int right = cardWidth - left;
        int bottom = cardHeight - borderWidth * 3;
        int rulesY = typeLineY + boxHeight;
        int rulesHeight = Math.max(0, bottom - rulesY);
        int panelInset = borderWidth / 2;
        int panelLeft = left + panelInset;
        int panelRight = right - panelInset;
        int nameLeft = left + boxHeight * 2 / 3;

        Path2D.Float rulesBox = new Path2D.Float();
        rulesBox.moveTo(panelLeft, rulesY);
        rulesBox.lineTo(panelRight, rulesY);
        rulesBox.quadTo(right + borderWidth, rulesY + rulesHeight / 2f, panelRight, bottom);
        rulesBox.lineTo(panelLeft, bottom);
        rulesBox.quadTo(left - borderWidth, rulesY + rulesHeight / 2f, panelLeft, rulesY);
        rulesBox.closePath();
        drawInsetPanel(g, rulesBox, textboxPaint);

        // The left edge of the name bar continues into the outer bevel of the mana rail.
        Path2D.Float nameCurve = new Path2D.Float();
        nameCurve.moveTo(nameLeft, artY);
        nameCurve.quadTo(nameLeft + boxHeight / 2f, left, nameLeft + boxHeight * 1.5f, left);
        drawFutureRail(g, railColor, boxColor, borderPaint, nameLeft, nameCurve);

        Path2D.Float nameBar = new Path2D.Float(nameCurve);
        nameBar.lineTo(right - borderWidth, left);
        nameBar.quadTo(right, left, right, left + borderWidth);
        nameBar.lineTo(right, artY);
        nameBar.closePath();
        drawInsetPanel(g, nameBar, getBarPaint(barColor, left));

        Path2D.Float typeBar = new Path2D.Float();
        typeBar.moveTo(left, typeLineY);
        typeBar.lineTo(right, typeLineY);
        typeBar.lineTo(panelRight, rulesY);
        typeBar.lineTo(panelLeft, rulesY);
        typeBar.closePath();
        drawInsetPanel(g, typeBar, getBarPaint(barColor, typeLineY));

        int nameTextX = nameLeft + boxHeight / 2;
        drawNameLine(g, attribs, cardView.getDisplayName(), "",
                nameTextX, left, right - 1 - nameTextX, boxHeight);
        drawTypeLine(g, attribs, getCardTypeLine(),
                panelLeft + 2, typeLineY, panelRight - panelLeft - 4, boxHeight, true);
        drawTypeMedallion(g, boxColor, borderPaint, Math.max(10, boxHeight));
        drawRulesText(g, textboxKeywords, textboxRules,
                panelLeft + 2, rulesY + 3,
                panelRight - panelLeft - 4, Math.max(0, rulesHeight - 5), false);
        drawBottomRight(g, attribs, borderPaint, boxColor);
    }

    private Paint getBarPaint(Color color, int top) {
        return new GradientPaint(0, top, color.darker(), 0, top + boxHeight / 3f, color);
    }

    private void drawInsetPanel(Graphics2D g, Shape panel, Paint fill) {
        int bevel = Math.max(1, borderWidth / 4);
        g.setStroke(new BasicStroke(bevel));
        g.translate(0, bevel);
        g.setColor(INNER_HIGHLIGHT);
        g.draw(panel);
        g.translate(0, -bevel);
        g.setPaint(fill);
        g.fill(panel);
        g.setColor(RAIL_SHADOW);
        g.draw(panel);
        g.setStroke(new BasicStroke(1));
    }

    private void drawFutureRail(Graphics2D g, Color railColor, Color boxColor, Paint borderPaint, int nameLeft, Path2D nameCurve) {
        Path2D.Float outerEdge = new Path2D.Float();
        outerEdge.moveTo(totalContentInset, typeLineY);
        outerEdge.curveTo(borderWidth, artY + artHeight * 0.76f,
                borderWidth / 2f, artY + artHeight * 0.30f,
                nameLeft, artY);
        outerEdge.append(nameCurve, true);

        Path2D.Float rail = new Path2D.Float(outerEdge);
        rail.lineTo(getManaCurveTopX(), artY);
        appendManaCurve(rail);
        rail.closePath();
        g.setPaint(new GradientPaint(borderWidth, artY, railColor, railRight + manaRailWidth, artY, boxColor));
        g.fill(rail);

        // Bevel only the outside. The art itself defines the edge beside the mana sockets.
        g.setStroke(new BasicStroke(Math.max(2f, borderWidth * 0.8f)));
        g.setPaint(borderPaint);
        g.draw(outerEdge);
        g.setStroke(new BasicStroke(Math.max(1f, borderWidth * 0.28f)));
        g.setColor(INNER_HIGHLIGHT);
        g.draw(outerEdge);
        g.setStroke(new BasicStroke(1));
        drawFutureManaCost(g, railColor, boxColor, rail);
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

    private void drawManaSocket(Graphics2D g, int x, int y, int diameter, Color railColor, Color boxColor) {
        int bevel = Math.max(1, diameter / 10);
        // Light strikes the raised lip from the upper left and the recessed face from the lower right.
        g.setColor(RAIL_SHADOW);
        g.fillOval(x, y, diameter + 2 * bevel, diameter + 2 * bevel);
        g.setPaint(new GradientPaint(x, y, Color.WHITE, x + diameter, y + diameter, railColor.darker()));
        g.fillOval(x - bevel, y - bevel, diameter + 2 * bevel, diameter + 2 * bevel);
        g.setPaint(new RadialGradientPaint(x + diameter * 0.55f, y + diameter * 0.77f, diameter * 0.8f,
                new float[]{0f, 0.65f, 1f}, new Color[]{boxColor, railColor, railColor.darker().darker()}));
        g.fillOval(x, y, diameter, diameter);
    }

    private void drawFutureManaCost(Graphics2D g, Color railColor, Color boxColor, Shape rail) {
        if (cardView.isAbility() || cardView.isFaceDown()) {
            return;
        }

        String cost = manaCostString.trim();
        String[] symbols = cost.isEmpty() ? new String[0] : cost.split("\\s+");
        int slots = Math.max(MANA_SLOTS, symbols.length);
        float step = (float) artHeight / slots;
        int diameter = Math.min(Math.min(boxHeight, manaRailWidth - 3), (int) step - 2);
        if (diameter < 1) {
            return;
        }

        Shape oldClip = g.getClip();
        Object oldAntialias = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < slots; i++) {
            float progress = (i + 0.5f) / slots;
            int x = Math.round(getManaCurveX(progress) - diameter * 0.55f);
            int y = artY + Math.round(artHeight * progress - diameter / 2f);
            if (i == symbols.length) {
                // The art covers the inner half of each unused socket.
                g.clip(rail);
            }
            drawManaSocket(g, x, y, diameter, railColor, boxColor);
            if (i >= symbols.length) {
                continue;
            }

            String symbol = symbols[i];
            BufferedImage mana = ManaSymbols.getSizedManaSymbol(symbol, Math.max(1, diameter - 3));
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
        }

        g.setClip(oldClip);
        if (oldAntialias != null) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntialias);
        }
    }
}
