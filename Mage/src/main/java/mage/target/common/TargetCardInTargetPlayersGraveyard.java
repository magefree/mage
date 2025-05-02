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

    private final int targetPlayerIndex;

    public TargetCardInTargetPlayersGraveyard(int targets) {
        this(targets, 0);
    }

    public TargetCardInTargetPlayersGraveyard(int targets, int targetPlayerIndex) {
        super(0, targets, StaticFilters.FILTER_CARD);
        this.targetPlayerIndex = targetPlayerIndex;
    }

    private TargetCardInTargetPlayersGraveyard(final TargetCardInTargetPlayersGraveyard target) {
        super(target);
        this.targetPlayerIndex = target.targetPlayerIndex;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (!super.canTarget(id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null && card.isOwnedBy(source.getTargets().get(targetPlayerIndex).getFirstTarget());
    }

    @Override
    public TargetCardInTargetPlayersGraveyard copy() {
        return new TargetCardInTargetPlayersGraveyard(this);
    }
}
