package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author MTGfan
 */
public enum AttachedPermanentToughnessValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        }
        if (enchantment == null) {
            return 0;
        }
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
