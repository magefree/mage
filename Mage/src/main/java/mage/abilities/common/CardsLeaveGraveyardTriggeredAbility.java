package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class CardsLeaveGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCard filter;

    public CardsLeaveGraveyardTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_CARD_CARDS);
    }

    public CardsLeaveGraveyardTriggeredAbility(Effect effect, FilterCard filter) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        setTriggerPhrase("Whenever one or more " + filter + " leave your graveyard, ");
    }

    private CardsLeaveGraveyardTriggeredAbility(final CardsLeaveGraveyardTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent != null
                && Zone.GRAVEYARD == zEvent.getFromZone()
                && Zone.GRAVEYARD != zEvent.getToZone()
                && zEvent.getCards() != null
                && zEvent.getCards()
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> filter.match(card, getControllerId(), this, game))
                .map(Card::getOwnerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public CardsLeaveGraveyardTriggeredAbility copy() {
        return new CardsLeaveGraveyardTriggeredAbility(this);
    }
}
