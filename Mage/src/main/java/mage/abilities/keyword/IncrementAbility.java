package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.AbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class IncrementAbility extends SpellCastControllerTriggeredAbility {

    public IncrementAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_SPELL, false);
    }

    protected IncrementAbility(final IncrementAbility ability) {
        super(ability);
    }

    @Override
    public IncrementAbility copy() {
        return new IncrementAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent == null) {
            return false;
        }
        int pt = Math.min(permanent.getPower().getValue(), permanent.getToughness().getValue());
        return CardUtil
                .getEffectValueFromAbility(this, "spellCast", Spell.class)
                .map(Spell::getSpellAbility)
                .map(AbilityImpl::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .filter(x -> x > pt)
                .isPresent();
    }

    @Override
    public String getRule() {
        return "Increment <i>(Whenever you cast a spell, if the amount of mana you spent " +
                "is greater than this creature's power or toughness, put a +1/+1 counter on this creature.)</i>";
    }
}
