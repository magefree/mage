package mage.target;

import mage.MageItem;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCard extends TargetObject {

    // all targets will be filtered by one zone, don't use "multi-zone" filter (if you want then override all canTarget and possible methods)
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
     * Checks if there are enough {@link Card cards} in the appropriate zone that the player can choose from among them
     * or if they are autochosen since there are fewer than the minimum number.
     *
     * @param sourceControllerId - controller of the target event source
     * @param source
     * @param game
     * @return - true if enough valid {@link Card} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        UUID sourceId = source != null ? source.getSourceId() : null;
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
                        for (Card card : player.getHand().getCards(filter, sourceControllerId, source, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets++;
                                if (possibleTargets >= this.minNumberOfTargets) {
                                    return true;
                                }
                            }
                        }
                        break;
                    case GRAVEYARD:
                        for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets++;
                                if (possibleTargets >= this.minNumberOfTargets) {
                                    return true;
                                }
                            }
                        }
                        break;
                    case LIBRARY:
                        for (Card card : player.getLibrary().getUniqueCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
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
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                if (filter.match(card, player.getId(), game)) {
                                    possibleTargets++;
                                    if (possibleTargets >= this.minNumberOfTargets) {
                                        return true;
                                    }
                                }
                            }
                        }
                        break;
                    case COMMAND:
                        List<Card> possibleCards = game.getCommandersIds(player, CommanderCardType.ANY, false).stream()
                                .map(game::getCard)
                                .filter(Objects::nonNull)
                                .filter(card -> game.getState().getZone(card.getId()).equals(Zone.COMMAND))
                                .filter(card -> filter.match(card, sourceControllerId, source, game))
                                .collect(Collectors.toList());
                        for (Card card : possibleCards) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets++;
                                if (possibleTargets >= this.minNumberOfTargets) {
                                    return true;
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
        return canChoose(sourceControllerId, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                switch (zone) {
                    case HAND:
                        for (Card card : player.getHand().getCards(filter, sourceControllerId, source, game)) {
                            // TODO: Why for sourceId == null?
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets.add(card.getId());
                            }
                        }
                        break;
                    case GRAVEYARD:
                        for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets.add(card.getId());
                            }
                        }
                        break;
                    case LIBRARY:
                        for (Card card : player.getLibrary().getUniqueCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                if (filter.match(card, sourceControllerId, source, game)) {
                                    possibleTargets.add(card.getId());
                                }
                            }
                        }
                        break;
                    case EXILED:
                        for (Card card : game.getExile().getPermanentExile().getCards(game)) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                if (filter.match(card, sourceControllerId, source, game)) {
                                    possibleTargets.add(card.getId());
                                }
                            }
                        }
                        break;
                    case COMMAND:
                        List<Card> possibleCards = game.getCommandersIds(player, CommanderCardType.ANY, false).stream()
                                .map(game::getCard)
                                .filter(Objects::nonNull)
                                .filter(card -> game.getState().getZone(card.getId()).equals(Zone.COMMAND))
                                .filter(card -> filter.match(card, sourceControllerId, source, game))
                                .collect(Collectors.toList());
                        for (Card card : possibleCards) {
                            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                                possibleTargets.add(card.getId());
                            }
                        }
                        break;
                }
            }
        }
        return possibleTargets;
    }

    public Set<UUID> possibleTargets(UUID sourceControllerId, Cards cards, Ability source, Game game) {
        return cards.getCards(filter, sourceControllerId, source, game).stream().map(MageItem::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, (Ability) null, game);
    }

    // TODO: check all class targets, if it override canTarget then make sure it override ALL 3 METHODS with canTarget and possibleTargets (method with cards doesn't need)

    @Override
    public boolean canTarget(UUID id, Game game) {
        // copy-pasted from super but with card instead object
        Card card = game.getCard(id);
        return card != null
                && zone != null && zone.match(game.getState().getZone(id))
                && getFilter() != null && getFilter().match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(id, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        return card != null
                && zone != null && zone.match(game.getState().getZone(id))
                && getFilter() != null && getFilter().match(card, playerId, source, game);
    }

    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        return cards.contains(id) && canTarget(playerId, id, source, game);
    }

    @Override
    public TargetCard copy() {
        return new TargetCard(this);
    }

}
