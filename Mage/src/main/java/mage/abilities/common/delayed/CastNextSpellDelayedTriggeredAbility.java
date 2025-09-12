package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class CastNextSpellDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterSpell filter;
    private final boolean setTargetPointer;

    public CastNextSpellDelayedTriggeredAbility(Effect effect, FilterSpell filter, boolean setTargetPointer) {
        super(effect, Duration.EndOfTurn, true, false);
        this.filter = filter;
        this.setTriggerPhrase("When you next cast " + filter.getMessage() + " this turn, ");
        this.setTargetPointer = setTargetPointer;
    }

    protected CastNextSpellDelayedTriggeredAbility(final CastNextSpellDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public CastNextSpellDelayedTriggeredAbility copy() {
        return new CastNextSpellDelayedTriggeredAbility(this);
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
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }

}
