/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.card.arcane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.font.TextMeasurer;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import mage.ObjectColor;
import mage.client.dialog.PreferencesDialog;
import mage.constants.CardType;
import mage.view.CardView;
import mage.view.PermanentView;
import net.java.balloontip.styles.RoundedBalloonStyle;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardRenderer;
import org.mage.card.arcane.CardRendererUtils;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.TextboxLoyaltyRule;
import org.mage.card.arcane.TextboxRule;
import org.mage.card.arcane.TextboxRuleType;
import sun.security.pkcs11.P11TlsKeyMaterialGenerator;


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
    private static Logger LOGGER = Logger.getLogger(ModernCardRenderer.class);
    
    ///////////////////////////////////////////////////////////////////////////
    // Textures for modern frame cards

    private static TexturePaint loadBackgroundTexture(String name) {
        URL url = ModernCardRenderer.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
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
    public static Font BASE_BELEREN_FONT = loadFont("beleren-bold");
    
    public static Paint BG_TEXTURE_WHITE    = loadBackgroundTexture("white");
    public static Paint BG_TEXTURE_BLUE     = loadBackgroundTexture("blue");
    public static Paint BG_TEXTURE_BLACK    = loadBackgroundTexture("black");
    public static Paint BG_TEXTURE_RED      = loadBackgroundTexture("red");
    public static Paint BG_TEXTURE_GREEN    = loadBackgroundTexture("green");
    public static Paint BG_TEXTURE_GOLD     = loadBackgroundTexture("gold");
    public static Paint BG_TEXTURE_ARTIFACT = loadBackgroundTexture("artifact");
    public static Paint BG_TEXTURE_LAND     = loadBackgroundTexture("land");
    
    public static Color BORDER_WHITE = new Color(216, 203, 188);
    public static Color BORDER_BLUE = new Color(20, 121, 175);
    public static Color BORDER_BLACK = new Color(45, 45, 35);
    public static Color BORDER_RED = new Color(201, 71, 58);
    public static Color BORDER_GREEN = new Color(4, 136, 69);
    public static Color BORDER_GOLD = new Color(255, 228, 124);
    public static Color BORDER_COLORLESS = new Color(238, 242, 242);
    public static Color BORDER_LAND = new Color(190, 173, 115);
    
    public static Color BOX_WHITE = new Color(244, 245, 239);
    public static Color BOX_BLUE = new Color(201, 223, 237);
    public static Color BOX_BLACK = new Color(204, 194, 192);
    public static Color BOX_RED = new Color(246, 208, 185);
    public static Color BOX_GREEN = new Color(205, 221, 213);
    public static Color BOX_GOLD = new Color(223, 195, 136);
    public static Color BOX_COLORLESS = new Color(220, 228, 232);
    public static Color BOX_LAND = new Color(220, 215, 213);
    
    public static Color BOX_WHITE_NIGHT = new Color(169, 160, 145);
    public static Color BOX_BLUE_NIGHT = new Color(46, 133, 176);
    public static Color BOX_BLACK_NIGHT = new Color(95, 90, 89);
    public static Color BOX_RED_NIGHT = new Color(188, 87, 57);
    public static Color BOX_GREEN_NIGHT = new Color(31, 100, 44);
    public static Color BOX_GOLD_NIGHT = new Color(171, 134, 70);
    public static Color BOX_COLORLESS_NIGHT = new Color(118, 147, 158);
    
    public static Color TEXTBOX_WHITE = new Color(252, 249, 244, 244);
    public static Color TEXTBOX_BLUE  = new Color(229, 238, 247, 244);
    public static Color TEXTBOX_BLACK = new Color(241, 241, 240, 244);
    public static Color TEXTBOX_RED   = new Color(243, 224, 217, 244);
    public static Color TEXTBOX_GREEN = new Color(217, 232, 223, 244);
    public static Color TEXTBOX_GOLD  = new Color(240, 234, 209, 244);
    public static Color TEXTBOX_COLORLESS = new Color(219, 229, 233, 244);
    public static Color TEXTBOX_LAND = new Color(218, 214, 212, 244);
    
    public static Color ERROR_COLOR = new Color(255, 0, 255);
    
    
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
    protected static float BOX_HEIGHT_FRAC = 0.065f; // x cardHeight
    protected static int BOX_HEIGHT_MIN = 16;
    protected int boxHeight;
    
    // How far down the card is the type line placed?
    protected static float TYPE_LINE_Y_FRAC = 0.57f; // x cardHeight
    protected static float TYPE_LINE_Y_FRAC_TOKEN = 0.70f;
    protected int typeLineY;
    
    // How large is the box text, and how far is it down the boxes
    protected int boxTextHeight;
    protected int boxTextOffset;
    protected Font boxTextFont;

    // How large is the P/T text, and how far is it down the boxes
    protected int ptTextHeight;
    protected int ptTextOffset;
    protected Font ptTextFont;
    
    // Processed mana cost string
    protected String manaCostString;
    
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
        contentWidth = cardWidth - 2*totalContentInset;
        
        // Box height
        boxHeight = (int)Math.max(
                BOX_HEIGHT_MIN,
                BOX_HEIGHT_FRAC * cardHeight);
        
        // Type line at
        if (cardView.isToken()) {
            typeLineY = (int)(TYPE_LINE_Y_FRAC_TOKEN * cardHeight);
        } else {
            typeLineY = (int)(TYPE_LINE_Y_FRAC * cardHeight);
        }
        
        // Box text height
        boxTextHeight = getTextHeightForBoxHeight(boxHeight);
        boxTextOffset = (boxHeight - boxTextHeight)/2;
        boxTextFont = BASE_BELEREN_FONT.deriveFont(Font.PLAIN, boxTextHeight);
        
        // Box text height
        ptTextHeight = getPTTextHeightForLineHeight(boxHeight);
        ptTextOffset = (boxHeight - ptTextHeight)/2;
        ptTextFont = BASE_BELEREN_FONT.deriveFont(Font.PLAIN, ptTextHeight);
    }
    
    @Override
    protected void drawBorder(Graphics2D g) {
        // Draw border as one rounded rectangle
        g.setColor(Color.black);
        g.fillRoundRect(0, 0, cardWidth, cardHeight, cornerRadius, cornerRadius);
        
        // Selection Borders
        Color borderColor;
        if (isSelected) {
            borderColor = Color.green;
        } else if (isChoosable) {
            borderColor = new Color(250, 250, 0, 230);
        } else if (cardView.isPlayable()) {
            borderColor = new Color(153, 102, 204, 200);
        } else if (cardView instanceof PermanentView && ((PermanentView)cardView).isCanAttack()) {
            borderColor = new Color(0, 0, 255, 230);
        } else {
            borderColor = null;
        }
        if (borderColor != null) {
            float hwidth = borderWidth / 2.0f;
            Graphics2D g2 = (Graphics2D)g.create();
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
    }
    
    @Override
    protected void drawBackground(Graphics2D g) {
        // Draw background, in 3 parts
        
        if (cardView.isFaceDown()) {
            // Just draw a brown rectangle
            drawCardBack(g);
        } else {
            // Set texture to paint with
            g.setPaint(getBackgroundPaint(cardView.getColor(), cardView.getCardTypes()));

            // Draw main part (most of card)
            g.fillRoundRect(
                    borderWidth, borderWidth, 
                    cardWidth - borderWidth*2, cardHeight - borderWidth*4 - cornerRadius*2,
                    cornerRadius - 1, cornerRadius - 1);

            // Draw the M15 rounded "swoosh" at the bottom
            g.fillRoundRect(
                    borderWidth, cardHeight - borderWidth*4 - cornerRadius*4,
                    cardWidth - borderWidth*2, cornerRadius*4,
                    cornerRadius*2, cornerRadius*2);

            // Draw the cutout into the "swoosh" for the textbox to lie over
            g.fillRect(
                    borderWidth + contentInset, cardHeight - borderWidth*5,
                    cardWidth - borderWidth*2 - contentInset*2, borderWidth*2);
        }
    }
    
    @Override
    protected void drawArt(Graphics2D g) {
        if (artImage != null && !cardView.isFaceDown()) {
            int imgWidth = artImage.getWidth();
            int imgHeight = artImage.getHeight();
            BufferedImage subImg = 
                    artImage.getSubimage(
                            (int)(.079*imgWidth), (int)(.11*imgHeight), 
                            (int)(.84*imgWidth), (int)(.42*imgHeight));
            g.drawImage(subImg,
                    totalContentInset+1, totalContentInset+boxHeight, 
                    contentWidth - 2, typeLineY - totalContentInset - boxHeight, 
                    null);
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
        
        // Draw the main card content border
        g.setPaint(borderPaint);
        g.drawRect(
                totalContentInset, totalContentInset,
                contentWidth - 1, cardHeight - borderWidth*3 - totalContentInset - 1);
        
        // Draw the textbox fill
        g.setPaint(textboxPaint);
        g.fillRect(
                totalContentInset + 1, typeLineY,
                contentWidth - 2, cardHeight - borderWidth*3 - typeLineY - 1);
        
        // If it's a planeswalker, extend the textbox left border by some
        if (cardView.getCardTypes().contains(CardType.PLANESWALKER)) {
            g.setPaint(borderPaint);
            g.fillRect(
                    totalContentInset, typeLineY + boxHeight,
                    cardWidth/16, cardHeight - typeLineY - boxHeight - borderWidth*3);
        }
        
        // Draw a shadow highlight at the right edge of the content frame
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(
                totalContentInset - 1, totalContentInset,
                1, cardHeight - borderWidth*3 - totalContentInset - 1);
        
        // Draw a shadow highlight separating the card art and rest of frame
        g.drawRect(
                totalContentInset + 1, totalContentInset + boxHeight,
                contentWidth - 3, typeLineY - totalContentInset - boxHeight - 1);
        
        // Draw the name line box
        CardRendererUtils.drawRoundedBox(g, 
                borderWidth, totalContentInset, 
                cardWidth - 2*borderWidth, boxHeight, 
                contentInset, 
                borderPaint, boxColor);
        
        // Draw the type line box
        CardRendererUtils.drawRoundedBox(g, 
                borderWidth, typeLineY, 
                cardWidth - 2*borderWidth, boxHeight, 
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
                1, cardHeight - borderWidth*3 - typeLineY - boxHeight);
        
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
                contentWidth - 4, cardHeight - typeLineY - boxHeight - 4 - borderWidth*3);
        
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
            if (cardView instanceof PermanentView && ((PermanentView)cardView).isManifested()) {
                nameStr = "Manifest: " + cardView.getName();   
            } else {
                nameStr = "Morph: " + cardView.getName();
            }
        } else {
            nameStr = cardView.getName();
        }
        AttributedString str = new AttributedString(nameStr);
        str.addAttribute(TextAttribute.FONT, boxTextFont);
        TextMeasurer measure = new TextMeasurer(str.getIterator(), g.getFontRenderContext());
        TextLayout layout = measure.getLayout(0, measure.getLineBreakIndex(0, availableWidth));
        g.setColor(getBoxTextColor());
        layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);
    
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
            TextLayout layout = measure.getLayout(0, measure.getLineBreakIndex(0, availableWidth));
            g.setColor(getBoxTextColor());
            layout.draw(g, x, y + boxTextOffset + boxTextHeight - 1);   
        }
    }
    
    // Draw the P/T and/or Loyalty boxes
    protected void drawBottomRight(Graphics2D g, Paint borderPaint, Color fill) {
        // No bottom right for abilities
        if (cardView.isAbility()) {
            return;
        }
        
        // Where to start drawing the things
        int curY = cardHeight - (int)(0.03f*cardHeight);
        
        // Width of the boxes
        int partWidth = (int)Math.max(30, 0.20f*cardWidth);
        
        // Is it a creature?
        if (cardView.getCardTypes().contains(CardType.CREATURE)) {
            int x = cardWidth - borderWidth - partWidth;
            
            // Draw PT box
            CardRendererUtils.drawRoundedBox(g, 
                    x, curY - boxHeight,
                    partWidth, boxHeight,
                    contentInset,
                    borderPaint,
                    fill);
            
            // Draw shadow line top
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(
                    x + contentInset, curY - boxHeight - 1,
                    partWidth - 2*contentInset, 1);
            
            // Draw text
            g.setColor(getBoxTextColor());
            g.setFont(ptTextFont);
            String ptText = cardView.getPower() + "/" + cardView.getToughness();
            int ptTextWidth = g.getFontMetrics().stringWidth(ptText);
            g.drawString(ptText, 
                    x + (partWidth - ptTextWidth)/2, curY - ptTextOffset - 1);
            
            // Does it have damage on it?
            if ((cardView instanceof PermanentView) && ((PermanentView)cardView).getDamage() > 0) {
                // Show marked damage
                
            }
            
            curY -= boxHeight;
        }
        
        // Is it a walker? (But don't draw the box if it's a non-permanent view
        // of a walker without a starting loyalty (EG: Arlin Kord's flipped side).
        if (cardView.getCardTypes().contains(CardType.PLANESWALKER)
                && (cardView instanceof PermanentView || !cardView.getStartingLoyalty().equals("0"))) {
            // Draw the PW loyalty box
            int w = partWidth;
            int h = partWidth/2;
            int x = cardWidth - partWidth - borderWidth;
            int y = curY - h;
            
            Polygon symbol = new Polygon(
                new int[]{
                    x + w/2,
                    (int)(x + w*0.9),
                    x + w,
                    (int)(x + w*0.6),
                    x + w/2,
                    (int)(x + w*0.4),
                    x,
                    (int)(x + w*0.1),
                },
                new int[]{
                    y + h,
                    (int)(y + 0.8*h),
                    y,
                    (int)(y - 0.2*h),
                    y,
                    (int)(y - 0.2*h),
                    y,
                    (int)(y + 0.8*h),
                },
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
            g.drawString(loyalty, x + (w - loyaltyWidth)/2, y + ptTextHeight + (h - ptTextHeight)/2);
            
            // Advance
            curY -= (int)(1.2*y);
        }
        
        // does it have damage on it?
        if ((cardView instanceof PermanentView) && ((PermanentView)cardView).getDamage() > 0) {
            int x = cardWidth - partWidth - borderWidth;
            int y = curY - boxHeight;
            String damage = "" + ((PermanentView)cardView).getDamage();
            g.setFont(ptTextFont);
            int txWidth = g.getFontMetrics().stringWidth(damage);
            g.setColor(Color.red);
            g.fillRect(x, y, partWidth, boxHeight);
            g.setColor(Color.white);
            g.drawRect(x, y, partWidth, boxHeight);
            g.drawString(damage, x + (partWidth - txWidth)/2, curY - 1);
        }
    }
    
    // Draw the card's textbox in a given rect
    protected boolean loyaltyAbilityColorToggle = false;
    protected void drawRulesText(Graphics2D g, int x, int y, int w, int h) {        
        // Initial font size to try to render at
        Font font = new Font("Arial", Font.PLAIN, 12);
        Font fontItalic = new Font("Arial", Font.ITALIC, 12);
        
        // Handle the keyword rules
        boolean hasKeywords = !textboxKeywords.isEmpty();
        String keywordRulesString = getKeywordRulesString();
        AttributedString keywordRulesAttributed = new AttributedString(keywordRulesString);
        if (hasKeywords) {
            keywordRulesAttributed.addAttribute(TextAttribute.FONT, font);
        }
        
        // Get the total height
        List<AttributedString> attributedRules = new ArrayList<>();
        boolean useSmallFont = false;
        int remaining = h;
        {
            if (hasKeywords) {
                remaining -= drawSingleRule(g, keywordRulesAttributed, null, 0, 0, w, remaining, false);
            }
            for (TextboxRule rule: textboxRules) {
                AttributedString attributed = rule.generateAttributedString(font, fontItalic);
                attributedRules.add(attributed);
                remaining -= drawSingleRule(g, attributed, rule, 0, 0, w, remaining, false);
                if (remaining < 0) {
                    useSmallFont = true;
                    break;
                }
            }
        }
        
        // If there wasn't enough room, try using a smaller font
        if (useSmallFont) {
            font = new Font("Arial", Font.PLAIN, 9);
            fontItalic = new Font("Arial", Font.ITALIC, 9);
            if (hasKeywords) {
                keywordRulesAttributed = new AttributedString(keywordRulesString);
                keywordRulesAttributed.addAttribute(TextAttribute.FONT, font);
            }
            
            // Clear out the attributed rules and reatribute them with the new font size
            attributedRules.clear();
            for (TextboxRule rule: textboxRules) {
                AttributedString attributed = rule.generateAttributedString(font, fontItalic);
                attributedRules.add(attributed);
            }
            
            // Get the new spacing for the small text
            remaining = h;
            if (hasKeywords) {
                remaining -= drawSingleRule(g, keywordRulesAttributed, null, 0, 0, w, remaining, false);
            }
            for (TextboxRule rule: textboxRules) {
                AttributedString attributed = rule.generateAttributedString(font, fontItalic);
                attributedRules.add(attributed);
                remaining -= drawSingleRule(g, attributed, rule, 0, 0, w, remaining, false);
                if (remaining < 0) {
                    useSmallFont = true;
                    break;
                }
            }
        }
        
        // Do we have room for additional spacing between the parts of text?
        // If so, calculate the spacing based on how much space was left over
        int spacing = 
                (int)(remaining / (hasKeywords ? 
                        (textboxRules.size() + 2) : 
                        (textboxRules.size() + 1)));
        
        // Do the actual draw
        loyaltyAbilityColorToggle = false;
        g.setColor(Color.black);
        int curY = y + spacing;
        if (hasKeywords) {
            int adv = drawSingleRule(g, keywordRulesAttributed, null, x, curY, w, h, true);
            curY += adv + spacing;
            h -= adv;
        }
        for (int i = 0; i < textboxRules.size(); ++i) {
            TextboxRule rule = textboxRules.get(i);
            AttributedString attributedRule = attributedRules.get(i);
            int adv = drawSingleRule(g, attributedRule, rule, x, curY, w, h, true);
            curY += adv + spacing;
            h -= adv;
            if (h < 0) {
                break;
            }
        }
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
            inset = cardWidth/12;
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
            char ch = newLineCheck.setIndex(measure.getPosition());
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
        int advance = ((int)Math.ceil(yPos)) - y;
        
        // Is it a loyalty ability?
        if (rule != null && rule.type == TextboxRuleType.LOYALTY) {
            TextboxLoyaltyRule loyaltyRule = (TextboxLoyaltyRule)rule;
            Polygon symbol;
            int symbolWidth = (x + inset) - borderWidth - 4;
            int symbolHeight = (int)(0.7f*symbolWidth);
            if (symbolHeight > advance) {
                advance = symbolHeight;
            }
            int symbolX = x - borderWidth;
            int symbolY = y + (advance - symbolHeight)/2;
            if (doDraw) {
                if (loyaltyRule.loyaltyChange < 0 || loyaltyRule.loyaltyChange == TextboxLoyaltyRule.MINUS_X) {
                    symbol = new Polygon(
                        new int[]{
                            symbolX, 
                            symbolX + symbolWidth,
                            symbolX + symbolWidth,
                            symbolX + symbolWidth/2,
                            symbolX,
                        },
                        new int[]{
                            symbolY,
                            symbolY,
                            symbolY + symbolHeight - 3,
                            symbolY + symbolHeight + 3,
                            symbolY + symbolHeight - 3,
                        },
                    5);             
                } else if (loyaltyRule.loyaltyChange > 0) {
                    symbol = new Polygon(
                        new int[]{
                            symbolX, 
                            symbolX + symbolWidth/2,
                            symbolX + symbolWidth,
                            symbolX + symbolWidth,
                            symbolX,
                        },
                        new int[]{
                            symbolY + 3,
                            symbolY - 3,
                            symbolY + 3,
                            symbolY + symbolHeight,
                            symbolY + symbolHeight,
                        },
                    5); 
                } else {
                    symbol = new Polygon(
                        new int[]{
                            symbolX, 
                            symbolX + symbolWidth,
                            symbolX + symbolWidth,
                            symbolX,
                        },
                        new int[]{
                            symbolY, 
                            symbolY,
                            symbolY + symbolHeight,
                            symbolY + symbolHeight,
                        },
                    4);               
                }
                g.setColor(new Color(0, 0, 0, 128));
                g.fillRect(x+2, y+advance+1, w-2, 1);
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
                        symbolX + (symbolWidth - textWidth)/2, 
                        symbolY + symbolHeight - (symbolHeight - boxTextHeight)/2);

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
            g.fillOval(borderWidth+1, totalContentInset+1, boxHeight-2, boxHeight-2);
            g.setColor(Color.white);
            if (isNightCard()) {
                g.fillArc(borderWidth+3, totalContentInset+3, boxHeight-6, boxHeight-6, 90, 270);
                g.setColor(Color.black);
                g.fillArc(borderWidth+3+3, totalContentInset+3, boxHeight-6-3, boxHeight-6, 90, 270);
            } else {
                g.fillOval(borderWidth+3, totalContentInset+3, boxHeight-6, boxHeight-6);
            }
        }
        return transformCircleOffset;
    }
    
    // Get the text height for a given box height
    protected static int getTextHeightForBoxHeight(int h) {
        if (h < 15) {
            return h-3;
        } else {
            return (int)Math.ceil(.6*h);
        } 
    }
    
    protected static int getPTTextHeightForLineHeight(int h) {
        return h - 4;
    }
    
    // Determine the color of the name / type line text
    protected Color getBoxTextColor() {
        if (isNightCard()) {
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
    protected static Paint getBackgroundPaint(ObjectColor colors, Collection<CardType> types) {
        if (types.contains(CardType.LAND)) {
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
    
    // Determine the border paint to use, based on an ObjectColors
    protected static Paint getTextboxPaint(ObjectColor colors, Collection<CardType> types, int width) {
        if (colors.isMulticolored()) {
            if (colors.getColorCount() == 2) {
                List<ObjectColor> twoColors = colors.getColors();
                
                // Special case for two colors, gradient paint
                return new LinearGradientPaint(
                        0, 0, width, 0, 
                        new float[]{0.4f, 0.6f}, 
                        new Color[]{
                            getTextboxColor(twoColors.get(0)),
                            getTextboxColor(twoColors.get(1))
                        });
            } else {
                return TEXTBOX_GOLD;
            }
        } else if (colors.isColorless()) {
            if (types.contains(CardType.LAND)) {
                return TEXTBOX_LAND;
            } else {
                return TEXTBOX_COLORLESS;
            }
        } else {
            return getTextboxColor(colors);
        }
    }
}
