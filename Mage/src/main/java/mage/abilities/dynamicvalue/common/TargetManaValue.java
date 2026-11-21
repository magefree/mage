package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum TargetManaValue implements DynamicValue {
    CARD("card", false),
    CREATURE("creature", true),
    ARTIFACT("artifact", true),
    ENCHANTMENT("enchantment", true);

    private final String targetDescription;
    private final boolean lookupToBattlefield;

    TargetManaValue(String targetDescription, boolean lookupToBattlefield) {
        this.targetDescription = targetDescription;
        this.lookupToBattlefield = lookupToBattlefield;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // CR 608.2h - must use LKI to find permanent's data
        MageObject target = this.lookupToBattlefield
                ? effect.getTargetPointer().getFirstTargetPermanentOrLKI(game, sourceAbility)
                : game.getCard(sourceAbility.getFirstTarget());
        return target == null ? 0 : target.getManaValue();
    }

    @Override
    public TargetManaValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that " + targetDescription + "'s mana value";
    }
}
