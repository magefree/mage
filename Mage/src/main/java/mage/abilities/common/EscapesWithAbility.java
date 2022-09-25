package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
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
    private final TriggeredAbility triggeredAbility;

    public EscapesWithAbility(int counters) {
        this(counters, null);
    }

    public EscapesWithAbility(int counters, TriggeredAbility triggeredAbility) {
        super(new EscapesWithEffect(counters, triggeredAbility), false);
        this.counters = counters;
        this.triggeredAbility = triggeredAbility;
    }

    private EscapesWithAbility(final EscapesWithAbility ability) {
        super(ability);
        this.counters = ability.counters;
        this.triggeredAbility = ability.triggeredAbility;
    }

    @Override
    public EscapesWithAbility copy() {
        return new EscapesWithAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("{this} escapes with ");
        if (counters > 0) {
            sb.append(CardUtil.numberToText(counters, "a"));
            sb.append(" +1/+1 counter");
            sb.append((counters > 1 ? 's' : ""));
            sb.append(" on it.");
        }

        if (triggeredAbility == null) {
            // Do nothing
        } else if (triggeredAbility instanceof DelayedTriggeredAbility) {
            sb.append(this.triggeredAbility.getRule());
        } else {
            sb.append("\"");
            sb.append(this.triggeredAbility.getRule());
            sb.append("\"");
        }
        return sb.toString();
    }
}

class EscapesWithEffect extends OneShotEffect {

    private final int counter;
    private final TriggeredAbility triggeredAbility;

    EscapesWithEffect(int counter, TriggeredAbility triggeredAbility) {
        super(Outcome.BoostCreature);
        this.counter = counter;
        this.triggeredAbility = triggeredAbility;
    }

    private EscapesWithEffect(final EscapesWithEffect effect) {
        super(effect);
        this.counter = effect.counter;
        this.triggeredAbility = (effect.triggeredAbility == null ? null : effect.triggeredAbility.copy());
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
                || permanent.getZoneChangeCounter(game) != spellAbility.getSourceObjectZoneChangeCounter()) {
            return false;
        }

        if (counter > 0) {
            List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
            permanent.addCounters(CounterType.P1P1.createInstance(counter), source.getControllerId(), source, game, appliedEffects);
        }

        if (this.triggeredAbility != null) {
            if (triggeredAbility instanceof DelayedTriggeredAbility) {
                game.addDelayedTriggeredAbility((DelayedTriggeredAbility) this.triggeredAbility, source);
            } else {
                ContinuousEffect gainsAbilityEffect = new GainAbilitySourceEffect(triggeredAbility);
                game.addEffect(gainsAbilityEffect, source);
            }
        }
        return true;
    }

    @Override
    public EscapesWithEffect copy() {
        return new EscapesWithEffect(this);
    }

}
