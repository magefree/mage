package org.mage.card.arcane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Paint;
import java.awt.font.GraphicAttribute;
import java.awt.font.ImageGraphicAttribute;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.List;

/**
 * @author stravant@gmail.com
 *
 * Class describing parsed & translated rules in the text box of a card, ready
 * to be rendered.
 */
public class TextboxRule {

    // An attributed region in the text, which can be applied to an
    // attributed string.
    @FunctionalInterface
    public interface AttributeRegion {

        void applyToAttributedString(AttributedString str, Font normal, Font italic);
    }

    // A region of italics, or bold text in a
    public static class ItalicRegion implements AttributeRegion {

        ItalicRegion(int start, int end) {
            this.start = start;
            this.end = end;
        }
        private final int start;
        private final int end;

        @Override
        public void applyToAttributedString(AttributedString str, Font normal, Font italic) {
            if (end > start + 1) {
                str.addAttribute(TextAttribute.FONT, italic, start, end);
            }
        }
    }

    public static class ColorRegion implements AttributeRegion {

        ColorRegion(int start, int end, Paint color) {
            this.start = start;
            this.end = end;
            this.color = color;
        }
        private final int start;
        private final int end;
        private final Paint color;

        @Override
        public void applyToAttributedString(AttributedString str, Font normal, Font italic) {
            if (end > start + 1) {
                str.addAttribute(TextAttribute.FOREGROUND, color, start, end);
            }
        }
    }

    // A special symbol embedded at some point in a string
    public static class EmbeddedSymbol implements AttributeRegion {

        EmbeddedSymbol(String symbol, int location) {
            this.symbol = symbol;
            this.location = location;
        }
        private final String symbol;
        private final int location;

        @Override
        public void applyToAttributedString(AttributedString str, Font normal, Font italic) {
            Image symbolImage = ManaSymbols.getSizedManaSymbol(symbol.replace("/", ""), normal.getSize());
            if (symbolImage != null) {
                ImageGraphicAttribute imgAttr
                        = new ImageGraphicAttribute(symbolImage, GraphicAttribute.BOTTOM_ALIGNMENT);
                str.addAttribute(TextAttribute.CHAR_REPLACEMENT, imgAttr, location, location + 1);
            }
        }
    }

    public final String text;
    public final TextboxRuleType type;

    private final List<AttributeRegion> regions;

    protected TextboxRule(String text, List<AttributeRegion> regions, TextboxRuleType type) {
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Empty rule");
        }
        this.text = text;
        this.type = type;
        this.regions = regions;
    }

    public TextboxRule(String text, List<AttributeRegion> regions) {
        this(text, regions, TextboxRuleType.NORMAL);
    }

    public AttributedString generateAttributedString(Font normal, Font italic) {
        // Build the final attributed text using the regions
        // Do it in reverse order for proper handling of regions where
        // there are multiple attributes stacked (EG: bold + italic)
        AttributedString attributedRule = new AttributedString(text);
        if (!text.isEmpty()) {
            attributedRule.addAttribute(TextAttribute.FONT, normal);
            for (int i = regions.size() - 1; i >= 0; --i) {
                regions.get(i).applyToAttributedString(attributedRule, normal, italic);
            }
        }
        return attributedRule;
    }
}
