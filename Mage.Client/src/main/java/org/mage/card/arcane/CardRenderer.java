package org.mage.card.arcane;

import mage.abilities.hint.HintUtils;
import mage.cards.ArtRect;
import mage.client.dialog.PreferencesDialog;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stravant@gmail.com
 * <p>
 * Common base class for card renderers for each card frame / card type.
 * <p>
 * Follows the template method pattern to implement a new renderer, implement
 * the following methods (they are called in the following order):
 * <p>
 * * drawBorder() Draws the outermost border of the card, white border or black
 * border
 * <p>
 * * drawBackground() Draws the background texture / color of the card
 * <p>
 * * drawArt() Draws the card's art
 * <p>
 * * drawFrame() Draws the card frame (over the art and background)
 * <p>
 * * drawOverlays() Draws summoning sickness and possible other overlays
 * <p>
 * * drawCounters() Draws counters on the card, such as +1/+1 and -1/-1
 * counters
 * <p>
 * Predefined methods that the implementations can use:
 * <p>
 * * drawRules(font, bounding box)
 * <p>
 * * drawNameLine(font, bounding box)
 * <p>
 * * drawTypeLine(font, bounding box)
 */
public abstract class CardRenderer {

    ///////////////////////////////////////////////////////////////////////////
    // Common layout metrics between all cards
    // The card to be rendered
    protected final CardView cardView;

    // The card image
    protected BufferedImage artImage;

    // The face card image
    protected BufferedImage faceArtImage;

    ///////////////////////////////////////////////////////////////////////////
    // Common layout metrics between all cards
    // Polygons for counters
    private static final Polygon PLUS_COUNTER_POLY = new Polygon(new int[]{
            0, 5, 10, 10, 5, 0
    }, new int[]{
            3, 0, 3, 10, 9, 10
    }, 6);
    private static final Polygon MINUS_COUNTER_POLY = new Polygon(new int[]{
            0, 5, 10, 10, 5, 0
    }, new int[]{
            0, 1, 0, 7, 10, 7
    }, 6);
    private static final Polygon TIME_COUNTER_POLY = new Polygon(new int[]{
            0, 10, 8, 10, 0, 2
    }, new int[]{
            0, 0, 5, 10, 10, 5
    }, 6);
    private static final Polygon OTHER_COUNTER_POLY = new Polygon(new int[]{
            1, 9, 9, 1
    }, new int[]{
            1, 1, 9, 9
    }, 4);

    // Paint for a card back
    public static final Paint BG_TEXTURE_CARDBACK = new Color(153, 102, 51);

    // The size of the card
    protected int cardWidth;
    protected int cardHeight;

    // Is it selectable / selected
    protected boolean isChoosable;
    protected boolean isSelected;

    // Radius of the corners of the cards
    protected static final float CORNER_RADIUS_FRAC = 0.1f; //x cardWidth
    protected static final int CORNER_RADIUS_MIN = 3;
    protected int cornerRadius;

    // The inset of the actual card from the black / white border around it
    protected static final float BORDER_WIDTH_FRAC = 0.03f; //x cardWidth
    protected static final float BORDER_WIDTH_MIN = 2;
    protected int borderWidth;

    // The parsed text of the card
    protected final ArrayList<TextboxRule> textboxRules = new ArrayList<>();
    protected final ArrayList<TextboxRule> textboxKeywords = new ArrayList<>();

    // The Construtor
    // The constructor should prepare all of the things that it can
    // without knowing the dimensions that the card will be rendered at.
    // Then, the CardRenderer can be called on multiple times to render the
    // card at various sizes (for instance, during animation)
    public CardRenderer(CardView card) {
        // Set base parameters
        this.cardView = card;
        parseRules(card.getRules(), textboxKeywords, textboxRules);
    }

    protected void parseRules(List<String> stringRules, List<TextboxRule> keywords, List<TextboxRule> rules) {
        // Translate the textbox text and remove card hints
        for (String rule : stringRules) {
            // remove all card hints
            if (rule.equals(HintUtils.HINT_START_MARK)) {
                break;
            }

            // workaround to use real split card names
            String realCardName = cardView.getDisplayName();
            if (cardView.isSplitCard()) {
                for (String partRule : cardView.getLeftSplitRules()) {
                    if (partRule.equals(rule)) {
                        realCardName = cardView.getLeftSplitName();
                        break;
                    }
                }
                for (String partRule : cardView.getRightSplitRules()) {
                    if (partRule.equals(rule)) {
                        realCardName = cardView.getRightSplitName();
                        break;
                    }
                }
            }

            // Kill reminder text
            if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_REMINDER_TEXT, "false").equals("false")) {
                rule = CardRendererUtils.killReminderText(rule).trim();
            }

