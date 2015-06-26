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
package mage.abilities.effects.common.discard;

import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 * @author mluds
 */
public class DiscardHandAndDrawEffect extends OneShotEffect {

    private int CardDrawOffset = 0;
    public DiscardHandAndDrawEffect(TargetController targetController) {
        this(targetController,0);
    }
    public DiscardHandAndDrawEffect(TargetController targetController, int cardDrawOffset) {
        super(Outcome.Discard);
        String PostText = "";
        if (cardDrawOffset >0){
            PostText = " plus " + cardDrawOffset;
        }else if(cardDrawOffset < 0){
            PostText = " minus " + cardDrawOffset;
        }
        this.CardDrawOffset = cardDrawOffset;
        switch(targetController) {
            case OPPONENT:
                this.staticText = "Opponent discards his hand then draws that many cards" + PostText;
               break;
            case YOU:
                this.staticText = "Discard your hand then draws that many cards" + PostText;
               break;
        }
    }

    public DiscardHandAndDrawEffect(final DiscardHandAndDrawEffect effect) {
        super(effect);
    }

    @Override
    public DiscardHandAndDrawEffect copy() {
        return new DiscardHandAndDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                Set<Card> cards = player.getHand().getCards(game);
                int count = cards.size();
                for (Card card : cards) {
                    player.discard(card, source, game);
                }
                player.drawCards(count + this.CardDrawOffset, game);
            }
        return true;
    }
}
