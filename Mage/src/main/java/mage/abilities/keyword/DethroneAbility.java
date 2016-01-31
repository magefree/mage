/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * Dethrone triggers whenever a creature with dethrone attacks the player with
 * the most life or tied for the most life. When the ability resolves, you put a
 * +1/+1 counter on the creature. This happens before blockers are declared.
 * Once the ability triggers, it doesn't matter what happens to anybody's life
 * total. If the defending player doesn't have the most life when the ability
 * resolves, the creature will still get the +1/+1 counter. Note that dethrone
 * won't trigger if the creature attacks a Planeswalker. You're going after the
 * crown, after all, not the royal advisors. If you have the most life, your
 * dethrone abilities won't trigger, but you may find a few choice ways to avoid
 * that situation.
 *
 * @author LevelX2
 */
public class DethroneAbility extends TriggeredAbilityImpl {

    public DethroneAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    public DethroneAbility(final DethroneAbility ability) {
        super(ability);
    }

    @Override
    public DethroneAbility copy() {
        return new DethroneAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId != null) {
            Player attackedPlayer = game.getPlayer(defenderId);
            Player controller = game.getPlayer(getControllerId());
            if (attackedPlayer != null && controller != null) {
                int mostLife = Integer.MIN_VALUE;
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() > mostLife) {
                            mostLife = player.getLife();
                        }
                    }
                }
                return attackedPlayer.getLife() == mostLife;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Dethrone (<i>Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1 counter on it.</i>)";
    }
}
