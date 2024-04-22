package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class TheRingTemptsYouChooseAnotherTriggeredAbility extends TriggeredAbilityImpl {

    public TheRingTemptsYouChooseAnotherTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public TheRingTemptsYouChooseAnotherTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever the Ring tempts you, if you chose a creature other than {this} as your Ring-bearer, ");
    }

    private TheRingTemptsYouChooseAnotherTriggeredAbility(final TheRingTemptsYouChooseAnotherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRingTemptsYouChooseAnotherTriggeredAbility copy() {
        return new TheRingTemptsYouChooseAnotherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TEMPTED_BY_RING;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.isControlledBy(event.getPlayerId())
                && event.getTargetId() != null
                && !event.getTargetId().equals(this.getSourceId());
    }
}
