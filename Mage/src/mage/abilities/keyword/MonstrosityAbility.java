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

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 *
 * @author LevelX2
 */

public class MonstrosityAbility extends ActivatedAbilityImpl<MonstrosityAbility> {

    private int monstrosityValue;

    public MonstrosityAbility(String manaString, int monstrosityValue) {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(monstrosityValue)),
                new InvertCondition(MonstrousCondition.getInstance()),
                ""), new ManaCostsImpl(manaString));
        this.addEffect(new ConditionalOneShotEffect(
                new BecomeMonstrousSourceEffect(),
                new InvertCondition(MonstrousCondition.getInstance()),""));

        this.monstrosityValue = monstrosityValue;
    }

    public MonstrosityAbility(final MonstrosityAbility ability) {
        super(ability);
        this.monstrosityValue = ability.monstrosityValue;
    }

    @Override
    public MonstrosityAbility copy() {
        return new MonstrosityAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder(manaCosts.getText()).append(": Monstrosity ").append(monstrosityValue)
                .append(".  <i>(If this creature isn't monstrous, put ").append(CardUtil.numberToText(monstrosityValue))
                .append(" +1/+1 counters on it and it becomes monstrous.)</i>").toString();
    }

}

class BecomeMonstrousSourceEffect extends OneShotEffect<BecomeMonstrousSourceEffect> {

    public BecomeMonstrousSourceEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "";
    }

    public BecomeMonstrousSourceEffect(final BecomeMonstrousSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomeMonstrousSourceEffect copy() {
        return new BecomeMonstrousSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.setMonstrous(true);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_MONSTROUS, source.getSourceId(), source.getSourceId(), source.getControllerId()));
            return true;
        }
        return false;
    }
}
