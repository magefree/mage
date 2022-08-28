package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class CopyNextSpellDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public CopyNextSpellDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    private CopyNextSpellDelayedTriggeredAbility(final CopyNextSpellDelayedTriggeredAbility ability) {
        super(ability);
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
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, "
                + "copy that spell. You may choose new targets for the copy.";
    }
}
