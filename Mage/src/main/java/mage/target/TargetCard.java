
package mage.target;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageItem;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCard extends TargetObject {

    protected final FilterCard filter;

    protected TargetCard(Zone zone) {
        this(1, 1, zone, new FilterCard());
    }

    public TargetCard(Zone zone, FilterCard filter) {
        this(1, 1, zone, filter);
    }

    public TargetCard(int numTargets, Zone zone, FilterCard filter) {
        this(numTargets, numTargets, zone, filter);
    }

    public TargetCard(int minNumTargets, int maxNumTargets, Zone zone, FilterCard filter) {
        super(minNumTargets, maxNumTargets, zone, false);
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetCard(final TargetCard target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterCard getFilter() {
        return this.filter;
    }

    /**
     * Checks if there are enough {@link Card} that can be chosen.
     *
     * @param sourceId - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid {@link Card} exist
     */
    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int possibleTargets = 0;
        if (getNumberOfTargets() == 0) { // if 0 target is valid, the canChoose is always true
            return true;
        }
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (this.minNumberOfTargets == 0) {
                    return true;
                }
                switch (zone) {
                    case HAND:
                        for (Card card : player.getHand().getCards(filter, sourceId, sourceControllerId, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                possibleTargets++;
                                if (possibleTargets >= this.minNumberOfTargets) {
                                    return true;
                                }
                            }
                        }
                        break;
                    case GRAVEYARD:
                        for (Card card : player.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                possibleTargets++;
                                if (possibleTargets >= this.minNumberOfTargets) {
                                    return true;
                                }
                            }
                        }
                        break;
                    case LIBRARY:
                        for (Card card : player.getLibrary().getUniqueCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                if (filter.match(card, game)) {
                                    possibleTargets++;
                                    if (possibleTargets >= this.minNumberOfTargets) {
                                        return true;
                                    }
                                }
                            }
                        }
                        break;
                    case EXILED:
                        for (Card card : game.getExile().getPermanentExile().getCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                if (filter.match(card, player.getId(), game)) {
                                    possibleTargets++;
                                    if (possibleTargets >= this.minNumberOfTargets) {
                                        return true;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link Card} that can be selected.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Card} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(null, sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                switch (zone) {
                    case HAND:
                        for (Card card : player.getHand().getCards(filter, sourceId, sourceControllerId, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                possibleTargets.add(card.getId());
                            }
                        }
                        break;
                    case GRAVEYARD:
                        for (Card card : player.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                possibleTargets.add(card.getId());
                            }
                        }
                        break;
                    case LIBRARY:
                        for (Card card : player.getLibrary().getUniqueCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                if (filter.match(card, game)) {
                                    possibleTargets.add(card.getId());
                                }
                            }
                        }
                        break;
                    case EXILED:
                        for (Card card : game.getExile().getPermanentExile().getCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                                if (filter.match(card, player.getId(), game)) {
                                    possibleTargets.add(card.getId());
                                }
                            }
                        }
                        break;
                }
            }
        }
        return possibleTargets;
    }

    public Set<UUID> possibleTargets(UUID sourceControllerId, Cards cards, Game game) {
        return cards.getCards(filter,game).stream().map(MageItem::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return possibleTargets(null, sourceControllerId, game);
    }

    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        return card != null && filter.match(card, game);
    }

    @Override
    public TargetCard copy() {
        return new TargetCard(this);
    }

}
