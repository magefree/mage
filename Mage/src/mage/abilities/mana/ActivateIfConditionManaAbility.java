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
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
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
import mage.Constants.Zone;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */


public class ActivateIfConditionManaAbility extends ManaAbility<ActivateIfConditionManaAbility> {

    private Condition condition;

    public ActivateIfConditionManaAbility(Zone zone, BasicManaEffect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.netMana = effect.getMana();
        this.condition = condition;
    }

    public ActivateIfConditionManaAbility(ActivateIfConditionManaAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (condition.apply(game, this)) {
            return super.canActivate(playerId, game);
        }
        return false;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game)) {
            return super.activate(game, noMana);
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder(super.getRule()).append(" Activate this ability only if ").append(condition.toString()).append(".").toString() ;
    }

    @Override
    public ActivateIfConditionManaAbility copy() {
        return new ActivateIfConditionManaAbility(this);
    }

}
