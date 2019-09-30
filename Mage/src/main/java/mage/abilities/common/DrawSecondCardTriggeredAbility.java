package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

/**
 * @author TheElk801
 */
public class DrawSecondCardTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggeredOnce = false;

    public DrawSecondCardTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
        this.addWatcher(new CardsAmountDrawnThisTurnWatcher());
    }

    private DrawSecondCardTriggeredAbility(final DrawSecondCardTriggeredAbility ability) {
        super(ability);
        this.triggeredOnce = ability.triggeredOnce;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_POST) {
            triggeredOnce = false;
            return false;
        }
        if (event.getType() != GameEvent.EventType.DREW_CARD
                || !event.getPlayerId().equals(controllerId)
                || game.getPermanent(sourceId) == null) {
            return false;
        }
        if (triggeredOnce) {
            return false;
        }
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (watcher.getAmountCardsDrawn(controllerId) > 1) {
            triggeredOnce = true;
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you draw your second card each turn, " + super.getRule();
    }

    @Override
    public DrawSecondCardTriggeredAbility copy() {
        return new DrawSecondCardTriggeredAbility(this);
    }
}
