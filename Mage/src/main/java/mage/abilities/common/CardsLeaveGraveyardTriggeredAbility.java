package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;
        if (zEvent == null){
            return false;
        }
        Set<Card> cards = zEvent.getEvents()
                .stream()
                .filter(Objects::nonNull)
                .filter(ev -> ev.getFromZone() == Zone.GRAVEYARD)
                .filter(ev -> ev.getToZone() != Zone.GRAVEYARD)
                .map(GameEvent::getTargetId)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> filter.match(card, getControllerId(), this, game))
                .filter(card -> this.isControlledBy(card.getOwnerId()))
                .collect(Collectors.toSet());

        if (cards.isEmpty()){
            return false;
        }
        this.getAllEffects().setValue("cardsLeavingGraveyard", cards);
        return true;
    }

    @Override
    public CardsLeaveGraveyardTriggeredAbility copy() {
        return new CardsLeaveGraveyardTriggeredAbility(this);
    }
}
