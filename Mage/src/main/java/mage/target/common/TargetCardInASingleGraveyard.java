package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author LevelX2
 */

public class TargetCardInASingleGraveyard extends TargetCard {

    public TargetCardInASingleGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        // workaround to add extra message to final ability text
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter.copy().withMessage(filter.getMessage() + " from a single graveyard"));
    }

    public TargetCardInASingleGraveyard(final TargetCardInASingleGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = this.getFirstTarget();
        if (firstTarget != null) {
            Card card = game.getCard(firstTarget);
            Card targetCard = game.getCard(id);
            if (card == null || targetCard == null
                    || !card.isOwnedBy(targetCard.getOwnerId())) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }

    @Override
    public TargetCardInASingleGraveyard copy() {
        return new TargetCardInASingleGraveyard(this);
    }

}
