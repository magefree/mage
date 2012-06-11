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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ElderCathar extends CardImpl<ElderCathar> {

    public ElderCathar(UUID ownerId) {
        super(ownerId, 12, "Elder Cathar", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Elder Cathar dies, put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead.
        Ability ability = new DiesTriggeredAbility(new ElderCatharAddCountersTargetEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public ElderCathar(final ElderCathar card) {
        super(card);
    }

    @Override
    public ElderCathar copy() {
        return new ElderCathar(this);
    }
}

class ElderCatharAddCountersTargetEffect extends OneShotEffect<ElderCatharAddCountersTargetEffect> {

    private Counter counter;
    private Counter counter2;

    public ElderCatharAddCountersTargetEffect() {
        super(Constants.Outcome.Benefit);
        counter = CounterType.P1P1.createInstance(1);
        counter2 = CounterType.P1P1.createInstance(2);
        staticText = "put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead";
    }

    public ElderCatharAddCountersTargetEffect(final ElderCatharAddCountersTargetEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.counter2 = effect.counter2.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            if (counter != null) {
                if (permanent.hasSubtype("Human")) {
                    permanent.addCounters(counter2.copy(), game);
                } else {
                    permanent.addCounters(counter.copy(), game);
                }
                return true;
            }
        }
        return false;

    }

    @Override
    public ElderCatharAddCountersTargetEffect copy() {
        return new ElderCatharAddCountersTargetEffect(this);
    }


}
