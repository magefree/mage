package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class TargetCardInTargetPlayersGraveyard extends TargetCardInGraveyard {

    public TargetCardInTargetPlayersGraveyard(int targets) {
        super(0, targets, StaticFilters.FILTER_CARD);
    }

    private TargetCardInTargetPlayersGraveyard(final TargetCardInTargetPlayersGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (!super.canTarget(id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null && card.isOwnedBy(source.getFirstTarget());
    }

    @Override
    public TargetCardInTargetPlayersGraveyard copy() {
        return new TargetCardInTargetPlayersGraveyard(this);
    }
}
