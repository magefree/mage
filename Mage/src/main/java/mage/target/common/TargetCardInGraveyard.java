

package mage.target.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInGraveyard extends TargetCard {

    public TargetCardInGraveyard() {
        this(1, 1, new FilterCard("card from a graveyard"));
    }

    public TargetCardInGraveyard(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInGraveyard(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter);
    }

    public TargetCardInGraveyard(final TargetCardInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        return card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD && filter.match(card, game);
    }

    @Override
    public TargetCardInGraveyard copy() {
        return new TargetCardInGraveyard(this);
    }

}
