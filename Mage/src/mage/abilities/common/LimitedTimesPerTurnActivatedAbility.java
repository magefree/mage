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

package mage.abilities.common;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LimitedTimesPerTurnActivatedAbility extends ActivatedAbilityImpl {

    class ActivationInfo {

        public int turnNum;
        public int activationCounter;

        public ActivationInfo(int turnNum, int activationCounter) {
            this.turnNum = turnNum;
            this.activationCounter = activationCounter;
        }
    }

    private int maxActivationsPerTurn;

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost) {
        this(zone, effect, cost, 1);
    }

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost, int maxActivationsPerTurn) {
        super(zone, effect, cost);
        this.maxActivationsPerTurn = maxActivationsPerTurn;
    }

    public LimitedTimesPerTurnActivatedAbility(LimitedTimesPerTurnActivatedAbility ability) {
        super(ability);
        this.maxActivationsPerTurn = ability.maxActivationsPerTurn;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            ActivationInfo activationInfo = (ActivationInfo) game.getState().getValue(CardUtil.getCardZoneString("activations", sourceId, game));
            return activationInfo == null || activationInfo.turnNum != game.getTurnNum() || activationInfo.activationCounter < maxActivationsPerTurn;
        }
        return false;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game)) {
            if (super.activate(game, noMana)) {
                ActivationInfo activationInfo = (ActivationInfo) game.getState().getValue(CardUtil.getCardZoneString("activations", sourceId, game));
                if (activationInfo == null) {
                    activationInfo = new ActivationInfo(game.getTurnNum(), 1);
                } else {
                    if (activationInfo.turnNum != game.getTurnNum()) {
                        activationInfo.turnNum = game.getTurnNum();
                        activationInfo.activationCounter = 1;
                    } else {
                        activationInfo.activationCounter++;
                    }
                }
                game.getState().setValue(CardUtil.getCardZoneString("activations", sourceId, game), activationInfo);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean resolve(Game game) {
        return super.resolve(game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule()).append(" Activate this ability ");
        switch(maxActivationsPerTurn) {
            case 1:
                sb.append("only once");
                break;
            case 2:
                sb.append("no more than twice");
                break;
            default:
                sb.append("no more than ").append(CardUtil.numberToText(maxActivationsPerTurn)).append(" times");
        }
        sb.append(" each turn.");
        return sb.toString();
    }

    @Override
    public LimitedTimesPerTurnActivatedAbility copy() {
        return new LimitedTimesPerTurnActivatedAbility(this);
    }

}
