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

package mage.filter.common;

import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureOrPlayer extends FilterImpl<Object, FilterCreatureOrPlayer> implements FilterInPlay<Object> {

    protected FilterCreaturePermanent creatureFilter;
    protected FilterPlayer playerFilter;

    public FilterCreatureOrPlayer() {
        this("player or creature");
    }

    public FilterCreatureOrPlayer(String name, UUID controllerId) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        playerFilter = new FilterPlayer();
        creatureFilter.getControllerId().add(controllerId);
        playerFilter.getPlayerId().add(controllerId);
    }

    public FilterCreatureOrPlayer(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        playerFilter = new FilterPlayer();
    }

    public FilterCreatureOrPlayer(final FilterCreatureOrPlayer filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player)o, game);
        }
        else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent)o, game);
        }
        return notFilter;
    }

    @Override
    public boolean match(Object o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player)o, sourceId, playerId, game);
        }
        else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent)o, sourceId, playerId, game);
        }
        return notFilter;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    public FilterPlayer getPlayerFilter() {
        return this.playerFilter;
    }

    @Override
    public FilterCreatureOrPlayer copy() {
        return new FilterCreatureOrPlayer(this);
    }

}
