package mage.watchers.common;

import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Counts cards that was moved to exile zone by any way
 * <p>
 * Can contain multiple instances of the same card (if it was moved multiple times per turn)
 *
 * @author Susucr, JayDi85
 */
public class CardsExiledThisTurnWatcher extends Watcher {

    private final List<UUID> exiledCards = new ArrayList<>();

    public CardsExiledThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.EXILED) {
            this.exiledCards.add(event.getTargetId());
        }
    }

    public int getCountCardsExiledThisTurn() {
        return this.exiledCards.size();
    }

    public List<Card> getCardsExiledThisTurn(Game game) {
        return this.exiledCards.stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        super.reset();
        this.exiledCards.clear();
    }
}
