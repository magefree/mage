package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetCardInGraveyardOrBattlefield extends TargetCard {

    protected final FilterPermanent filterBattlefield;

    public TargetCardInGraveyardOrBattlefield(int minNumTargets, int maxNumTargets, FilterCard filterGraveyard, FilterPermanent filterBattlefield) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filterGraveyard); // zone for card in graveyard, don't change
        this.filterBattlefield = filterBattlefield;
        this.targetName = filter.getMessage()
                + " in a graveyard "
                + (maxNumTargets > 1 ? " and/or " : " or ")
                + this.filterBattlefield.getMessage()
                + " on the battlefield";
    }

    public TargetCardInGraveyardOrBattlefield(final TargetCardInGraveyardOrBattlefield target) {
        super(target);
        this.filterBattlefield = target.filterBattlefield;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        if (!super.canChoose(sourceId, sourceControllerId, game)) {
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterBattlefield, sourceControllerId, game)) {
                if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                        && filterBattlefield.match(permanent, sourceId, sourceControllerId, game)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (!super.canTarget(id, source, game)) { // in graveyard first
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                return filterBattlefield.match(permanent, game);
            }
        }
        return true;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) { // in graveyard first
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                return filterBattlefield.match(permanent, source != null ? source.getSourceId() : null, playerId, game);
            }
        }
        return true;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        if (!super.canTarget(id, game)) { // in graveyard first
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                return filterBattlefield.match(permanent, game);
            }
        }
        return true;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, game); // in graveyard first
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterBattlefield, sourceControllerId, game)) {
            if (filterBattlefield.match(permanent, null, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, sourceControllerId, game); // in graveyard first
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterBattlefield, sourceControllerId, sourceId, game)) {
            if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filterBattlefield.match(permanent, sourceId, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCardInGraveyardOrBattlefield copy() {
        return new TargetCardInGraveyardOrBattlefield(this);
    }

}
