package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;
import mage.game.stack.Spell;


/**
 * Kicker {X}
 *
 * @author JayDi85
 */
public enum GetKickerXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // calcs only kicker with X values

        // kicker adds additional costs to spell ability
        // only one X value per card possible
        // kicker can be calls multiple times (use getKickedCounter)

        int countX = 0;
        Spell spell = game.getSpellOrLKIStack(sourceAbility.getSourceId());
        if (spell != null && spell.getSpellAbility() != null) {
            int xValue = spell.getSpellAbility().getManaCostsToPay().getX();
            for (Ability ability : spell.getAbilities()) {
                if (ability instanceof KickerAbility) {

                    // search that kicker used X value
                    KickerAbility kickerAbility = (KickerAbility) ability;
                    boolean haveVarCost = kickerAbility.getKickerCosts()
                            .stream()
                            .anyMatch(varCost -> !((OptionalAdditionalCostImpl) varCost).getVariableCosts().isEmpty());

                    if (haveVarCost) {
                        int kickedCount = ((KickerAbility) ability).getKickedCounter(game, sourceAbility);
                        if (kickedCount > 0) {
                            countX += kickedCount * xValue;
                        }
                    }
                }
            }
        }
        return countX;
    }

    @Override
    public GetKickerXValue copy() {
        return GetKickerXValue.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
