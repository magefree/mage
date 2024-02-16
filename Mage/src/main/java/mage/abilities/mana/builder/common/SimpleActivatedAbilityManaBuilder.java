package mage.abilities.mana.builder.common;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.game.Game;

import java.util.UUID;

/**
 * testing class
 *
 * @author JayDi85
 */
public class SimpleActivatedAbilityManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SimpleActivatedAbilityConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate or pay for simple abilities";
    }
}

class SimpleActivatedAbilityConditionalMana extends ConditionalMana {

    public SimpleActivatedAbilityConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate or pay for simple abilities";
        addCondition(new SimpleActivatedAbilityManaCondition());
    }
}

class SimpleActivatedAbilityManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return source instanceof SimpleActivatedAbility;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
