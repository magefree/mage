package mage.abilities.mana.conditional;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
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
            MageObject object = game.getObject(source);
            if ((object instanceof StackObject)) {
                return filter.match((StackObject) object, source.getControllerId(), source, game);
            }

            // checking mana without real cast
            if (game.inCheckPlayableState()) {
                Spell spell = null;
                if (object instanceof Card) {
                    spell = new Spell((Card) object, (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                } else if (object instanceof Commander) {
                    spell = new Spell(((Commander) object).getSourceObject(), (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                }
                return filter.match(spell, source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }

}
