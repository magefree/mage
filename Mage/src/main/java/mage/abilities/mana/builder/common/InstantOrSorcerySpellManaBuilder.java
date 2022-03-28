package mage.abilities.mana.builder.common;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class InstantOrSorcerySpellManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new InstantOrSorceryCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an instant or sorcery spell";
    }
}

class InstantOrSorceryCastConditionalMana extends ConditionalMana {

    public InstantOrSorceryCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an instant or sorcery spell";
        addCondition(new InstantOrSorceryCastManaCondition());
    }
}

class InstantOrSorceryCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.isInstantOrSorcery(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
