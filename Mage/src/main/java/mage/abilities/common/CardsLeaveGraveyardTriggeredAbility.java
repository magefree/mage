package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;


/**
 * @author TheElk801
 */
public class CardsLeaveGraveyardTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    private final FilterCard filter;
    private final boolean yourTurn;

    public CardsLeaveGraveyardTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_CARD_CARDS);
    }

    public CardsLeaveGraveyardTriggeredAbility(Effect effect, FilterCard filter) {
        this(effect, filter, false);
    }

    public CardsLeaveGraveyardTriggeredAbility(Effect effect, FilterCard filter, boolean yourTurn) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        this.yourTurn = yourTurn;
        setTriggerPhrase("Whenever one or more " + filter + " leave your graveyard" + (yourTurn ? " during your turn" : "") + ", ");
    }

    private CardsLeaveGraveyardTriggeredAbility(final CardsLeaveGraveyardTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.yourTurn = ability.yourTurn;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getFromZone() != Zone.GRAVEYARD || event.getToZone() == Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        return card != null
                && card.isOwnedBy(getControllerId())
                && filter.match(card, getControllerId(), this, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (yourTurn && !isControlledBy(game.getActivePlayerId())) {
            return false;
        }
        ZoneChangeBatchEvent batch = (ZoneChangeBatchEvent) event;
        // 603.10a: leaves-the-graveyard abilities look back to the game state immediately before the event.
        // A source that itself enters the battlefield from another zone in this batch was not on the
        // battlefield before it (e.g. it sat in the graveyard), so its ability wasn't functioning and can't trigger.
        if (batch.getEvents().stream().anyMatch(e -> getSourceId().equals(e.getTargetId())
                && e.getFromZone() != Zone.BATTLEFIELD
                && e.getToZone() == Zone.BATTLEFIELD)) {
            return false;
        }
        return !getFilteredEvents(batch, game).isEmpty();
    }

    @Override
    public CardsLeaveGraveyardTriggeredAbility copy() {
        return new CardsLeaveGraveyardTriggeredAbility(this);
    }
}
