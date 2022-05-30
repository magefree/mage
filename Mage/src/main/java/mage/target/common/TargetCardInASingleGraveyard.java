package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.*;
import java.util.stream.Collectors;

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
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = this.getFirstTarget();

        // If a card is already targeted, ensure that this new target has the same owner as currently chosen target
        if (firstTarget != null) {
            Card card = game.getCard(firstTarget);
            Card targetCard = game.getCard(id);
            if (card == null || targetCard == null || !card.isOwnedBy(targetCard.getOwnerId())) {
                return false;
            }
        }

        // If it has the same owner (or no target picked) check that it's a valid target with super
        return super.canTarget(id, source, game);
    }

    /**
     * Set of UUIDs of all possible targets
     *
     * @param sourceControllerId    UUID of the ability's controller
     * @param source                Ability which requires the targets
     * @param game                  Current game
     * @return                      Set of the UUIDs of possible targets
     */
    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        UUID sourceId = source != null ? source.getSourceId() : null;

        UUID controllerOfFirstTarget = null;

        // If any targets have been chosen, get the UUID of the owner in order to limit the targets to that owner's graveyard
        if (!targets.isEmpty()) {
            for (UUID cardInGraveyardId : targets.keySet()) {
                Card targetCard = game.getCard(cardInGraveyardId);
                if (targetCard == null) {
                    continue;
                }

                UUID ownerOfCardId = targetCard.getOwnerId();
                controllerOfFirstTarget = ownerOfCardId;
                break; // Only need the first UUID since they will all be the same
            }
        }

        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            // If the playerId of this iteration is not the same as that of any existing target, then continue
            // All cards must be from the same player's graveyard.
            if (controllerOfFirstTarget != null && !playerId.equals(controllerOfFirstTarget)) {
                continue;
            }

            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            for (Card card : player.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                // TODO: Why for sourceId == null?
                if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCardInASingleGraveyard copy() {
        return new TargetCardInASingleGraveyard(this);
    }

}
