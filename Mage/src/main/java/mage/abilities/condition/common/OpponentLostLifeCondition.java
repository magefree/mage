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
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 * Describes condition when an opponent has lost an amount of life
 *
 * @author LevelX2
 */
public class OpponentLostLifeCondition extends IntCompareCondition {

    public OpponentLostLifeCondition(Condition.ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int maxLostLive = 0;
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get("PlayerLostLifeWatcher");
        if (watcher != null) {
            for (UUID opponentId: game.getOpponents(source.getControllerId())) {
                int lostLive = watcher.getLiveLost(opponentId);
                if (lostLive > maxLostLive) {
                    maxLostLive = lostLive;
                }
            }
        }
        return maxLostLive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if an opponent lost ");
        switch(type) {
            case GreaterThan:
                sb.append(value+1).append(" or more life this turn ");
                break;
            case Equal:
                sb.append(value).append(" life this turn ");
                break;
            case LessThan:
                sb.append(" less than ").append(value).append(" life this turn ");
                break;
        }
        return sb.toString();
    }
}
