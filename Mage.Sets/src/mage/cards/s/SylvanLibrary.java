package mage.cards.s;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class SylvanLibrary extends CardImpl {

    public SylvanLibrary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of your draw step, you may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new SylvanLibraryEffect(), TargetController.YOU, true),
                new SylvanLibraryCardsDrawnThisTurnWatcher());

    }

    private SylvanLibrary(final SylvanLibrary card) {
        super(card);
    }

    @Override
    public SylvanLibrary copy() {
        return new SylvanLibrary(this);
    }
}

class SylvanLibraryEffect extends OneShotEffect {

    public SylvanLibraryEffect() {
        super(Outcome.LoseLife);
        this.staticText = "you may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library";
    }

    public SylvanLibraryEffect(final SylvanLibraryEffect effect) {
        super(effect);
    }

    @Override
    public SylvanLibraryEffect copy() {
        return new SylvanLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game);
            SylvanLibraryCardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(SylvanLibraryCardsDrawnThisTurnWatcher.class);
            if (watcher != null) {
                Cards cards = new CardsImpl();
                Set<UUID> cardsDrawnThisTurn = watcher.getCardsDrawnThisTurn(controller.getId());
                for (UUID cardId : controller.getHand()) {
                    if (cardsDrawnThisTurn != null && cardsDrawnThisTurn.contains(cardId)) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
                int numberOfTargets = Math.min(2, cards.size());
                if (numberOfTargets > 0) {
                    FilterCard filter = new FilterCard(numberOfTargets + " cards of cards drawn this turn");
                    filter.add(new CardIdPredicate(cards));
                    TargetCardInHand target = new TargetCardInHand(numberOfTargets, filter);
                    controller.choose(outcome, target, source, game);

                    Cards cardsPutBack = new CardsImpl();
                    for (UUID cardId : target.getTargets()) {
                        Card card = cards.get(cardId, game);
                        if (card != null) {
                            if (controller.canPayLifeCost(source)
                                    && controller.getLife() >= 4
                                    && controller.chooseUse(outcome, "Pay 4 life for " + card.getLogName() + "? (Otherwise it's put on top of your library)", source, game)) {
                                controller.loseLife(4, game, source, false);
                                game.informPlayers(controller.getLogName() + " pays 4 life to keep a card on hand");
                            } else {
                                cardsPutBack.add(card);
                            }
                        }
                    }
                    controller.putCardsOnTopOfLibrary(cardsPutBack, game, source, true);
                }
            }
            return true;
        }
        return false;
    }
}

class SylvanLibraryCardsDrawnThisTurnWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> cardsDrawnThisTurn = new HashMap<>();

    public SylvanLibraryCardsDrawnThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {

            Set<UUID> cardsDrawn = getCardsDrawnThisTurn(event.getPlayerId());
            cardsDrawn.add(event.getTargetId());
            cardsDrawnThisTurn.put(event.getPlayerId(), cardsDrawn);
        }
    }

    public Set<UUID> getCardsDrawnThisTurn(UUID playerId) {
        return cardsDrawnThisTurn.getOrDefault(playerId, new LinkedHashSet<>());
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawnThisTurn.clear();
    }
}

class CardIdPredicate implements Predicate<MageObject> {

    private final Cards cardsId;

    public CardIdPredicate(Cards cardsId) {
        this.cardsId = cardsId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        for (UUID uuid : cardsId) {
            if (uuid.equals(input.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CardsId";
    }
}
