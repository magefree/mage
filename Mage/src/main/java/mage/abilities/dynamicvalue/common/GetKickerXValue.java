package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.List;

/**
 * @author JayDi85
 */
public enum GetKickerXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        // calcs only kicker with X values

        // kicker adds additional costs to spell ability
        // only one X value per card possible
        // kicker can be calls multiple times (use getKickedCounter)

        int finalValue = 0;
        Spell spell = game.getSpellOrLKIStack(source.getSourceId());
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
                        int kickedCount = ((KickerAbility) ability).getKickedCounter(game, source);
                        if (kickedCount > 0) {
                            finalValue += kickedCount * xValue;
                        }
                    }
                }
            }
        }
        return finalValue;
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
