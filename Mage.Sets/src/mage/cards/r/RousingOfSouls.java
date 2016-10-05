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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class RousingOfSouls extends CardImpl {

    public RousingOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Parley - Each player reveals the top card of his or her library. For each nonland card revealed this way, 
        // you put a 1/1 white Spirit creature token with flying onto the battlefield. Then each player draws a card.
        this.getSpellAbility().addEffect(new RousingOfSoulsEffect());
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        this.getSpellAbility().addEffect(effect);
    }

    public RousingOfSouls(final RousingOfSouls card) {
        super(card);
    }

    @Override
    public RousingOfSouls copy() {
        return new RousingOfSouls(this);
    }
}

class RousingOfSoulsEffect extends OneShotEffect {

    public RousingOfSoulsEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Parley &mdash; </i> Each player reveals the top card of his or her library. For each nonland card revealed this way, you put a 1/1 white Spirit creature token with flying onto the battlefield";
    }

    public RousingOfSoulsEffect(final RousingOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public RousingOfSoulsEffect copy() {
        return new RousingOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                new CreateTokenEffect(new SpiritWhiteToken(), parley).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
