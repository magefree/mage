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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public class LiquidFire extends CardImpl {

    public LiquidFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");

        // As an additional cost to cast Liquid Fire, choose a number between 0 and 5.
        this.getSpellAbility().addCost(new LiquidFireCost());
        // Liquid Fire deals X damage to target creature and 5 minus X damage to that creature's controller, where X is the chosen number.
        DynamicValue choiceValue = new GetXValue();
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new LiquidFireEffect(choiceValue));
        
    }

    public LiquidFire(final LiquidFire card) {
        super(card);
    }

    @Override
    public LiquidFire copy() {
        return new LiquidFire(this);
    }

    private static class LiquidFireEffect extends OneShotEffect {
        protected DynamicValue choiceValue;

        public LiquidFireEffect(DynamicValue choiceValue) {
            super(Outcome.Damage);
            this.staticText = "{this} deals X damage to target creature and 5 minus X damage to that creature's controller, where X is the chosen number.";
            this.choiceValue = choiceValue;
        }
        
        public LiquidFireEffect(LiquidFireEffect effect) {
            super(effect);
            this.choiceValue = effect.choiceValue;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
            int creatureDamage = choiceValue.calculate(game, source, this);
            int playerDamage = 5 - creatureDamage;
            if (target != null) {
                target.damage(creatureDamage, source.getSourceId(), game, false, true);
                Player controller = game.getPlayer(target.getControllerId());
                if (controller != null) {
                    controller.damage(playerDamage, source.getSourceId(), game, false, true);
                }
                return true;
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new LiquidFireEffect(this);
        }
    }
    
    class LiquidFireCost extends VariableCostImpl {
        public LiquidFireCost() {
            super("Choose a Number");
            this.text = "As an additional cost to cast {source}, choose a number between 0 and 5";
        }
        
        public LiquidFireCost(final LiquidFireCost cost) {
            super(cost);
        }

        @Override
        public Cost copy() {
            return new LiquidFireCost(this);
        }

        @Override
        public Cost getFixedCostsFromAnnouncedValue(int xValue) {
            return null;
        }
        
        @Override
        public int getMaxValue(Ability source, Game game) {
            return 5;
        }
    }
}
