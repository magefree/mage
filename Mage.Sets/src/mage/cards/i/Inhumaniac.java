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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class Inhumaniac extends CardImpl {

    public Inhumaniac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BRAINIAC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, roll a six-sided die. On a 3 or 4, put a +1/+1 counter on Inhumaniac. On a 5 or higher, put two +1/+1 counters on it. On a 1, remove all +1/+1 counters from Inhumaniac.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InhumaniacEffect(), TargetController.YOU, false));
    }

    public Inhumaniac(final Inhumaniac card) {
        super(card);
    }

    @Override
    public Inhumaniac copy() {
        return new Inhumaniac(this);
    }
}

class InhumaniacEffect extends OneShotEffect {

    public InhumaniacEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die. On a 3 or 4, put a +1/+1 counter on {this}. On a 5 or higher, put two +1/+1 counters on it. On a 1, remove all +1/+1 counters from {this}";
    }

    public InhumaniacEffect(final InhumaniacEffect effect) {
        super(effect);
    }

    @Override
    public InhumaniacEffect copy() {
        return new InhumaniacEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            if (amount >= 3 && amount <= 4) {
                permanent.addCounters(CounterType.P1P1.createInstance(1), source, game);
            } else if (amount >= 5) {
                permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
            } else if (amount == 1) {
                permanent.getCounters(game).removeAllCounters(CounterType.P1P1);
            }
            return true;
        }
        return false;
    }
}
