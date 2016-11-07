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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TunnelVision extends CardImpl {

    public TunnelVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");

        // Name a card. Target player reveals cards from the top of his or her library until the named card is revealed. 
        // If it is, that player puts the rest of the revealed cards into his or her graveyard and puts the named card on top of his or her library. 
        // Otherwise, the player shuffles his or her library.
        this.getSpellAbility().addEffect(new NameACardEffect(NameACardEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new TunnelVisionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public TunnelVision(final TunnelVision card) {
        super(card);
    }

    @Override
    public TunnelVision copy() {
        return new TunnelVision(this);
    }
}

class TunnelVisionEffect extends OneShotEffect {

    public TunnelVisionEffect() {
        super(Outcome.Damage);
        this.staticText = "Target player reveals cards from the top of his or her library until the named card is revealed. If it is, that player puts the rest of the revealed cards into his or her graveyard and puts the named card on top of his or her library. Otherwise, the player shuffles his or her library.";
    }

    public TunnelVisionEffect(final TunnelVisionEffect effect) {
        super(effect);
    }

    @Override
    public TunnelVisionEffect copy() {
        return new TunnelVisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (sourceObject == null || targetPlayer == null || cardName == null || cardName.isEmpty()) {
            return false;
        }

        Cards cardsToReveal = new CardsImpl();
        Cards cardsToBury = new CardsImpl();
        Card namedCard = null;
                
        // reveal until named card found
        // if named card found, put all revealed cards in grave and put named card on top of library
        // if named card not found, shuffle library
        boolean namedCardFound = false;
        while (targetPlayer.getLibrary().size() > 0) {
            Card card = targetPlayer.getLibrary().removeFromTop(game);
            if (card != null) {
                cardsToReveal.add(card);
                if (card.getName().equals(cardName)) {
                    namedCardFound = true;
                    namedCard = card;
                    break;
                } else {
                    cardsToBury.add(card);
                }
            }
        }
                
        targetPlayer.revealCards(sourceObject.getIdName(), cardsToReveal, game);
        if (namedCardFound) {
            targetPlayer.moveCards(cardsToBury, Zone.GRAVEYARD, source, game);
            targetPlayer.moveCards(namedCard, Zone.LIBRARY, source, game);
        } else {
            targetPlayer.shuffleLibrary(source, game);
        }

        return true;
    }
}
