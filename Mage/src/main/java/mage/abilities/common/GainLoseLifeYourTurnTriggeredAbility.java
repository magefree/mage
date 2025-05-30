package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class GainLoseLifeYourTurnTriggeredAbility extends TriggeredAbilityImpl {

    public GainLoseLifeYourTurnTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you gain or lose life during your turn, ");
    }

    private GainLoseLifeYourTurnTriggeredAbility(final GainLoseLifeYourTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GainLoseLifeYourTurnTriggeredAbility copy() {
        return new GainLoseLifeYourTurnTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE
                || event.getType() == GameEvent.EventType.LOST_LIFE_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getActivePlayerId())
                && isControlledBy(event.getTargetId());
    }
}
