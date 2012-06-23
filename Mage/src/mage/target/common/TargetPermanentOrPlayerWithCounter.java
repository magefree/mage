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

import mage.abilities.Ability;
import mage.filter.common.FilterPermanentOrPlayerWithCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import java.util.UUID;

/**
 *
 * @author nantuko
 */
public class TargetPermanentOrPlayerWithCounter extends TargetPermanentOrPlayer {

    protected FilterPermanentOrPlayerWithCounter filter;

    public TargetPermanentOrPlayerWithCounter() {
        this(1, 1);
    }

    public TargetPermanentOrPlayerWithCounter(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetPermanentOrPlayerWithCounter(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets);
        this.filter = new FilterPermanentOrPlayerWithCounter();
        this.targetName = filter.getMessage();
        super.setFilter(this.filter);
    }

    public TargetPermanentOrPlayerWithCounter(int minNumTargets, int maxNumTargets, boolean notTarget) {
           this(minNumTargets, maxNumTargets);
        this.notTarget = notTarget;
    }

    public TargetPermanentOrPlayerWithCounter(final TargetPermanentOrPlayerWithCounter target) {
        super(target);
        this.filter = target.filter.copy();
        super.setFilter(this.filter);
    }

    @Override
    public TargetPermanentOrPlayerWithCounter copy() {
        return new TargetPermanentOrPlayerWithCounter(this);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (permanent.getCounters().size() == 0) {
                return false;
            }
        }
        Player player = game.getPlayer(id);
        if (player != null) {
            if (player.getCounters().size() == 0) {
                return false;
            }
        }
        return super.canTarget(id, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (permanent.getCounters().size() == 0) {
                return false;
            }
        }
        Player player = game.getPlayer(id);
        if (player != null) {
            if (player.getCounters().size() == 0) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }

}
