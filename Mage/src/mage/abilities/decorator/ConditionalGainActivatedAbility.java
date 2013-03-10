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
package mage.abilities.decorator;

import java.util.UUID;
import mage.Constants;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.game.Game;

/**
 * The card / permanent has the ability only, if the condition is true.
 *
 * @author LevelX
 */
public class ConditionalGainActivatedAbility extends ActivatedAbilityImpl<ConditionalGainActivatedAbility> {

        private Condition condition;
        private String staticText = "";

        private static final Effects emptyEffects = new Effects();

        public ConditionalGainActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(Zone zone, Effect effect, Costs costs, Condition condition, String rule) {
        super(zone, effect, costs);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(ConditionalGainActivatedAbility ability) {
        super(ability);
        this.condition = ability.condition;
        this.staticText = ability.staticText;
    }

    @Override
    public Effects getEffects(Game game, Constants.EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!condition.apply(game, this)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public ConditionalGainActivatedAbility copy() {
        return new ConditionalGainActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
