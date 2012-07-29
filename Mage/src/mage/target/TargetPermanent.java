/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.target;

import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPermanent<T extends TargetPermanent<T>> extends TargetObject<TargetPermanent<T>> {

    protected FilterPermanent filter;

    public TargetPermanent() {
        this(1, 1, new FilterPermanent(), false);
    }

    public TargetPermanent(FilterPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetPermanent(int numTargets, FilterPermanent filter) {
        this(numTargets, numTargets, filter, false);
    }

    public TargetPermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, boolean notTarget) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.BATTLEFIELD;
        this.filter = filter;
        this.targetName = filter.getMessage();
        this.notTarget = notTarget;
    }

    public TargetPermanent(final TargetPermanent<T> target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(source.getControllerId(), id, source, game);
    }

    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (source != null)
                //1. TODO: check for replacement effects
                //2. We need to check both source.getId() and source.getSourceId()
                // first for protection from spells or abilities (e.g. protection from colored spells, r1753)
                // second for protection from sources (e.g. protection from artifacts + equip ability)
                return permanent.canBeTargetedBy(game.getObject(source.getId()), controllerId, game)
                        && permanent.canBeTargetedBy(game.getObject(source.getSourceId()), controllerId, game)
                        && filter.match(permanent, source.getSourceId(), controllerId, game);
            else
                return filter.match(permanent, null, controllerId, game);
        }
        return false;
    }

    public boolean canTarget(UUID controllerId, UUID id, UUID sourceId, Game game, boolean flag) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, sourceId, controllerId, game);
        }
        return false;
    }

    @Override
    public FilterPermanent getFilter() {
        return this.filter;
    }

    /**
     * Checks if there are enough {@link Permanent} that can be chosen.
     *
     * Takes into account notTarget parameter, it case it's true doesn't check for protection, shroud etc.
     *
     * @param sourceId - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid {@link Permanent} exist
     */
    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0)
            return true;
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (!targets.containsKey(permanent.getId())) {
                if (!notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    count++;
                    if (count >= remainingTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link Permanent} that can be selected.  Should not be used
     * for Ability targets since this does not check for protection, shroud etc.
     * 
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Permanent} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0) {
            // if we return true, then AnowonTheRuinSage will hang for AI when no targets in play
            // TODO: retest Anowon the Ruin Sage
            return true;
        }
        int count = 0;
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
            if (!targets.containsKey(permanent.getId())) {
                count++;
                if (count >= remainingTargets)
                    return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        MageObject targetSource = game.getObject(sourceId);
         for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
            if (!targets.containsKey(permanent.getId())) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetPermanent copy() {
        return new TargetPermanent(this);
    }

}
