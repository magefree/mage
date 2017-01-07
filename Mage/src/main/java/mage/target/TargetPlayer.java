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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPlayer extends TargetImpl {

    protected final FilterPlayer filter;

    public TargetPlayer() {
        this(1, 1, false);
    }

    public TargetPlayer(int numTargets) {
        this(numTargets, numTargets, false);
    }

    public TargetPlayer(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, notTarget, new FilterPlayer());
    }

    public TargetPlayer(int minNumTargets, int maxNumTargets, boolean notTarget, FilterPlayer filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.filter = filter;
        this.targetName = filter.getMessage();
        this.notTarget = notTarget;
    }

    public TargetPlayer(final TargetPlayer target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterPlayer getFilter() {
        return filter;
    }

    /**
     * Checks if there are enough {@link Player} that can be chosen. Should only
     * be used for Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceId - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid {@link Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, sourceId, sourceControllerId, game)) {
                if (player.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link Player} that can be selected. Should
     * not be used for Ability targets since this does not check for protection,
     * shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, sourceId, sourceControllerId, game)) {
                if (isNotTarget() || player.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    possibleTargets.add(playerId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        if (getNumberOfTargets() == 0 && targets.isEmpty()) {
            return true; // 0 targets selected is valid
        }
        for (UUID playerId : targets.keySet()) {
            if (canTarget(playerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Player player = game.getPlayer(id);
        return player != null && filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            if (source != null) {
                return (isNotTarget() || player.canBeTargetedBy(game.getObject(source.getSourceId()), source.getControllerId(), game))
                        && filter.match(player, source.getSourceId(), source.getControllerId(), game);
            } else {
                return filter.match(player, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                sb.append(player.getLogName()).append(" ");
            } else {
                sb.append("[target missing]");
            }
        }
        return sb.toString();
    }

    @Override
    public TargetPlayer copy() {
        return new TargetPlayer(this);
    }

}
