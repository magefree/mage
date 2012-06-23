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

package mage.player.ai.simulators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatSimulator implements Serializable {

    public List<CombatGroupSimulator> groups = new ArrayList<CombatGroupSimulator>();
    public List<UUID> defenders = new ArrayList<UUID>();
    public Map<UUID, Integer> playersLife = new HashMap<UUID, Integer>();
    public Map<UUID, Integer> planeswalkerLoyalty = new HashMap<UUID, Integer>();
    public UUID attackerId;
    public int rating = 0;

    public static CombatSimulator load(Game game) {
        CombatSimulator simCombat = new CombatSimulator();
        for (CombatGroup group: game.getCombat().getGroups()) {
            simCombat.groups.add(new CombatGroupSimulator(group.getDefenderId(), group.getAttackers(), group.getBlockers(), game));
        }
        for (UUID defenderId: game.getCombat().getDefenders()) {
            simCombat.defenders.add(defenderId);
            Player player = game.getPlayer(defenderId);
            if (player != null) {
                simCombat.playersLife.put(defenderId, player.getLife());
            }
            else {
                Permanent permanent = game.getPermanent(defenderId);
                simCombat.planeswalkerLoyalty.put(defenderId, permanent.getCounters().getCount(CounterType.LOYALTY));
            }
        }
        return simCombat;
    }

    public CombatSimulator() {}

    public void clear() {
        groups.clear();
        defenders.clear();
        attackerId = null;
    }

    public void simulate() {
        for (CombatGroupSimulator group: groups) {
            group.simulateCombat();
        }
    }

    public int evaluate() {
        Map<UUID, Integer> damage = new HashMap<UUID, Integer>();
        int result = 0;
        for (CombatGroupSimulator group: groups) {
            if (!damage.containsKey(group.defenderId)) {
                damage.put(group.defenderId, group.unblockedDamage);
            }
            else {
                damage.put(group.defenderId, damage.get(group.defenderId) + group.unblockedDamage);
            }
        }
        //check for lethal damage to player
        for (Entry<UUID, Integer> entry: playersLife.entrySet()) {
            if (damage.containsKey(entry.getKey()) && entry.getValue() <= damage.get(entry.getKey())) {
                //TODO:  check for protection
                //NOTE:  not applicable for mulitplayer games
                return Integer.MAX_VALUE;
            }
        }

        for (CombatGroupSimulator group: groups) {
            result += group.evaluateCombat();
        }

        rating = result;
        return result;
    }
}
