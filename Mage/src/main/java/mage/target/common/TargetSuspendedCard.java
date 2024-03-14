package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.common.FilterSuspendedCard;
import mage.game.Game;
import mage.target.TargetImpl;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author skiwkr
 */
public class TargetSuspendedCard extends TargetImpl {

    protected final FilterSuspendedCard filter;

    public TargetSuspendedCard() {
        this(new FilterSuspendedCard(), false);
    }

    public TargetSuspendedCard(FilterSuspendedCard filter, boolean notTarget) {
        this(filter, notTarget, 1, 1);
    }

    public TargetSuspendedCard(FilterSuspendedCard filter, boolean notTarget, int minTargets, int maxTargets) {
        super(notTarget);
        this.filter = filter;
        this.zone = Zone.ALL;
        this.targetName = filter.getMessage();
        this.minNumberOfTargets = minTargets;
        this.maxNumberOfTargets = maxTargets;
    }

    protected TargetSuspendedCard(final TargetSuspendedCard target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterSuspendedCard getFilter() {
        return this.filter;
    }

    @Override
    public TargetSuspendedCard copy() {
        return new TargetSuspendedCard(this);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        for (Card card : game.getExile().getAllCards(game, source.getControllerId())) {
            if (filter.match(card, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>(20);
        for (Card card : game.getExile().getAllCards(game, source.getControllerId())) {
            if (filter.match(card, sourceControllerId, source, game)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Card card = game.getExile().getCard(id, game);
        return filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getExile().getCard(id, game);
        return filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return this.canTarget(id, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return this.canChoose(sourceControllerId, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(sourceControllerId, null, game);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : this.getTargets()) {
                Card card = game.getExile().getCard(targetId, game);
                if (card != null) {
                    sb.append(card.getLogName()).append(' ');
                }
            }
        return sb.toString().trim();
    }
}
