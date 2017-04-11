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
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo
 */
public class QuarryHauler extends CardImpl {

    public QuarryHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add("Camel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Quarry Hauler enters the battlefield, for each kind of counter on target permanent, put another counter of that kind on it or remove one from it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarryHaulerEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    public QuarryHauler(final QuarryHauler card) {
        super(card);
    }

    @Override
    public QuarryHauler copy() {
        return new QuarryHauler(this);
    }
}

class QuarryHaulerEffect extends OneShotEffect {

    public QuarryHaulerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "for each kind of counter on target permanent, put another counter of that kind on it or remove one from it";

    }

    public QuarryHaulerEffect(final QuarryHaulerEffect effect) {
        super(effect);
    }

    @Override
    public QuarryHaulerEffect copy() {
        return new QuarryHaulerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent != null) {
                Counters counters = permanent.getCounters(game).copy();
                CounterType counterType;
                for (Counter counter : counters.values()) {
                    if (controller.chooseUse(Outcome.BoostCreature, "Do you want to add or remove a " + counter.getName() + " counter?", null, "add", "remove", source, game)) {
                        counterType = CounterType.findByName(counter.getName());
                        Counter counterToAdd;
                        if (counterType != null) {
                            counterToAdd = counterType.createInstance();
                        } else {
                            counterToAdd = new Counter(counter.getName());
                        }
                        permanent.addCounters(counterToAdd, source, game);
                    } else {
                        counterType = CounterType.findByName(counter.getName());
                        if (counterType != null) {
                            permanent.removeCounters(counterType.createInstance(), game);
                        } else {
                            permanent.removeCounters(counter.getName(), 1, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
