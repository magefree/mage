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

package mage.target.common;

import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.common.FilterPlaneswalkerOrPlayer;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDefender extends TargetImpl<TargetDefender> {

    protected FilterPlaneswalkerOrPlayer filter;
    protected UUID attackerId;

    public TargetDefender(Set<UUID> defenders, UUID attackerId) {
        this(1, 1, defenders, attackerId);
    }

    public TargetDefender(int numTargets, Set<UUID> defenders, UUID attackerId) {
        this(numTargets, numTargets, defenders, attackerId);
    }

    public TargetDefender(int minNumTargets, int maxNumTargets, Set<UUID> defenders, UUID attackerId) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.ALL;
        this.filter = new FilterPlaneswalkerOrPlayer(defenders);
        this.targetName = filter.getMessage();
        this.attackerId = attackerId;
    }

    public TargetDefender(final TargetDefender target) {
        super(target);
        this.filter = target.filter.copy();
        this.attackerId = target.attackerId;
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canBeTargetedBy(targetSource, game) && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets)
                    return true;
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterPlaneswalkerPermanent(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, game)) {
                count++;
                if (count >= this.minNumberOfTargets)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets)
                    return true;
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterPlaneswalkerPermanent(), sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
                count++;
                if (count >= this.minNumberOfTargets)
                    return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canBeTargetedBy(targetSource, game) && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterPlaneswalkerPermanent(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterPlaneswalkerPermanent(), sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId: getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getName()).append(" ");
            }
            else {
                Player player = game.getPlayer(targetId);
                sb.append(player.getName()).append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            return filter.match(player, game);
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(id);
        MageObject targetSource = game.getObject(attackerId);
        if (player != null) {
            return player.canBeTargetedBy(targetSource, game) && filter.match(player, game);
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            //Could be targeting due to combat decision to attack a player or planeswalker.
            UUID controllerId = null;
            if ( source != null ) {
                controllerId = source.getControllerId();
            }
            return permanent.canBeTargetedBy(targetSource, controllerId, game) && filter.match(permanent, game);
        }
        return false;
    }

    @Override
    public TargetDefender copy() {
        return new TargetDefender(this);
    }


}
