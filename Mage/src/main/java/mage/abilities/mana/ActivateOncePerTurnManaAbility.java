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

package mage.abilities.mana;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ActivateOncePerTurnManaAbility extends ManaAbility {

    class ActivationInfo {
        
        public int turnNum;
        public int activationCounter;

        public ActivationInfo(int turnNum, int activationCounter) {
            this.turnNum = turnNum;
            this.activationCounter = activationCounter;
        }
    }

    public ActivateOncePerTurnManaAbility(Zone zone, BasicManaEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.netMana.add(effect.getMana());
    }

    public ActivateOncePerTurnManaAbility(Zone zone, AddManaOfAnyColorEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.netMana.add(new Mana(0,0,0,0,0,0,effect.getAmount(), 0));
    }

    public ActivateOncePerTurnManaAbility(ActivateOncePerTurnManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            ActivationInfo activationInfo = getActivationInfo(game);
            if (activationInfo == null || activationInfo.turnNum != game.getTurnNum() || activationInfo.activationCounter < 1) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game)) {
            if (super.activate(game, noMana)) {
                ActivationInfo activationInfo = getActivationInfo(game);
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
                setActivationInfo(activationInfo, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only once each turn.";
    }

    @Override
    public ActivateOncePerTurnManaAbility copy() {
        return new ActivateOncePerTurnManaAbility(this);
    }

    private ActivationInfo getActivationInfo(Game game) {
        Integer turnNum = (Integer) game.getState().getValue(CardUtil.getCardZoneString("activationsTurn", sourceId, game));
        Integer activationCount = (Integer) game.getState().getValue(CardUtil.getCardZoneString("activationsCount", sourceId, game));
        if (turnNum == null || activationCount == null) {
            return null;
        }
        return new ActivationInfo(turnNum, activationCount);
    }
    
    private void setActivationInfo(ActivationInfo activationInfo, Game game) {
        game.getState().setValue(CardUtil.getCardZoneString("activationsTurn", sourceId, game), activationInfo.turnNum);
        game.getState().setValue(CardUtil.getCardZoneString("activationsCount", sourceId, game), activationInfo.activationCounter);
    }    
    
}
