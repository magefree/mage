package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class CopyNextSpellDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterSpell filter;

    public CopyNextSpellDelayedTriggeredAbility() {
        this(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY);
    }

    public CopyNextSpellDelayedTriggeredAbility(FilterSpell filter) {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
        this.filter = filter;
    }

    private CopyNextSpellDelayedTriggeredAbility(final CopyNextSpellDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public CopyNextSpellDelayedTriggeredAbility copy() {
        return new CopyNextSpellDelayedTriggeredAbility(this);
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
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !filter.match(spell, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next " + filter.getMessage() + " this turn, "
                + "copy that spell. You may choose new targets for the copy.";
    }
}
