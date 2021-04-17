package org.mage.card.arcane;

import java.util.List;

/**
 * @author stravant@gmail.com
 */
public class TextboxKeywordRule extends TextboxRule {
    public TextboxKeywordRule(String text, List<AttributeRegion> regions) {
        super(text, regions, TextboxRuleType.SIMPLE_KEYWORD);
    }
}
