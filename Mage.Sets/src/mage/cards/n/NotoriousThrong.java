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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ProwlCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.FaerieRogueToken;
import mage.players.Player;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author LoneFox
 */
public class NotoriousThrong extends CardImpl {

    public NotoriousThrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{U}");
        this.subtype.add("Rogue");

        // Prowl {5}{U}
        this.addAbility(new ProwlAbility(this, "{5}{U}"));
        // create X 1/1 black Faerie Rogue creature tokens with flying, where X is the damage dealt to your opponents this turn.
        this.getSpellAbility().addEffect(new NotoriousThrongEffect());
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
        // If Notorious Throng's prowl cost was paid, take an extra turn after this one.
        Effect effect = new ConditionalOneShotEffect(new AddExtraTurnControllerEffect(), ProwlCondition.instance);
        effect.setText("If {this}'s prowl cost was paid, take an extra turn after this one.");
        this.getSpellAbility().addEffect(effect);
    }

    public NotoriousThrong(final NotoriousThrong card) {
        super(card);
    }

    @Override
    public NotoriousThrong copy() {
        return new NotoriousThrong(this);
    }
}

class NotoriousThrongEffect extends OneShotEffect {

    public NotoriousThrongEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create X 1/1 black Faerie Rogue creature tokens with flying, where X is the damage dealt to your opponents this turn";
    }

    public NotoriousThrongEffect(NotoriousThrongEffect effect) {
        super(effect);
    }

    @Override
    public NotoriousThrongEffect copy() {
        return new NotoriousThrongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = (AmountOfDamageAPlayerReceivedThisTurnWatcher) game.getState().getWatchers().get(AmountOfDamageAPlayerReceivedThisTurnWatcher.class.getSimpleName());
        if(controller != null && watcher != null) {
            int numTokens = 0;
            for(UUID opponentId: game.getOpponents(controller.getId())) {
                numTokens += watcher.getAmountOfDamageReceivedThisTurn(opponentId);
            }
            if(numTokens > 0) {
                new CreateTokenEffect(new FaerieRogueToken(), numTokens).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
