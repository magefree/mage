package mage.abilities.common.delayed;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author androosss
 */
public class WhenYouAttackDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public WhenYouAttackDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.EndOfTurn);
    }

    public WhenYouAttackDelayedTriggeredAbility(Effect effect, Duration duration) {
        this(effect, duration, false);
    }

    public WhenYouAttackDelayedTriggeredAbility(Effect effect, Duration duration, boolean triggerOnlyOnce) {
        super(effect, duration, triggerOnlyOnce);
        setTriggerPhrase((triggerOnlyOnce ? "When you next" : "Whenever you") + " attack"
                + (duration == Duration.EndOfTurn ? " this turn, " : ", "));
    }

    protected WhenYouAttackDelayedTriggeredAbility(final WhenYouAttackDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WhenYouAttackDelayedTriggeredAbility copy() {
        return new WhenYouAttackDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID attackerId = game.getCombat().getAttackingPlayerId();
        return attackerId != null && attackerId.equals(getControllerId()) && !game.getCombat().getAttackers().isEmpty();
    }
}
