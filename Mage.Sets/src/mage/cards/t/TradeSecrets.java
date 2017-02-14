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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class TradeSecrets extends CardImpl {

    public TradeSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Target opponent draws two cards, then you draw up to four cards. That opponent may repeat this process as many times as he or she chooses.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new TradeSecretsEffect());

    }

    public TradeSecrets(final TradeSecrets card) {
        super(card);
    }

    @Override
    public TradeSecrets copy() {
        return new TradeSecrets(this);
    }
}

class TradeSecretsEffect extends OneShotEffect {

    public TradeSecretsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target opponent draws two cards, then you draw up to four cards. That opponent may repeat this process as many times as he or she chooses";
    }

    public TradeSecretsEffect(final TradeSecretsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String message = "Do you want to draw two cards and allow the spellcaster to draw up to four cards again?";
        String message2 = "How many cards do you want to draw?";
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (controller != null
                && targetOpponent != null) {
            new DrawCardTargetEffect(2).apply(game, source);//The drawcard method would not work immediately
            int amountOfCardsToDraw = controller.getAmount(0, 4, message2, game);
            new DrawCardSourceControllerEffect(amountOfCardsToDraw).apply(game, source);
            while (targetOpponent.chooseUse(Outcome.AIDontUseIt, message, source, game)) {
                new DrawCardTargetEffect(2).apply(game, source);
                amountOfCardsToDraw = controller.getAmount(0, 4, message2, game);
                new DrawCardSourceControllerEffect(amountOfCardsToDraw).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public TradeSecretsEffect copy() {
        return new TradeSecretsEffect(this);
    }
}
