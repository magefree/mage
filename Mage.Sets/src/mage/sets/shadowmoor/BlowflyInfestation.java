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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 *
 * @author jeffwadsworth
 *
 *
 *
 */
public class BlowflyInfestation extends CardImpl {
    
    private static final String rule = "Whenever a creature dies, if it had a -1/-1 counter on it, put a -1/-1 counter on target creature.";

    public BlowflyInfestation(UUID ownerId) {
        super(ownerId, 58, "Blowfly Infestation", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "SHM";


        Effect effect = new BlowflyInfestationEffect();
        TriggeredAbility triggeredAbility = new DiesCreatureTriggeredAbility(effect, false, false, true);
        triggeredAbility.addTarget(new TargetCreaturePermanent());
        Condition condition = new BlowflyInfestationCondition();
        this.addAbility(new ConditionalTriggeredAbility(triggeredAbility, condition, rule));

    }

    public BlowflyInfestation(final BlowflyInfestation card) {
        super(card);
    }

    @Override
    public BlowflyInfestation copy() {
        return new BlowflyInfestation(this);
    }
}

class BlowflyInfestationCondition implements Condition {

    private static Permanent permanent;

    @Override
    public boolean apply(Game game, Ability source) {
        for (Effect effect : source.getEffects()) {
            if (effect.getTargetPointer().getFirst(game, source) != null) {
                permanent = game.getPermanentOrLKIBattlefield(effect.getTargetPointer().getFirst(game, source));
            }
        }
        if (permanent != null) {
            return permanent.getCounters().containsKey(CounterType.M1M1);
        }
        return false;
    }
}

class BlowflyInfestationEffect extends OneShotEffect {

    public BlowflyInfestationEffect() {
        super(Outcome.Detriment);
    }

    public BlowflyInfestationEffect(BlowflyInfestationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.addCounters(CounterType.M1M1.createInstance(), game);
            return true;
        }
        return false;
    }

    @Override
    public BlowflyInfestationEffect copy() {
        return new BlowflyInfestationEffect(this);
    }
}
