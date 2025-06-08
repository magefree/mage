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

    protected TargetCard(final TargetCard target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterCard getFilter() {
        return this.filter;
    }

    @Override
    public TargetCard withNotTarget(boolean notTarget) {
        super.withNotTarget(notTarget);
        return this;
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
        int possibleTargets = 0;
        if (getMinNumberOfTargets() == 0) { // if 0 target is valid, the canChoose is always true
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
                        possibleTargets += countPossibleTargetInHand(game, player, sourceControllerId, source,
                                filter, isNotTarget(), this.minNumberOfTargets - possibleTargets);
                        break;
                    case GRAVEYARD:
                        possibleTargets += countPossibleTargetInGraveyard(game, player, sourceControllerId, source,
                                filter, isNotTarget(), this.minNumberOfTargets - possibleTargets);
                        break;
                    case LIBRARY:
                        possibleTargets += countPossibleTargetInLibrary(game, player, sourceControllerId, source,
                                filter, isNotTarget(), this.minNumberOfTargets - possibleTargets);
                        break;
                    case EXILED:
                        possibleTargets += countPossibleTargetInExile(game, player, sourceControllerId, source,
                                filter, isNotTarget(), this.minNumberOfTargets - possibleTargets);
                        break;
                    case COMMAND:
                        possibleTargets += countPossibleTargetInCommandZone(game, player, sourceControllerId, source,
                                filter, isNotTarget(), this.minNumberOfTargets - possibleTargets);
                        break;
                }
                if (possibleTargets >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * count up to N possible target cards in a player's hand matching the filter
     */
    protected static int countPossibleTargetInHand(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget, int countUpTo) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        int possibleTargets = 0;
        for (Card card : player.getHand().getCards(filter, sourceControllerId, source, game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets++;
                if (possibleTargets >= countUpTo) {
                    return possibleTargets; // early return for faster computation.
                }
            }
        }
        return possibleTargets;
    }

    /**
     * count up to N possible target cards in a player's graveyard matching the filter
     */
    protected static int countPossibleTargetInGraveyard(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget, int countUpTo) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        int possibleTargets = 0;
        for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets++;
                if (possibleTargets >= countUpTo) {
                    return possibleTargets; // early return for faster computation.
                }
            }
        }
        return possibleTargets;
    }

    /**
     * count up to N possible target cards in a player's library matching the filter
     */
    protected static int countPossibleTargetInLibrary(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget, int countUpTo) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        int possibleTargets = 0;
        for (Card card : player.getLibrary().getUniqueCards(game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                if (filter.match(card, game)) {
                    possibleTargets++;
                    if (possibleTargets >= countUpTo) {
                        return possibleTargets; // early return for faster computation.
                    }
                }
            }
        }
        return possibleTargets;
    }

    /**
     * count up to N possible target cards in a player's exile matching the filter
     */
    protected static int countPossibleTargetInExile(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget, int countUpTo) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        int possibleTargets = 0;
        for (Card card : game.getExile().getPermanentExile().getCards(game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                if (filter.match(card, player.getId(), source, game)) {
                    possibleTargets++;
                    if (possibleTargets >= countUpTo) {
                        return possibleTargets; // early return for faster computation.
                    }
                }
            }
        }
        return possibleTargets;
    }

    /**
     * count up to N possible target cards in a player's command zone matching the filter
     */
    protected static int countPossibleTargetInCommandZone(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget, int countUpTo) {
        UUID sourceId = source != null ? source.getSourceId() : null;
        int possibleTargets = 0;
        List<Card> possibleCards = game.getCommandersIds(player, CommanderCardType.ANY, false).stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()).equals(Zone.COMMAND))
                .filter(card -> filter.match(card, sourceControllerId, source, game))
                .collect(Collectors.toList());
        for (Card card : possibleCards) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets++;
                if (possibleTargets >= countUpTo) {
                    return possibleTargets; // early return for faster computation.
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, (Ability) null, game);
    }

    public Set<UUID> possibleTargets(UUID sourceControllerId, Cards cards, Ability source, Game game) {
        return cards.getCards(filter, sourceControllerId, source, game).stream().map(MageItem::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                switch (zone) {
                    case HAND:
                        possibleTargets.addAll(getAllPossibleTargetInHand(game, player, sourceControllerId, source, filter, isNotTarget()));
                        break;
                    case GRAVEYARD:
                        possibleTargets.addAll(getAllPossibleTargetInGraveyard(game, player, sourceControllerId, source, filter, isNotTarget()));
                        break;
                    case LIBRARY:
                        possibleTargets.addAll(getAllPossibleTargetInLibrary(game, player, sourceControllerId, source, filter, isNotTarget()));
                        break;
                    case EXILED:
                        possibleTargets.addAll(getAllPossibleTargetInExile(game, player, sourceControllerId, source, filter, isNotTarget()));
                        break;
                    case COMMAND:
                        possibleTargets.addAll(getAllPossibleTargetInCommandZone(game, player, sourceControllerId, source, filter, isNotTarget()));
                        break;
                }
            }
        }
        return possibleTargets;
    }

    /**
     * set of all matching target in a player's hand
     */
    protected static Set<UUID> getAllPossibleTargetInHand(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        for (Card card : player.getHand().getCards(filter, sourceControllerId, source, game)) {
            // TODO: Why for sourceId == null?
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    /**
     * set of all matching target in a player's hand
     */
    protected static Set<UUID> getAllPossibleTargetInGraveyard(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    /**
     * set of all matching target in a player's hand
     */
    protected static Set<UUID> getAllPossibleTargetInLibrary(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        for (Card card : player.getLibrary().getUniqueCards(game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        return possibleTargets;
    }

    /**
     * set of all matching target for a player in exile
     */
    protected static Set<UUID> getAllPossibleTargetInExile(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        for (Card card : game.getExile().getPermanentExile().getCards(game)) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        return possibleTargets;
    }

    /**
     * set of all matching target in a player's command zone
     */
    protected static Set<UUID> getAllPossibleTargetInCommandZone(Game game, Player player, UUID sourceControllerId, Ability source, FilterCard filter, boolean isNotTarget) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;
        List<Card> possibleCards = game.getCommandersIds(player, CommanderCardType.ANY, false).stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()).equals(Zone.COMMAND))
                .filter(card -> filter.match(card, sourceControllerId, source, game))
                .collect(Collectors.toList());
        for (Card card : possibleCards) {
            if (sourceId == null || isNotTarget || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
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
