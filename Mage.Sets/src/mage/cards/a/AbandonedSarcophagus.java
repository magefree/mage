package mage.cards.a;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class AbandonedSarcophagus extends CardImpl {

    public AbandonedSarcophagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may cast nonland cards with cycling from your graveyard.
        FilterCard filter = new FilterCard("nonland cards with cycling");
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new AbilityPredicate(CyclingAbility.class));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PlayFromNotOwnHandZoneAllEffect(filter,
                        Zone.GRAVEYARD, true, TargetController.YOU, Duration.WhileOnBattlefield)
                        .setText("You may cast nonland cards with cycling from your graveyard"))
        );

        // If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbandonedSarcophagusReplacementEffect()), new AbandonedSarcophagusWatcher());

    }

    public AbandonedSarcophagus(final AbandonedSarcophagus card) {
        super(card);
    }

    @Override
    public AbandonedSarcophagus copy() {
        return new AbandonedSarcophagus(this);
    }
}

class AbandonedSarcophagusReplacementEffect extends ReplacementEffectImpl {

    boolean cardHasCycling;
    boolean cardWasCycledThisTurn;

    public AbandonedSarcophagusReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead";
    }

    public AbandonedSarcophagusReplacementEffect(final AbandonedSarcophagusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbandonedSarcophagusReplacementEffect copy() {
        return new AbandonedSarcophagusReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                return controller.moveCards(permanent, Zone.EXILED, source, game);
            }
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                return controller.moveCards(card, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        cardWasCycledThisTurn = false;
        cardHasCycling = false;
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && !game.isSimulation()) {
            Player controller = game.getPlayer(source.getControllerId());
            AbandonedSarcophagusWatcher watcher = (AbandonedSarcophagusWatcher) game.getState().getWatchers().get(AbandonedSarcophagusWatcher.class.getSimpleName());
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && watcher != null
                    && card.isOwnedBy(controller.getId())) {
                for (Ability ability : card.getAbilities()) {
                    if (ability instanceof CyclingAbility) {
                        cardHasCycling = true;
                    }
                }
                Cards cards = watcher.getCardsCycledThisTurn(controller.getId());
                for (Card c : cards.getCards(game)) {
                    if (c == card) {
                        cardWasCycledThisTurn = true;
                        watcher.getCardsCycledThisTurn(controller.getId()).remove(card); //remove reference to the card as it is no longer needed
                    }
                }
                return (!cardWasCycledThisTurn
                        && cardHasCycling);
            }
        }
        return false;
    }
}

class AbandonedSarcophagusWatcher extends Watcher {

    private final Map<UUID, Cards> cycledCardsThisTurn = new HashMap<>();

    public AbandonedSarcophagusWatcher() {
        super(AbandonedSarcophagusWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AbandonedSarcophagusWatcher(final AbandonedSarcophagusWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Cards> entry : watcher.cycledCardsThisTurn.entrySet()) {
            cycledCardsThisTurn.put(entry.getKey(), entry.getValue().copy());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CYCLE_CARD
                && !game.isSimulation()) {
            Card card = game.getCard(event.getSourceId());
            Player controller = game.getPlayer(event.getPlayerId());
            if (card != null
                    && controller != null
                    && card.isOwnedBy(controller.getId())) {
                Cards c = getCardsCycledThisTurn(event.getPlayerId());
                c.add(card);
                cycledCardsThisTurn.put(event.getPlayerId(), c);
            }
        }
    }

    public Cards getCardsCycledThisTurn(UUID playerId) {
        return cycledCardsThisTurn.getOrDefault(playerId, new CardsImpl());
    }

    @Override
    public void reset() {
        super.reset();
        cycledCardsThisTurn.clear();
    }

    @Override
    public AbandonedSarcophagusWatcher copy() {
        return new AbandonedSarcophagusWatcher(this);
    }
}
