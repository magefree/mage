
package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
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

    public TargetCardInGraveyardOrBattlefield() {
        this(1, 1, new FilterCard("target card in a graveyard or permanent on the battlefield"));
    }

    public TargetCardInGraveyardOrBattlefield(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInGraveyardOrBattlefield(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInGraveyardOrBattlefield(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter); // Zone for card
        this.targetName = filter.getMessage();
    }

    public TargetCardInGraveyardOrBattlefield(final TargetCardInGraveyardOrBattlefield target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        if (!super.canChoose(sourceId, sourceControllerId, game)) {
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
                if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceControllerId, game)) {
                    return true;
                }
            }
            return false;

        }
        return true;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Card card = game.getCard(id);
        return card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD && filter.match(card, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        //return super.possibleTargets(sourceControllerId, game); //To change body of generated methods, choose Tools | Templates.
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
            if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceId, sourceControllerId, game)) {
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
