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

package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterPlayer extends FilterImpl<Player> {

    protected List<ObjectPlayerPredicate<ObjectPlayer<Player>>> extraPredicates = new ArrayList<ObjectPlayerPredicate<ObjectPlayer<Player>>>();

    public FilterPlayer() {
        this("player");
    }

    public FilterPlayer(String name) {
        super(name);
    }

    public FilterPlayer(final FilterPlayer filter) {
        super(filter);
        this.extraPredicates = new ArrayList<ObjectPlayerPredicate<ObjectPlayer<Player>>>(filter.extraPredicates);
    }

    public void add(ObjectPlayerPredicate predicate) {
        extraPredicates.add(predicate);
    }

    public boolean match(Player player, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(player, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(player, sourceId, playerId), game);
    }

    @Override
    public FilterPlayer copy() {
        return new FilterPlayer(this);
    }

}
