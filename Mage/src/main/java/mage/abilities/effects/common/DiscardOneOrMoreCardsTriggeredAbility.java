package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class DiscardOneOrMoreCardsTriggeredAbility extends TriggeredAbilityImpl {

    public DiscardOneOrMoreCardsTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiscardOneOrMoreCardsTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public DiscardOneOrMoreCardsTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you discard one or more cards, ");
    }

    private DiscardOneOrMoreCardsTriggeredAbility(final DiscardOneOrMoreCardsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiscardOneOrMoreCardsTriggeredAbility copy() {
        return new DiscardOneOrMoreCardsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setValue("discarded", event.getAmount());
        return true;
    }
}
