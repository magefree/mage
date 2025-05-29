package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public class TargetCardInYourGraveyardOrExile extends TargetCardInYourGraveyard {

    private final FilterCard filterExile;

    public TargetCardInYourGraveyardOrExile(FilterCard filterGraveyard, FilterCard filterExile) {
        super(1, 1, filterGraveyard);
        this.filterExile = filterExile;
        this.targetName = filterGraveyard.getMessage() + " or " + filterExile.getMessage();
    }

    protected TargetCardInYourGraveyardOrExile(final TargetCardInYourGraveyardOrExile target) {
        super(target);
        this.filterExile = target.filterExile;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        // in your graveyard:
        if (super.canChoose(sourceControllerId, source, game)) {
            return true;
        }
        // in exile:
        for (Card card : game.getExile().getAllCardsByRange(game, sourceControllerId)) {
            if (filterExile.match(card, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        // in your graveyard:
        if (super.canTarget(playerId, id, source, game)) {
            return true;
        }
        Card card = game.getCard(id);
        // in exile:
        return card != null
                && Zone.EXILED.equals(game.getState().getZone(id))
                && filterExile.match(card, playerId, source, game);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        return this.canTarget(null, id, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(sourceControllerId, (Ability) null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // in your graveyard:
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        // in exile:
        for (Card card : game.getExile().getAllCardsByRange(game, sourceControllerId)) {
            if (filterExile.match(card, sourceControllerId, source, game)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCardInYourGraveyardOrExile copy() {
        return new TargetCardInYourGraveyardOrExile(this);
    }
}
