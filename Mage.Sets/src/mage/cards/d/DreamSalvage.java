
package mage.cards.d;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public final class DreamSalvage extends CardImpl {

    public DreamSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/B}");


        // Draw cards equal to the number of cards target opponent discarded this turn.
        this.getSpellAbility().addEffect(new DreamSalvageEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addWatcher(new CardsDiscardedThisTurnWatcher());

    }

    public DreamSalvage(final DreamSalvage card) {
        super(card);
    }

    @Override
    public DreamSalvage copy() {
        return new DreamSalvage(this);
    }
}

class CardsDiscardedThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsDiscardedThisTurn = new HashMap<>();

    public CardsDiscardedThisTurnWatcher() {
        super(CardsDiscardedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CardsDiscardedThisTurnWatcher(final CardsDiscardedThisTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDiscardedThisTurn.entrySet()) {
            amountOfCardsDiscardedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                amountOfCardsDiscardedThisTurn.put(playerId, getAmountCardsDiscarded(playerId) + 1);
            }
        }
    }

    public int getAmountCardsDiscarded(UUID playerId) {
        return amountOfCardsDiscardedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        amountOfCardsDiscardedThisTurn.clear();
    }

    @Override
    public CardsDiscardedThisTurnWatcher copy() {
        return new CardsDiscardedThisTurnWatcher(this);
    }
}

class DreamSalvageEffect extends OneShotEffect {

    public DreamSalvageEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the number of cards target opponent discarded this turn";
    }

    public DreamSalvageEffect(final DreamSalvageEffect effect) {
        super(effect);
    }

    @Override
    public DreamSalvageEffect copy() {
        return new DreamSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsDiscardedThisTurnWatcher watcher = (CardsDiscardedThisTurnWatcher) game.getState().getWatchers().get(CardsDiscardedThisTurnWatcher.class.getSimpleName());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetOpponent != null
                && controller != null
                && watcher != null
                && watcher.getAmountCardsDiscarded(targetOpponent.getId()) > 0) {
            controller.drawCards(watcher.getAmountCardsDiscarded(targetOpponent.getId()), game);
            return true;
        }
        return false;
    }
}
