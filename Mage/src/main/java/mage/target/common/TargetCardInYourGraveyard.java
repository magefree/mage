package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInYourGraveyard extends TargetCard {

    public TargetCardInYourGraveyard() {
        this(1, 1, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD);
    }

    public TargetCardInYourGraveyard(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInYourGraveyard(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInYourGraveyard(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD);
    }

    public TargetCardInYourGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, false);
    }

    public TargetCardInYourGraveyard(int minNumTarget, int maxNumTargets, FilterCard filter, boolean notTarget) {
        super(minNumTarget, maxNumTargets, Zone.GRAVEYARD, filter);
        this.setNotTarget(notTarget);
    }

    public TargetCardInYourGraveyard(final TargetCardInYourGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            if (game.getPlayer(source.getControllerId()).getGraveyard().contains(id)) {
                return filter.match(card, source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            if (game.getPlayer(playerId).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(sourceControllerId);
        for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
            if (source.getSourceId() == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, source.getSourceId(), sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Cards cards, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(sourceControllerId);
        for (Card card : cards.getCards(filter, game)) {
            if (player.getGraveyard().getCards(game).contains(card)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
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
        return game.getPlayer(sourceControllerId).getGraveyard().count(filter, game) >= this.minNumberOfTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Player player = game.getPlayer(sourceControllerId);
        if (player != null) {
            if (this.minNumberOfTargets == 0) {
                return true;
            }
            int possibleTargets = 0;
            for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                if (source.getSourceId() == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, source.getSourceId(), sourceControllerId))) {
                    possibleTargets++;
                    if (possibleTargets >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TargetCardInYourGraveyard copy() {
        return new TargetCardInYourGraveyard(this);
    }

}
