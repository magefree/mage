package mage.abilities.common.delayed;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public class AddCounterNextSpellDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterSpell filter;

    public AddCounterNextSpellDelayedTriggeredAbility() {
        this(StaticFilters.FILTER_SPELL_A_CREATURE);
    }

    public AddCounterNextSpellDelayedTriggeredAbility(FilterSpell filter) {
        super(new AddCounterNextSpellEffect(), Duration.EndOfTurn, true, false);
        this.filter = filter;
        this.setTriggerPhrase("When you next cast " + " this turn, ");
    }

    private AddCounterNextSpellDelayedTriggeredAbility(final AddCounterNextSpellDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public AddCounterNextSpellDelayedTriggeredAbility copy() {
        return new AddCounterNextSpellDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !filter.match(spell, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }
}

class AddCounterNextSpellEffect extends ReplacementEffectImpl {

    AddCounterNextSpellEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        staticText = "that creature enters the battlefield with an additional +1/+1 counter on it";
    }

    private AddCounterNextSpellEffect(AddCounterNextSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && event.getTargetId().equals(spell.getCard().getId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        creature.addCounters(
                CounterType.P1P1.createInstance(), source.getControllerId(),
                source, game, event.getAppliedEffects()
        );
        discard();
        return false;
    }

    @Override
    public AddCounterNextSpellEffect copy() {
        return new AddCounterNextSpellEffect(this);
    }
}
