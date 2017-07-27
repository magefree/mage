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
package mage.cards.m;

import java.util.HashMap;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
 * @author jeffwadsworth
 */
public class MenacingOgre extends CardImpl {

    public MenacingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add("Ogre");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Menacing Ogre enters the battlefield, each player secretly chooses a number. Then those numbers are revealed. Each player with the highest number loses that much life. If you are one of those players, put two +1/+1 counters on Menacing Ogre.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MenacingOgreEffect(), false));

    }

    public MenacingOgre(final MenacingOgre card) {
        super(card);
    }

    @Override
    public MenacingOgre copy() {
        return new MenacingOgre(this);
    }
}

class MenacingOgreEffect extends OneShotEffect {

    public MenacingOgreEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player secretly chooses a number. Then those numbers are revealed. Each player with the highest number loses that much life. If you are one of those players, put two +1/+1 counters on {this}";
    }

    public MenacingOgreEffect(final MenacingOgreEffect effect) {
        super(effect);
    }

    @Override
    public MenacingOgreEffect copy() {
        return new MenacingOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int highestNumber = 0;
        int number = 0;
        Permanent menacingOgre = game.getPermanent(source.getSourceId());
        String message = "Choose a number.";
        HashMap<Player, Integer> numberChosen = new HashMap<>();

        //players choose numbers
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                number = player.getAmount(0, 1000, message, game);
                numberChosen.put(player, number);
            }
        }
        //get highest number
        for (Player player : numberChosen.keySet()) {
            if (highestNumber < numberChosen.get(player)) {
                highestNumber = numberChosen.get(player);
            }
        }
        //reveal numbers to players and follow through with effect
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                game.informPlayers(player.getLogName() + " chose number " + numberChosen.get(player));
                if (numberChosen.get(player) >= highestNumber) {
                    player.loseLife(highestNumber, game, false);
                    if (player.getId() == source.getControllerId()
                            && menacingOgre != null) {
                        menacingOgre.addCounters(CounterType.P1P1.createInstance(2), source, game);
                    }
                }
            }
        }
        return true;
    }
}
