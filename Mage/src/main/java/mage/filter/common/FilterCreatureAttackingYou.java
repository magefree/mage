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

import java.util.UUID;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class FilterCreatureAttackingYou extends FilterAttackingCreature {

    private final boolean orWalker;

    public FilterCreatureAttackingYou() {
        this(false);
    }

    public FilterCreatureAttackingYou(boolean orWalker) {
        this("creature that's attacking you" + (orWalker ? "or a planeswalker you control" : ""), orWalker);
    }

    public FilterCreatureAttackingYou(String name) {
        this(name, false);
    }

    public FilterCreatureAttackingYou(String name, boolean orWalker) {
        super(name);
        this.orWalker = orWalker;
    }

    public FilterCreatureAttackingYou(final FilterCreatureAttackingYou filter) {
        super(filter);
        this.orWalker = filter.orWalker;
    }

    @Override
    public FilterCreatureAttackingYou copy() {
        return new FilterCreatureAttackingYou(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (orWalker) {
            return super.match(permanent, sourceId, playerId, game)
                    && permanent.isAttacking() // to prevent unneccessary combat checking if not attacking
                    && playerId.equals(game.getCombat().getDefendingPlayerId(permanent.getId(), game));
        } else {
            return super.match(permanent, sourceId, playerId, game)
                    && permanent.isAttacking() // to prevent unneccessary combat checking if not attacking
                    && playerId.equals(game.getCombat().getDefenderId(permanent.getId()));
        }
    }
}
