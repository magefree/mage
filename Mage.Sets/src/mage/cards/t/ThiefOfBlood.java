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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class ThiefOfBlood extends CardImpl {

    public ThiefOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.subtype.add("Vampire");

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Thief of Blood enters the battlefield, remove all counters from all permanents. Thief of Blood enters the battlefield with a +1/+1 counter on it for each counter removed this way.
        this.addAbility(new EntersBattlefieldAbility(new ThiefOfBloodEffect(), null, "As {this} enters the battlefield, remove all counters from all permanents. {this} enters the battlefield with a +1/+1 counter on it for each counter removed this way", null));
    }

    public ThiefOfBlood(final ThiefOfBlood card) {
        super(card);
    }

    @Override
    public ThiefOfBlood copy() {
        return new ThiefOfBlood(this);
    }
}

class ThiefOfBloodEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a counter");

    static {
        filter.add(new CounterPredicate(null));
    }

    ThiefOfBloodEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "remove all counters from all permanents. {this} enters the battlefield with a +1/+1 counter on it for each counter removed this way";
    }

    ThiefOfBloodEffect(final ThiefOfBloodEffect effect) {
        super(effect);
    }

    @Override
    public ThiefOfBloodEffect copy() {
        return new ThiefOfBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            Counters counters = permanent.getCounters(game).copy();
            for (Counter counter : counters.values()) {
                permanent.removeCounters(counter.getName(), counter.getCount(), game);
                countersRemoved += counter.getCount();
            }
        }
        if (countersRemoved > 0) {
            Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
            if (sourcePermanent != null) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(countersRemoved), game);
            }
        }
        return true;
    }
}
