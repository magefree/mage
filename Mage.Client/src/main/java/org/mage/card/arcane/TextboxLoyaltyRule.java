/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.card.arcane;

import java.util.List;

/**
 * @author StravantUser
 */
public class TextboxLoyaltyRule extends TextboxRule {

    public final int loyaltyChange;

    public static final int MINUS_X = 100;

    public String getChangeString() {
        if (loyaltyChange == MINUS_X) {
            return "-X";
        } else if (loyaltyChange > 0) {
            return "+" + loyaltyChange;
        } else {
            return String.valueOf(loyaltyChange);
        }
    }

    public TextboxLoyaltyRule(String text, List<AttributeRegion> regions, int loyaltyChange) {
        super(text, regions, TextboxRuleType.LOYALTY);
        this.loyaltyChange = loyaltyChange;
    }
}
