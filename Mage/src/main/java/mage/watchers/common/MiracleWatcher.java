package mage.watchers.common;

import mage.abilities.Ability;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Counts amount of cards drawn this turn by players. Asks players about Miracle
 * ability to be activated if it the first card drawn this turn.
 *
 * @author noxx
 */
public class MiracleWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<>();

    public MiracleWatcher() {
        super(WatcherScope.GAME);
    }

    public MiracleWatcher(final MiracleWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDrawnThisTurn.entrySet()) {
            amountOfCardsDrawnThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
            return;
        }
        // inital card draws do not trigger miracle so check that phase != null
        if (game.getPhase() != null && event.getType() == GameEvent.EventType.DREW_CARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = 1 + amountOfCardsDrawnThisTurn.getOrDefault(playerId, 0);
                amountOfCardsDrawnThisTurn.put(playerId, amount);
                if (amount == 1) {
                    checkMiracleAbility(event, game);
                }
            }
        }
    }

    private void checkMiracleAbility(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof MiracleAbility) {
                    Player controller = game.getPlayer(ability.getControllerId());
                    if (controller != null) {
                        Cards cards = new CardsImpl(card);
                        controller.lookAtCards("Miracle", cards, game);
                        if (controller.chooseUse(Outcome.Benefit, "Reveal " + card.getLogName() + " to be able to use Miracle?", ability, game)) {
                            controller.revealCards("Miracle", cards, game);
                            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.MIRACLE_CARD_REVEALED, card.getId(), card.getId(), controller.getId()));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsDrawnThisTurn.clear();
    }

    @Override
    public MiracleWatcher copy() {
        return new MiracleWatcher(this);
    }
}
