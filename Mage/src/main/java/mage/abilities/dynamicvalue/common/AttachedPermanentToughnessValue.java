/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author MTGfan
 */
public enum AttachedPermanentToughnessValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        return enchanted.getToughness().getValue();
    }

    @Override
    public AttachedPermanentToughnessValue copy() {
        return AttachedPermanentToughnessValue.instance;
    }

    @Override
    public String toString() {
        return "equal to";
    }

    @Override
    public String getMessage() {
        return "that creature's toughness";
    }
}
