package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDiscard extends TargetCard {

    private final UUID playerId;

    public TargetDiscard(UUID playerId) {
        this(1, 1, StaticFilters.FILTER_CARD, playerId);
    }

    public TargetDiscard(FilterCard filter, UUID playerId) {
        this(1, 1, filter, playerId);
    }

    public TargetDiscard(int numTargets, FilterCard filter, UUID playerId) {
        this(numTargets, numTargets, filter, playerId);
    }

    public TargetDiscard(int minNumTargets, int maxNumTargets, FilterCard filter, UUID playerId) {
        super(minNumTargets, maxNumTargets, Zone.HAND, filter.copy());
        this.filter.add(new OwnerIdPredicate(playerId));
        this.playerId = playerId;
        this.targetName = this.filter.getMessage() + " to discard";
    }

    protected TargetDiscard(final TargetDiscard target) {
        super(target);
        this.playerId = target.playerId;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getPlayer(playerId).getHand().get(id, game);
        return filter.match(card, source.getControllerId(), source, game);
    }

    @Override
    public TargetDiscard copy() {
        return new TargetDiscard(this);
    }
}
