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
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class NotForgotten extends CardImpl {

    public NotForgotten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Put target card from a graveyard on the top or bottom of its owner's library. 
        // Put a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new NotForgottenEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
    }

    public NotForgotten(final NotForgotten card) {
        super(card);
    }

    @Override
    public NotForgotten copy() {
        return new NotForgotten(this);
    }
}

class NotForgottenEffect extends OneShotEffect {
    
    public NotForgottenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target card from a graveyard on the top or bottom of its owner's library. Put a 1/1 white Spirit creature token with flying onto the battlefield.";
    }
    
    public NotForgottenEffect(final NotForgottenEffect effect) {
        super(effect);
    }
    
    @Override
    public NotForgottenEffect copy() {
        return new NotForgottenEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(source.getFirstTarget());
        
        if (controller != null && targetCard != null) {
            boolean onTop = controller.chooseUse(outcome, "Put " + targetCard.getName() + " on top of it's owners library (otherwise on bottom)?", source, game);
            boolean moved = controller.moveCardToLibraryWithInfo(targetCard, source.getSourceId(), game, Zone.GRAVEYARD, onTop, true);
            if (moved) {
                Token token = new SpiritWhiteToken();
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false);                
                return true;
            }
        }
        return false;
    }
}
