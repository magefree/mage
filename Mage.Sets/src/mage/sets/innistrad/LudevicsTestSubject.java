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

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class LudevicsTestSubject extends CardImpl<LudevicsTestSubject> {

    public LudevicsTestSubject(UUID ownerId) {
        super(ownerId, 64, "Ludevic's Test Subject", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Lizard");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.canTransform = true;
        this.secondSideCard = new LudevicsAbomination(ownerId);

        this.addAbility(DefenderAbility.getInstance());
        // {1}{U}: Put a hatchling counter on Ludevic's Test Subject. Then if there are five or more hatchling counters on it, remove all of them and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()), new ManaCostsImpl("{1}{U}"));
        ability.addEffect(new LudevicsTestSubjectEffect());
        this.addAbility(ability);
    }

    public LudevicsTestSubject(final LudevicsTestSubject card) {
        super(card);
    }

    @Override
    public LudevicsTestSubject copy() {
        return new LudevicsTestSubject(this);
    }
}

class LudevicsTestSubjectEffect extends OneShotEffect<LudevicsTestSubjectEffect> {
    LudevicsTestSubjectEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Then if there are five or more hatchling counters on it, remove all of them and transform it";
    }

    LudevicsTestSubjectEffect(final LudevicsTestSubjectEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            if (p.getCounters().getCount(CounterType.HATCHLING) >= 5) {
                p.getCounters().removeCounter(CounterType.HATCHLING, p.getCounters().getCount(CounterType.HATCHLING));
                TransformSourceEffect effect = new TransformSourceEffect(true);
                return effect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public LudevicsTestSubjectEffect copy() {
        return new LudevicsTestSubjectEffect(this);
    }
}