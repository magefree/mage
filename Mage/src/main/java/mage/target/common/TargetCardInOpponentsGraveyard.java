package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;


public class TargetCardInOpponentsGraveyard extends TargetCard {

    protected final boolean allFromOneOpponent;

    public TargetCardInOpponentsGraveyard(FilterCard filter) {
        this(1, 1, filter, false);
    }

    public TargetCardInOpponentsGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, false);
    }

    public TargetCardInOpponentsGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter, boolean allFromOneOpponent) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter);
        this.allFromOneOpponent = allFromOneOpponent;
    }

    public TargetCardInOpponentsGraveyard(final TargetCardInOpponentsGraveyard target) {
        super(target);
        this.allFromOneOpponent = target.allFromOneOpponent;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && zone.match(game.getState().getZone(id))) {
            if (game.getPlayer(source.getControllerId()).hasOpponent(card.getOwnerId(), game)) {
                if (allFromOneOpponent && !targets.isEmpty()) {
                    Card firstCard = game.getCard(targets.keySet().iterator().next());
                    if (firstCard != null && !card.isOwnedBy(firstCard.getOwnerId())) {
                        return false;
                    }
                }
                return filter.match(card, source.getId(), playerId, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            if (game.getPlayer(source.getControllerId()).hasOpponent(card.getOwnerId(), game)) {
                if (allFromOneOpponent && !targets.isEmpty()) {
                    Card firstCard = game.getCard(targets.keySet().iterator().next());
                    if (firstCard != null && !card.isOwnedBy(firstCard.getOwnerId())) {
                        return false;
                    }
                }
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(null, sourceControllerId, game);
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
        Player sourceController = game.getPlayer(sourceControllerId);
        for (UUID playerId: game.getState().getPlayersInRange(sourceControllerId, game)) {
            if (!sourceController.hasOpponent(playerId, game)) {
                continue;
            }
            if (this.allFromOneOpponent) {
                possibleTargets = 0;
            }
            if (!playerId.equals(sourceControllerId)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
                        if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                            possibleTargets++;
                            if (possibleTargets >= this.minNumberOfTargets) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Player sourceController = game.getPlayer(sourceControllerId);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            if (!sourceController.hasOpponent(playerId, game)) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Set<UUID> targetsInThisGraveyeard = new HashSet<>();
                for (Card card : player.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
                    if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                        targetsInThisGraveyeard.add(card.getId());
                    }
                }
                // if there is not enough possible targets, the can't be any
                if (this.allFromOneOpponent && targetsInThisGraveyeard.size() < this.minNumberOfTargets) {
                    continue;
                }
                possibleTargets.addAll(targetsInThisGraveyeard);
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCardInOpponentsGraveyard copy() {
        return new TargetCardInOpponentsGraveyard(this);
    }
}
