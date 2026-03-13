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
import java.util.UUID;

/**
 * Counts amount of cards drawn this turn by players. Asks players about Miracle
 * ability to be activated if it the first card drawn this turn.
 *
 * @author noxx
 */
public class MiracleWatcher extends Watcher {

    private final Map<UUID, UUID> playerDrawEventMap = new HashMap<>();

    public MiracleWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // inital card draws do not trigger miracle so check that phase != null
        if (game.getPhase() != null
                && event.getType() == GameEvent.EventType.DREW_CARD) {
            playerDrawEventMap.putIfAbsent(event.getPlayerId(), event.getId());
            checkMiracleAbility(event, game);
        }
    }

    private void checkMiracleAbility(GameEvent event, Game game) {
        if (!event.getId().equals(playerDrawEventMap.get(event.getPlayerId()))) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return;
        }
        Player controller = game.getPlayer(card.getOwnerId());
        if (controller == null) {
            return;
        }
        for (Ability ability : card.getAbilities(game)) {
            if (!(ability instanceof MiracleAbility)) {
                continue;
            }
            String miracleCost = ((MiracleAbility) ability).getMiracleCost();
            Cards cards = new CardsImpl(card);
            controller.lookAtCards("Miracle", cards, game);
            if (!controller.chooseUse(
                    Outcome.Benefit, "Reveal " + card.getLogName() +
                            " to cast for " + miracleCost + " with Miracle?", ability, game
            )) {
                continue;
            }
            controller.revealCards("Miracle", cards, game);
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.MIRACLE_CARD_REVEALED, card.getId(),
                    ability, controller.getId(), "" + ability.getId(), 0
            ));
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerDrawEventMap.clear();
    }
}
