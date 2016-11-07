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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInHand extends TargetCard {

    public TargetCardInHand() {
        this(1, 1, new FilterCard());
    }

    public TargetCardInHand(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInHand(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInHand(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.HAND, filter);
    }

    public TargetCardInHand(final TargetCardInHand target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Card card = game.getPlayer(playerId).getHand().get(id, game);
        if (card != null) {
            return filter.match(card, source.getSourceId(), playerId, game);
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID playerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(playerId);
        if (player != null) {
            for (Card card : player.getHand().getCards(filter, sourceId, playerId, game)) {
                if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, playerId))) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int possibleTargets = 0;
        Player player = game.getPlayer(sourceControllerId);
        if (player != null) {
            for (Card card : player.getHand().getCards(filter, sourceId, sourceControllerId, game)) {
                if (sourceId == null || isNotTarget() || !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                    possibleTargets++;
                    if (possibleTargets >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TargetCardInHand copy() {
        return new TargetCardInHand(this);
    }

    @Override
    public String getTargetedName(Game game) {
        return filter.getMessage();
    }
}
