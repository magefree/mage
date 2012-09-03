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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class SoulsMight extends CardImpl<SoulsMight> {

    public SoulsMight(UUID ownerId) {
        super(ownerId, 149, "Soul's Might", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "ALA";

        this.color.setGreen(true);

        // Put X +1/+1 counters on target creature, where X is that creature's power.
        this.getSpellAbility().addEffect(new SoulsMightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SoulsMight(final SoulsMight card) {
        super(card);
    }

    @Override
    public SoulsMight copy() {
        return new SoulsMight(this);
    }
}

class SoulsMightEffect extends OneShotEffect<SoulsMightEffect> {

    public SoulsMightEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put X +1/+1 counters on target creature, where X is that creature's power";
    }

    public SoulsMightEffect(final SoulsMightEffect effect) {
        super(effect);
    }

    @Override
    public SoulsMightEffect copy() {
        return new SoulsMightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.getPower().getValue() > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(permanent.getPower().getValue()), game);
            return true;
        }
        return false;
    }
}
