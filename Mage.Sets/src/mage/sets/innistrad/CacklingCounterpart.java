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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 * @author nantuko
 */
public class CacklingCounterpart extends CardImpl<CacklingCounterpart> {

    public CacklingCounterpart(UUID ownerId) {
        super(ownerId, 46, "Cackling Counterpart", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "ISD";

        this.color.setBlue(true);

        // Put a token onto the battlefield that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new CacklingCounterpartEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{U}{U}"), TimingRule.INSTANT));
    }

    public CacklingCounterpart(final CacklingCounterpart card) {
        super(card);
    }

    @Override
    public CacklingCounterpart copy() {
        return new CacklingCounterpart(this);
    }
}

class CacklingCounterpartEffect extends OneShotEffect<CacklingCounterpartEffect> {

    public CacklingCounterpartEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a token onto the battlefield that's a copy of target creature you control";
    }

    public CacklingCounterpartEffect(final CacklingCounterpartEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public CacklingCounterpartEffect copy() {
        return new CacklingCounterpartEffect(this);
    }
}


