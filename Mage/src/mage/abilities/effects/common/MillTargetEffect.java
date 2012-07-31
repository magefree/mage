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


package mage.abilities.effects.common;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeff
 */


public class MillTargetEffect extends OneShotEffect<MillTargetEffect> {

    int count = 0;

    public MillTargetEffect(final MillTargetEffect effect) {
        super(effect);
        this.count = effect.count;
    }

    public MillTargetEffect(final int count) {
        super(Constants.Outcome.Detriment);
        this.count = count;
        getText();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            for (int i = 0; i < count; i++) {
                if (!targetPlayer.getLibrary().getCardList().isEmpty()) {
                    Card card = targetPlayer.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MillTargetEffect copy() {
        return new MillTargetEffect(this);
    }
    
    private void getText() {
        StringBuilder sb = new StringBuilder("Target player puts the top ");
        if (count > 1) {
            sb.append(count).append(" cards of his or her library into his or her graveyard");
        }else {
            sb.append("card of his or her library into his or her graveyard");
        }
        staticText = sb.toString();
    }
}
