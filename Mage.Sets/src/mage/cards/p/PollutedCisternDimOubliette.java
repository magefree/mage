package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PollutedCisternDimOubliette extends RoomCard {

    public PollutedCisternDimOubliette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{1}{B}", "{4}{B}");

        // Polluted Cistern
        // Whenever one or more cards are put into your graveyard from your library, each opponent loses 1 life for each card type among those cards.
        this.getLeftHalfCard().addAbility(new PollutedCisternTriggeredAbility());

        // Dim Oubliette
        // When you unlock this door, mill three cards, then return a creature card from your graveyard to the battlefield.
        Ability ability = new UnlockThisDoorTriggeredAbility(new MillCardsControllerEffect(3), false, false);
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false,
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.BATTLEFIELD).concatBy(", then"));
        this.getRightHalfCard().addAbility(ability);
    }

    private PollutedCisternDimOubliette(final PollutedCisternDimOubliette card) {
        super(card);
    }

    @Override
    public PollutedCisternDimOubliette copy() {
        return new PollutedCisternDimOubliette(this);
    }
}

class PollutedCisternTriggeredAbility extends TriggeredAbilityImpl {

    private enum PollutedCisternValue implements DynamicValue {
        instance;

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            Set<Card> cards = (Set<Card>) effect.getValue("cards");
            return cards != null
                    ? cards
                    .stream()
                    .map(card -> card.getCardType(game))
                    .flatMap(Collection::stream)
                    .distinct()
                    .mapToInt(x -> 1)
                    .sum()
                    : 0;
        }

        @Override
        public PollutedCisternValue copy() {
            return this;
        }

        @Override
        public String getMessage() {
            return "";
        }

        @Override
        public String toString() {
            return "1";
        }
    }

    PollutedCisternTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(PollutedCisternValue.instance)
                .setText("each opponent loses 1 life for each card type among those cards"));
        this.setTriggerPhrase("Whenever one or more cards are put into your graveyard from your library, ");
    }

    private PollutedCisternTriggeredAbility(final PollutedCisternTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PollutedCisternTriggeredAbility copy() {
        return new PollutedCisternTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (!Zone.GRAVEYARD.match(zEvent.getToZone())
                || !Zone.LIBRARY.match(zEvent.getFromZone())
                || !isControlledBy(zEvent.getPlayerId())) {
            return false;
        }
        this.getAllEffects().setValue("cards", zEvent.getCards());
        return true;
    }
}
