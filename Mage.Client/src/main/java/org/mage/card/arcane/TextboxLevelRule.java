/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.card.arcane;

import java.util.List;

/**
 * @author StravantUser
 *
 * Level rule associated with leveler cards
 */
public class TextboxLevelRule extends TextboxRule {

    // The levels that this rule applies to
    public final int levelFrom;
    public final int levelTo;

    public static final int AND_HIGHER = 100;

    public TextboxLevelRule(String text, List<AttributeRegion> regions, int levelFrom, int levelTo) {
        super(text, regions, TextboxRuleType.LEVEL);
        this.levelFrom = levelFrom;
        this.levelTo = levelTo;
    }
}
