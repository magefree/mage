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
package mage.abilities.effects.keyword;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class SupportEffect extends AddCountersTargetEffect {

    private final DynamicValue amountSupportTargets;
    private final boolean otherPermanent;

    public SupportEffect(Card card, int amount, boolean otherPermanent) {
        super(CounterType.P1P1.createInstance(0), new StaticValue(1));
        this.amountSupportTargets = new StaticValue(amount);
        this.otherPermanent = otherPermanent;
        if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
            card.getSpellAbility().addTarget(new TargetCreaturePermanent(0, amount, new FilterCreaturePermanent("target creatures"), false));
        }
        staticText = setText();
    }

    public SupportEffect(final SupportEffect effect) {
        super(effect);
        this.amountSupportTargets = effect.amountSupportTargets;
        this.otherPermanent = effect.otherPermanent;
    }

    @Override
    public SupportEffect copy() {
        return new SupportEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("support ");
        if (amountSupportTargets instanceof StaticValue) {
            sb.append(amountSupportTargets);
            sb.append(". <i>(Put a +1/+1 counter on each of up to ");
            sb.append(CardUtil.numberToText(amountSupportTargets.toString()));
        } else {
            sb.append("X, where X is the number of ");
            sb.append(amountSupportTargets.getMessage());
            sb.append(". (Put a +1/+1 counter on each of up to X");
        }
        if (otherPermanent) {
            sb.append(" other");
        }
        sb.append(" target creatures.)</i>");
        return sb.toString();
    }
}
