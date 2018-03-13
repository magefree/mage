/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
