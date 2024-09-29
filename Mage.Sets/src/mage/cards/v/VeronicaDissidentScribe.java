package mage.cards.v;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RummageEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.JunkToken;
import mage.watchers.Watcher;

/**
 * @author Cguy7777
 */
public final class VeronicaDissidentScribe extends CardImpl {

    public VeronicaDissidentScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Veronica, Dissident Scribe attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new RummageEffect()));

        // Whenever you discard one or more nonland cards for the first time each turn, create a Junk token.
        this.addAbility(new VeronicaDissidentScribeTriggeredAbility());
    }

    private VeronicaDissidentScribe(final VeronicaDissidentScribe card) {
        super(card);
    }

    @Override
    public VeronicaDissidentScribe copy() {
        return new VeronicaDissidentScribe(this);
    }
}

class VeronicaDissidentScribeTriggeredAbility extends TriggeredAbilityImpl {

    VeronicaDissidentScribeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new JunkToken()));
        this.addWatcher(new VeronicaDissidentScribeWatcher());
        setTriggerPhrase("Whenever you discard one or more nonland cards for the first time each turn, ");
    }

    private VeronicaDissidentScribeTriggeredAbility(final VeronicaDissidentScribeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        VeronicaDissidentScribeWatcher watcher = game.getState().getWatcher(VeronicaDissidentScribeWatcher.class);

        DiscardedCardsEvent discardedCardsEvent = (DiscardedCardsEvent) event;
        boolean nonlandCardsWereDiscarded
                = !discardedCardsEvent.getDiscardedCards().getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty();

        return watcher != null
                && watcher.numTimesDiscardedNonlandCardThisTurn(event.getPlayerId()) <= 1
                && event.getPlayerId().equals(getControllerId())
                && event.getAmount() != 0
                && nonlandCardsWereDiscarded;
    }

    @Override
    public VeronicaDissidentScribeTriggeredAbility copy() {
        return new VeronicaDissidentScribeTriggeredAbility(this);
    }
}

class VeronicaDissidentScribeWatcher extends Watcher {

    private final Map<UUID, Integer> timesNonlandCardsDiscardedThisTurn = new HashMap<>();

    VeronicaDissidentScribeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARDS && event.getAmount() > 0) {
            DiscardedCardsEvent discardedCardsEvent = (DiscardedCardsEvent) event;
            boolean nonlandCardsWereDiscarded
                    = !discardedCardsEvent.getDiscardedCards().getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty();
            if (nonlandCardsWereDiscarded) {
                timesNonlandCardsDiscardedThisTurn.merge(event.getPlayerId(), 1, Integer::sum);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.timesNonlandCardsDiscardedThisTurn.clear();
    }

    int numTimesDiscardedNonlandCardThisTurn(UUID playerId) {
        return timesNonlandCardsDiscardedThisTurn.getOrDefault(playerId, 0);
    }
}
