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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000 & L_J
 */
public class PulseOfTheDross extends CardImpl {

    public PulseOfTheDross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Target player reveals three cards from their hand and you choose one of them. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(TargetController.ANY, 3));
        this.getSpellAbility().addEffect(new PulseOfTheDrossReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public PulseOfTheDross(final PulseOfTheDross card) {
        super(card);
    }

    @Override
    public PulseOfTheDross copy() {
        return new PulseOfTheDross(this);
    }
}

class PulseOfTheDrossReturnToHandEffect extends OneShotEffect {

    PulseOfTheDrossReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if that player has more cards in hand than you, return {this} to its owner's hand";
    }

    PulseOfTheDrossReturnToHandEffect(final PulseOfTheDrossReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheDrossReturnToHandEffect copy() {
        return new PulseOfTheDrossReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) { 
                if (player.getHand().size() > controller.getHand().size()) {
                    Card card = game.getCard(source.getSourceId());
                    controller.moveCards(card, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
