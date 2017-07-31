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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class CrazedFirecat extends CardImpl {

    public CrazedFirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add("Elemental");
        this.subtype.add("Cat");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Crazed Firecat enters the battlefield, flip a coin until you lose a flip. Put a +1/+1 counter on Crazed Firecat for each flip you win.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CrazedFirecatEffect(), false));
    }

    public CrazedFirecat(final CrazedFirecat card) {
        super(card);
    }

    @Override
    public CrazedFirecat copy() {
        return new CrazedFirecat(this);
    }
}

class CrazedFirecatEffect extends OneShotEffect {

    public CrazedFirecatEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin until you lose a flip. Put a +1/+1 counter on {this} for each flip you win.";
    }

    public CrazedFirecatEffect(final CrazedFirecatEffect effect) {
        super(effect);
    }

    @Override
    public CrazedFirecatEffect copy() {
        return new CrazedFirecatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller != null && sourceObject != null) {
            int flipsWon = 0;
            while (controller.flipCoin(game)) {
                flipsWon++;
            }
            sourceObject.addCounters(CounterType.P1P1.createInstance(flipsWon), source, game);
            return true;
        }
        return false;
    }
}
