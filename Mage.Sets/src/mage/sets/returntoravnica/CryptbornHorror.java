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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CryptbornHorror extends CardImpl<CryptbornHorror> {

    private static final String rule = "{this} enters the battlefield with X +1/+1 counters on it, where X is the total life lost by your opponents this turn";

    public CryptbornHorror(UUID ownerId) {
        super(ownerId, 212, "Cryptborn Horror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Horror");
        this.color.setBlack(true);
        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cryptborn Horror enters the battlefield with X +1/+1 counters on it, where X is the total life lost by your opponents this turn.
        this.addAbility(new EntersBattlefieldAbility(new CryptbornHorrorEffect(),rule));
    }

    public CryptbornHorror(final CryptbornHorror card) {
        super(card);
    }

    @Override
    public CryptbornHorror copy() {
        return new CryptbornHorror(this);
    }
}
class CryptbornHorrorEffect extends OneShotEffect<CryptbornHorrorEffect> {
    CryptbornHorrorEffect() {
        super(Constants.Outcome.BoostCreature);
    }

    CryptbornHorrorEffect(final CryptbornHorrorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            OpponentsLostLifeCount dynamicValue = new OpponentsLostLifeCount();
            if (dynamicValue != null) {
                int oll = dynamicValue.calculate(game, source);
                if (oll > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(oll), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CryptbornHorrorEffect copy() {
        return new CryptbornHorrorEffect(this);
    }
}