            if (!rule.isEmpty()) {
                TextboxRule tbRule = TextboxRuleParser.parse(cardView, rule, realCardName);
                if (tbRule.type == TextboxRuleType.SIMPLE_KEYWORD) {
                    keywords.add(tbRule);
                } else if (tbRule.text.isEmpty()) {
                    // Nothing to do, rule is empty
                } else {
                    rules.add(tbRule);
                }
            }
        }
    }

    private static int getBorderWidth(int cardWidth) {
        return (int) Math.max(
                BORDER_WIDTH_MIN,
                BORDER_WIDTH_FRAC * cardWidth);
    }

    // Layout operation
    // Calculate common layout metrics that will be used by several
    // of the operations in the template method.
    protected void layout(int cardWidth, int cardHeight) {
        // Store the dimensions for the template methods to use
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;

        // Corner radius and border width
        cornerRadius = (int) Math.max(
                CORNER_RADIUS_MIN,
                CORNER_RADIUS_FRAC * cardWidth);

        borderWidth = getBorderWidth(cardWidth);
    }

    /**
     * How far does a card have to be spaced down from a rendered card to show
     * it's entire name line? This function is a bit of a hack, as different
     * card faces need slightly different spacing, but we need it in a static
     * context so that spacing is consistent in GY / deck views etc.
     *
     * @param cardWidth
     * @return
     */
    public static int getCardTopHeight(int cardWidth) {
        // Constants copied over from ModernCardRenderer and tweaked
        float BOX_HEIGHT_FRAC = 0.065f; // x cardHeight
        int BOX_HEIGHT_MIN = 16;
        int boxHeight = (int) Math.max(
                BOX_HEIGHT_MIN,
                BOX_HEIGHT_FRAC * cardWidth * 1.4f);
        int borderWidth = getBorderWidth(cardWidth);
        return 2 * borderWidth + boxHeight;
    }

    // The Draw Method
    // The draw method takes the information caculated by the constructor
    // and uses it to draw to a concrete size of card and graphics.
    public void draw(Graphics2D g, CardPanelAttributes attribs, BufferedImage image) {

        // Pre template method layout, to calculate shared layout info
        layout(attribs.cardWidth, attribs.cardHeight);
        isSelected = attribs.isSelected;
        isChoosable = attribs.isChoosable;

        // Call the template methods
        drawBorder(g);
        drawBackground(g);
        lessOpaqueRulesTextBox = false;
        drawArt(g);
        drawFrame(g, attribs, image, lessOpaqueRulesTextBox);
        if (!cardView.isAbility()) {
            drawOverlays(g);
            drawCounters(g);
        }
    }

    // Template methods to be implemented by sub classes
    // For instance, for the Modern vs Old border card frames
    protected abstract void drawBorder(Graphics2D g);

    protected abstract void drawBackground(Graphics2D g);

    protected abstract void drawArt(Graphics2D g);

    protected abstract void drawFrame(Graphics2D g, CardPanelAttributes attribs, BufferedImage image, boolean lessOpaqueRulesTextBox);

    // Template methods that are possible to override, but unlikely to be
    // overridden.
    // Draw the card back
    protected void drawCardBack(Graphics2D g) {
        g.setPaint(BG_TEXTURE_CARDBACK);
        g.fillRect(borderWidth, borderWidth,
                cardWidth - 2 * borderWidth, cardHeight - 2 * borderWidth);
    }

    // Draw summoning sickness overlay, and possibly other overlays
    protected void drawOverlays(Graphics2D g) {
        if (cardView.isCreature() && cardView instanceof PermanentView) {
            if (((PermanentView) cardView).hasSummoningSickness()) {
                int x1 = (int) (0.2 * cardWidth);
                int x2 = (int) (0.8 * cardWidth);
                int y1 = (int) (0.2 * cardHeight);
                int y2 = (int) (0.8 * cardHeight);
                int[] xPoints = {
                        x1, x2, x1, x2
                };
                int[] yPoints = {
                        y1, y1, y2, y2
                };
                g.setColor(new Color(255, 255, 255, 200));
                g.setStroke(new BasicStroke(7));
                g.drawPolygon(xPoints, yPoints, 4);
                g.setColor(new Color(0, 0, 0, 200));
                g.setStroke(new BasicStroke(5));
                g.drawPolygon(xPoints, yPoints, 4);
                g.setStroke(new BasicStroke(1));
                int[] xPoints2 = {
                        x1, x2, cardWidth / 2
                };
                int[] yPoints2 = {
                        y1, y1, cardHeight / 2
                };
                g.setColor(new Color(0, 0, 0, 100));
                g.fillPolygon(xPoints2, yPoints2, 3);
            }
        }
    }

    protected void drawArtIntoRect(Graphics2D g, int x, int y, int w, int h, Rectangle2D artRect, boolean shouldPreserveAspect) {
        // Perform a process to make sure that the art is scaled uniformly to fill the frame, cutting
        // off the minimum amount necessary to make it completely fill the frame without "squashing" it.
        double fullCardImgWidth = artImage.getWidth();
        double fullCardImgHeight = artImage.getHeight();
        double artWidth = artRect.getWidth() * fullCardImgWidth;
        double artHeight = artRect.getHeight() * fullCardImgHeight;
        double targetWidth = w;
        double targetHeight = h;
        double targetAspect = targetWidth / targetHeight;
        if (!shouldPreserveAspect) {
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
            g.drawImage(subImg,
                    x, y,
                    (int) targetWidth, (int) targetHeight,
                    null);
        } catch (RasterFormatException e) {
            // At very small card sizes we may encounter a problem with rounding error making the rect not fit
        }
    }

    private boolean lessOpaqueRulesTextBox = false;
    protected void drawFaceArtIntoRect(Graphics2D g, int x, int y, int w, int h, int alternate_h, Rectangle2D artRect, boolean shouldPreserveAspect) {
        // Perform a process to make sure that the art is scaled uniformly to fill the frame, cutting
        // off the minimum amount necessary to make it completely fill the frame without "squashing" it.
        double fullCardImgWidth = faceArtImage.getWidth();
        double fullCardImgHeight = faceArtImage.getHeight();
        double artWidth = fullCardImgWidth;
        double artHeight = fullCardImgHeight;
        double targetWidth = w;
        double targetHeight = h;
        double targetAspect = targetWidth / targetHeight;
        if (!shouldPreserveAspect) {
            // No adjustment to art
        } else if (targetAspect * artHeight < artWidth) {
            // Trim off some width
            artWidth = targetAspect * artHeight;
        } else {
            // Trim off some height
            artHeight = artWidth / targetAspect;
        }
        try {
            /*BufferedImage subImg
                    = faceArtImage.getSubimage(
                    (int) (artRect.getX() * fullCardImgWidth), (int) (artRect.getY() * fullCardImgHeight),
                    (int) artWidth, (int) artHeight);*/
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHints(rh);
            if (fullCardImgWidth > fullCardImgHeight) {
                g.drawImage(faceArtImage,
                        x, y,
                        (int) targetWidth, (int) targetHeight,
                        null);
            } else {
                g.drawImage(faceArtImage,
                        x, y,
                        (int) targetWidth, alternate_h, // alernate_h is roughly (targetWidth / 0.74)
                        null);
                lessOpaqueRulesTextBox = true;
            }
        } catch (RasterFormatException e) {
            // At very small card sizes we may encounter a problem with rounding error making the rect not fit
            System.out.println(e);
        }
    }

    // Draw +1/+1 and other counters
    protected void drawCounters(Graphics2D g) {
        int xPos = (int) (0.65 * cardWidth);
        int yPos = (int) (0.15 * cardHeight);
        if (cardView.getCounters() != null) {
            for (CounterView v : cardView.getCounters()) {
                // Don't render loyalty, we do that in the bottom corner
                if (!v.getName().equals("loyalty")) {
                    Polygon p;
                    switch (v.getName()) {
                        case "+1/+1":
                            p = PLUS_COUNTER_POLY;
                            break;
                        case "-1/-1":
                            p = MINUS_COUNTER_POLY;
                            break;
                        case "time":
                            p = TIME_COUNTER_POLY;
                            break;
                        default:
                            p = OTHER_COUNTER_POLY;
                            break;
                    }
                    double scale = (0.1 * 0.25 * cardWidth);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.translate(xPos, yPos);
                    g2.scale(scale, scale);
                    g2.setColor(Color.white);
                    g2.fillPolygon(p);
                    g2.setColor(Color.black);
                    g2.drawPolygon(p);
                    g2.setFont(new Font("Arial", Font.BOLD, 7));
                    String cstr = String.valueOf(v.getCount());
                    int strW = g2.getFontMetrics().stringWidth(cstr);
                    g2.drawString(cstr, 5 - strW / 2, 8);
                    g2.dispose();
                    yPos += ((int) (0.30 * cardWidth));
                }
            }
        }
    }

    // Draw an expansion symbol, right justified, in a given region
    // Return the width of the drawn symbol
    protected int drawExpansionSymbol(Graphics2D g, int x, int y, int w, int h) {
        // Draw the expansion symbol
        Image setSymbol = ManaSymbols.getSetSymbolImage(cardView.getExpansionSetCode(), cardView.getRarity());
        int setSymbolWidth;
        if (setSymbol == null) {
            // Don't draw anything when we don't have a set symbol
            return 0;
            /*
            // Just draw the as a code
            String code = cardView.getExpansionSetCode();
            code = (code != null) ? code.toUpperCase(Locale.ENGLISH) : "";
            FontMetrics metrics = g.getFontMetrics();
            setSymbolWidth = metrics.stringWidth(code);
            if (cardView.getRarity() == Rarity.COMMON) {
                g.setColor(Color.white);
            } else {
                g.setColor(Color.black);
            }
            g.fillRoundRect(
                    x + w - setSymbolWidth - 1, y + 2,
                    setSymbolWidth+2, h - 5,
                    5, 5);
            g.setColor(getRarityColor());
            g.drawString(code, x + w - setSymbolWidth, y + h - 3);
             */
        } else {
            // Draw the set symbol
            int height = setSymbol.getHeight(null);
            int scale = 1;
            if (height != -1) {
                while (height > h + 2) {
                    scale *= 2;
                    height /= 2;
                }
            }
            setSymbolWidth = setSymbol.getWidth(null) / scale;
            g.drawImage(setSymbol,
                    x + w - setSymbolWidth, y + (h - height) / 2,
                    setSymbolWidth, height,
                    null);
        }
        return setSymbolWidth;
    }

    private Color getRarityColor() {
        if (cardView.getRarity() != null) {
            switch (cardView.getRarity()) {
                case RARE:
                    return new Color(255, 191, 0);
                case UNCOMMON:
                    return new Color(192, 192, 192);
                case MYTHIC:
                    return new Color(213, 51, 11);
                case SPECIAL:
                    return new Color(204, 0, 255);
                case BONUS:
                    return new Color(129, 228, 228);
                case COMMON:
                default:
                    return Color.black;
            }
        } else {
            // tokens
            return Color.black;
        }
    }

    // Get a string representing the type line
    protected String getCardTypeLine() {
        if (cardView.isAbility()) {
            if (cardView.getAbilityType() == AbilityType.TRIGGERED) {
                return "Triggered Ability";
            } else if (cardView.getAbilityType() == AbilityType.ACTIVATED) {
                return "Activated Ability";
            } else if (cardView.getAbilityType() == null) {
                // TODO: Triggered abilities waiting to be put onto the stack have abilityType = null. Figure out why
                return "Triggered Ability";
            } else {
                return "??? Ability";
            }
        } else {
            StringBuilder sbType = new StringBuilder();
            String spType = getCardSuperTypeLine();
            String subType = getCardSubTypeLine();
            if (spType.equalsIgnoreCase("")) {
                sbType.append(subType);
            } else {
                sbType.append(spType);
                if (!subType.equalsIgnoreCase("")) {
                    sbType.append("- ");
                    sbType.append(subType);
                }
            }

            return sbType.toString();
        }
    }

    protected String getCardSuperTypeLine() {
        StringBuilder spType = new StringBuilder();
        for (SuperType superType : cardView.getSuperTypes()) {
            spType.append(superType).append(' ');
        }
        for (CardType cardType : cardView.getCardTypes()) {
            spType.append(cardType.toString()).append(' ');
        }
        return spType.toString();
    }

    protected String getCardSubTypeLine() {
        StringBuilder subType = new StringBuilder();

        if (!cardView.getSubTypes().isEmpty()) {
            for (SubType sType : cardView.getSubTypes()) {
                subType.append(sType).append(' ');
            }
        }
        return subType.toString();
    }

    // Set the card art image (CardPanel will give it to us when it
    // is loaded and ready)
    public void setArtImage(Image image) {
        artImage = CardRendererUtils.toBufferedImage(image);
    }

    // Set the card art image (CardPanel will give it to us when it
    // is loaded and ready)
    public void setFaceArtImage(Image image) {
        faceArtImage = CardRendererUtils.toBufferedImage(image);
    }
}
