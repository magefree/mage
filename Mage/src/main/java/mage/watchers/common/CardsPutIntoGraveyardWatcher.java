package mage.watchers.common;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Counts how many cards are put into each player's graveyard this turn.
 * Keeps track of the UUIDs of the cards that went to graveyard this turn.
 * from the battlefield, from anywhere other both from anywhere and from only the battlefield.
 *
 * @author LevelX2
 */
public class CardsPutIntoGraveyardWatcher extends Watcher {

    // Number of cards that have entered each players graveyards
    private final Map<UUID, Integer> amountOfCardsThisTurn = new HashMap<>();
    // UUID of cards that entered the graveyard from the battlefield
    private final Set<MageObjectReference> cardsPutIntoGraveyardFromBattlefield = new HashSet<>();
    // UUID of cards that entered the graveyard from everywhere other than the battlefield
    private final Set<MageObjectReference> cardsPutIntoGraveyardFromEverywhereElse = new HashSet<>();

    public CardsPutIntoGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || ((ZoneChangeEvent) event).getToZone() != Zone.GRAVEYARD) {
            return;
        }

        UUID playerId = event.getPlayerId();
        if (playerId == null || game.getCard(event.getTargetId()) == null) {
            return;
        }

        amountOfCardsThisTurn.compute(playerId, (k, amount) -> amount == null ? 1 : Integer.sum(amount, 1));

        if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            cardsPutIntoGraveyardFromBattlefield.add(new MageObjectReference(((ZoneChangeEvent) event).getTarget(), game, 1));
        } else {
            cardsPutIntoGraveyardFromEverywhereElse.add(new MageObjectReference(((ZoneChangeEvent) event).getTarget(), game, 1));
        }
    }

    /**
     * The number of cards that were put into a specific player's graveyard this turn.
     *
     * @param playerId The player's UUID.
     * @return The number of cards.
     */
    public int getAmountCardsPutToGraveyard(UUID playerId) {
        return amountOfCardsThisTurn.getOrDefault(playerId, 0);
    }

    /**
     * The cards put into any graveyard from the battelfield this turn.
     *
     * @param game The game to check for.
     * @return A set containing the card objects.
     */
    public Set<Card> getCardsPutIntoGraveyardFromBattlefield(Game game) {
        return cardsPutIntoGraveyardFromBattlefield.stream().map(mor -> mor.getCard(game)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * The cards put into any graveyard from anywhere other than the battelfield this turn.
     *
     * @param game The game to check for.
     * @return A set containing the card objects.
     */
    public Set<Card> getCardsPutIntoGraveyardNotFromBattlefield(Game game) {
        return cardsPutIntoGraveyardFromEverywhereElse.stream().map(mor -> mor.getCard(game)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * The cards put into any graveyard from anywhere this turn.
     *
     * @param game The game to check for.
     * @return A set containing the card objects.
     */
    public Set<Card> getCardsPutIntoGraveyardFromAnywhere(Game game) {
        Set<Card> cardsPutIntoGraveyardFromAnywhere = getCardsPutIntoGraveyardFromBattlefield(game);
        cardsPutIntoGraveyardFromAnywhere.addAll(getCardsPutIntoGraveyardNotFromBattlefield(game));

        return cardsPutIntoGraveyardFromAnywhere;
    }

    /**
     * Check if the passed card was put into the graveyard from the battlefield this turn.
     *
     * @param card The card to check.
     * @param game The game to check for.
     * @return Boolean indicating if the card was put into the graveyard from the battlefield this turn.
     */
    public boolean checkCardFromBattlefield(Card card, Game game) {
        return cardsPutIntoGraveyardFromBattlefield.stream().anyMatch(mor -> mor.refersTo(card, game));
    }

    /**
     * Check if the passed card was put into the graveyard from anywhere other than the battlefield this turn.
     *
     * @param card The card to check.
     * @param game The game to check for.
     * @return Boolean indicating if the card was put into the graveyard from anywhere other than the battlefield this turn.
     */
    public boolean checkCardNotFromBattlefield(Card card, Game game) {
        return cardsPutIntoGraveyardFromEverywhereElse.stream().anyMatch(mor -> mor.refersTo(card, game));
    }

    /**
     * Check if the passed card was put into the graveyard from anywhere this turn.
     *
     * @param card The card to check.
     * @param game The game to check for.
     * @return Boolean indicating if the card was put into the graveyard from anywhere this turn.
     */
    public boolean checkCardFromAnywhere(Card card, Game game) {
        return checkCardFromBattlefield(card, game) || checkCardNotFromBattlefield(card, game);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsThisTurn.clear();
        cardsPutIntoGraveyardFromBattlefield.clear();
        cardsPutIntoGraveyardFromEverywhereElse.clear();
    }
}
