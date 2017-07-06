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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GrindDust extends SplitCard {

    public GrindDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{B}", "{3}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Grind
        // Put a -1/-1 counter on each of up to two target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        effect.setText("Put a -1/-1 counter on each of up to two target creatures");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Dust
        // Aftermath
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Exile any number of target creatures that have -1/-1 counters on them.
        getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CounterPredicate(CounterType.M1M1));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));

    }

    public GrindDust(final GrindDust card) {
        super(card);
    }

    @Override
    public GrindDust copy() {
        return new GrindDust(this);
    }
}
