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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class AwakenAbility extends SpellAbility {

    static private String filterMessage = "a land you control to awake";

    private String rule;
    private int awakenValue;

    public AwakenAbility(Card card, int awakenValue, String awakenCosts) {
        super(new ManaCostsImpl<>(awakenCosts), card.getName() + " with awaken", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.getChoices().addAll(card.getSpellAbility().getChoices().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();
        this.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent(filterMessage)));
        this.addEffect(new AwakenEffect());
        this.awakenValue = awakenValue;
        rule = "Awaken " + awakenValue + "&mdash;" + awakenCosts
                + " <i>(If you cast this spell for " + awakenCosts + ", also put "
                + CardUtil.numberToText(awakenValue, "a")
                + " +1/+1 counters on target land you control and it becomes a 0/0 Elemental creature with haste. It's still a land.)</i>";

    }

    public AwakenAbility(final AwakenAbility ability) {
        super(ability);
        this.awakenValue = ability.awakenValue;
    }

    @Override
    public AwakenAbility copy() {
        return new AwakenAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return rule;
    }

    class AwakenEffect extends OneShotEffect {

        private AwakenEffect() {
            super(Outcome.BoostCreature);
            this.staticText = "put " + CardUtil.numberToText(awakenValue, "a") + " +1/+1 counters on target land you control";
        }

        public AwakenEffect(final AwakenEffect effect) {
            super(effect);
        }

        @Override
        public AwakenEffect copy() {
            return new AwakenEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            UUID targetId = null;
            for (Target target : source.getTargets()) {
                if (target.getFilter().getMessage().equals(filterMessage)) {
                    targetId = target.getFirstTarget();
                }
            }
            if (targetId != null) {
                FixedTarget fixedTarget = new FixedTarget(targetId);
                ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new AwakenElementalToken(), false, true, Duration.Custom);
                continuousEffect.setTargetPointer(fixedTarget);
                game.addEffect(continuousEffect, source);
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(awakenValue));
                effect.setTargetPointer(fixedTarget);
                return effect.apply(game, source);
            }
            return true;
        }
    }

}

class AwakenElementalToken extends Token {

    public AwakenElementalToken() {
        super("", "0/0 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add("Elemental");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }
}
