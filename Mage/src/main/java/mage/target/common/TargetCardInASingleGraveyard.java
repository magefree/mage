package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */

public class TargetCardInASingleGraveyard extends TargetCard {

    public TargetCardInASingleGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        // workaround to add extra message to final ability text
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter.copy().withMessage(filter.getMessage() + " from a single graveyard"));
    }

    private TargetCardInASingleGraveyard(final TargetCardInASingleGraveyard target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Card firstTarget = game.getCard(source.getFirstTarget());

        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            if (firstTarget == null) {
                // playable or not selected
                // use any player
            } else {
                // already selected
                // use from same player
                if (!playerId.equals(firstTarget.getOwnerId())) {
                    continue;
                }
            }

            for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                possibleTargets.add(card.getId());
            }
        }

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public TargetCardInASingleGraveyard copy() {
        return new TargetCardInASingleGraveyard(this);
    }

}
