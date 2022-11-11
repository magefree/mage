package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Based on TargetCardInHand
 * @author Alex-Vasile
 */
public class TargetCardInCommandZone extends TargetCard {

    public TargetCardInCommandZone(FilterCard filter) {
        super(1, 1, Zone.COMMAND, filter);
    }

    public TargetCardInCommandZone(final TargetCardInCommandZone targetCardInCommandZone) {
        super(targetCardInCommandZone);
    }

    @Override
    public TargetCardInCommandZone copy() {
        return new TargetCardInCommandZone(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        return game.getState().getZone(id) == Zone.COMMAND
                && game.getState().getPlayersInRange(getTargetController() == null ? playerId : getTargetController(), game).contains(game.getOwnerId(id))
                && filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return possibleTargets;
        }

        Cards cards = new CardsImpl(game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY));
        for (Card card : cards.getCards(filter, sourceControllerId, source, game)) {
            if (source == null || source.getSourceId() == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, source.getSourceId(), sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return false;
        }

        int possibletargets = 0;
        Cards cards = new CardsImpl(game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY));
        for (Card card : cards.getCards(filter, sourceControllerId, source, game)) {
            if (source == null || source.getSourceId() == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, source.getSourceId(), sourceControllerId))) {
                possibletargets++;
                if (possibletargets >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }
}