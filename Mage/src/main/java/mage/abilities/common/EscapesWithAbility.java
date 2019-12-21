package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class EscapesWithAbility extends EntersBattlefieldAbility {

    private final int counters;

    public EscapesWithAbility(int counters) {
        super(new EscapesWithEffect(counters), false);
        this.counters = counters;
    }

    private EscapesWithAbility(final EscapesWithAbility ability) {
        super(ability);
        this.counters = ability.counters;
    }

    @Override
    public EscapesWithAbility copy() {
        return new EscapesWithAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} escapes with " + CardUtil.numberToText(counters, "a")
                + " +1/+1 counter" + (counters > 1 ? 's' : "") + " on it.";
    }
}

class EscapesWithEffect extends OneShotEffect {

    private final int counter;

    EscapesWithEffect(int counter) {
        super(Outcome.BoostCreature);
        this.counter = counter;
    }

    private EscapesWithEffect(final EscapesWithEffect effect) {
        super(effect);
        this.counter = effect.counter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent == null) {
            return false;
        }
        SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
        if (!(spellAbility instanceof EscapeAbility)
                || !spellAbility.getSourceId().equals(source.getSourceId())
                || permanent.getZoneChangeCounter(game) != spellAbility.getSourceObjectZoneChangeCounter()
                || !spellAbility.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
        permanent.addCounters(CounterType.P1P1.createInstance(counter), source, game, appliedEffects);
        return true;
    }

    @Override
    public EscapesWithEffect copy() {
        return new EscapesWithEffect(this);
    }

}
