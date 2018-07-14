/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.mana.conditional;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class ConditionalSpellManaBuilder extends ConditionalManaBuilder {

    private final FilterSpell filter;

    public ConditionalSpellManaBuilder(FilterSpell filter) {
        this.filter = filter;
    }

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new SpellCastConditionalMana(this.mana, filter);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast " + filter.getMessage() + '.';
    }
}

class SpellCastConditionalMana extends ConditionalMana {

    SpellCastConditionalMana(Mana mana, FilterSpell filter) {
        super(mana);
        staticText = "Spend this mana only to cast " + filter.getMessage() + '.';
        addCondition(new SpellCastManaCondition(filter));
    }
}

class SpellCastManaCondition extends ManaCondition implements Condition {

    private final FilterSpell filter;

    public SpellCastManaCondition(FilterSpell filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source.getSourceId());
            if ((object instanceof StackObject)) {
                return filter.match((StackObject) object, source.getSourceId(), source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }

}
