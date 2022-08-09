package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class CardsLeaveGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    public CardsLeaveGraveyardTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever one or more cards leave your graveyard, ");
    }

    private CardsLeaveGraveyardTriggeredAbility(final CardsLeaveGraveyardTriggeredAbility ability) {
        super(ability);
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
                .map(Card::getOwnerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public CardsLeaveGraveyardTriggeredAbility copy() {
        return new CardsLeaveGraveyardTriggeredAbility(this);
    }
}
