package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class DiscardOneOrMoreCardsTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCard filter;

    public DiscardOneOrMoreCardsTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiscardOneOrMoreCardsTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional, StaticFilters.FILTER_CARD_CARDS);
    }

    public DiscardOneOrMoreCardsTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterCard filter) {
        super(zone, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever you discard one or more " + filter.getMessage() + ", ");
    }

    private DiscardOneOrMoreCardsTriggeredAbility(final DiscardOneOrMoreCardsTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
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
        DiscardedCardsEvent discardedCardsEvent = (DiscardedCardsEvent) event;
        int count = discardedCardsEvent.getDiscardedCards().count(filter, game);
        if (count < 1) {
            return false;
        }

        this.getEffects().setValue("discarded", count);
        return true;
    }
}
