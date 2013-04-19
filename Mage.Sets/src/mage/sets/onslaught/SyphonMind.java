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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class SyphonMind extends CardImpl<SyphonMind> {

    public SyphonMind(UUID ownerId) {
        super(ownerId, 175, "Syphon Mind", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "ONS";

        this.color.setBlack(true);

        // Each other player discards a card. You draw a card for each card discarded this way.
        this.getSpellAbility().addEffect(new SyphonMindEffect());

    }

    public SyphonMind(final SyphonMind card) {
        super(card);
    }

    @Override
    public SyphonMind copy() {
        return new SyphonMind(this);
    }
}

class SyphonMindEffect extends OneShotEffect<SyphonMindEffect> {

    public SyphonMindEffect() {
        super(Constants.Outcome.Discard);
        this.staticText = "Each other player discards a card. You draw a card for each card discarded this way";
    }

    public SyphonMindEffect(final SyphonMindEffect effect) {
        super(effect);
    }

    @Override
    public SyphonMindEffect copy() {
        return new SyphonMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        boolean result = false;
        Player you = game.getPlayer(source.getControllerId());
        for (UUID playerId : you.getInRange()) {
            if (!playerId.equals(source.getControllerId())) {
                Player otherPlayer = game.getPlayer(playerId);
                if (otherPlayer != null && otherPlayer.getHand().size() > 0) {
                    TargetCardInHand target = new TargetCardInHand();
                    if (otherPlayer.choose(Constants.Outcome.Discard, target, source.getSourceId(), game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            if (otherPlayer.discard(card, source, game)) {
                                amount += 1;
                                result = true;
                                target.clearChosen();
                            }
                        }
                    }
                }
            }
        }
        if (you != null) {
            you.drawCards(amount, game);
        }
        return result;
    }
}
