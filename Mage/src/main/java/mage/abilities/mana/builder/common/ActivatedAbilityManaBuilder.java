package mage.abilities.mana.builder.common;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ActivatedAbilityManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ActivatedAbilityConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class ActivatedAbilityConditionalMana extends ConditionalMana {

    public ActivatedAbilityConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new ActivatedAbilityManaCondition());
    }
}

class ActivatedAbilityManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return source != null
                && !source.isActivated()
                && source.isActivatedAbility();
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
