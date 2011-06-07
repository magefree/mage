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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class BlackSunsZenith extends CardImpl<BlackSunsZenith> {

    public BlackSunsZenith (UUID ownerId) {
        super(ownerId, 39, "Black Sun's Zenith", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");
        this.expansionSetCode = "MBS";
		this.color.setBlack(true);
        this.getSpellAbility().addEffect(new BlackSunsZenithEffect());
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    public BlackSunsZenith (final BlackSunsZenith card) {
        super(card);
    }

    @Override
    public BlackSunsZenith copy() {
        return new BlackSunsZenith(this);
    }

}

class BlackSunsZenithEffect extends OneShotEffect<BlackSunsZenithEffect> {
    BlackSunsZenithEffect() {
        super(Constants.Outcome.UnboostCreature);
    }

    BlackSunsZenithEffect(final BlackSunsZenithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getVariableCosts().get(0).getAmount();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                permanent.addCounters(CounterType.M1M1.createInstance(amount));
            }
        }
        return true;
    }

    @Override
    public BlackSunsZenithEffect copy() {
        return new BlackSunsZenithEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Put X -1/-1 counters on each creature";
    }
}