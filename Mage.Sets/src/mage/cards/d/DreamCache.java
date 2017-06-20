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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public class DreamCache extends CardImpl {

    public DreamCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.
        this.getSpellAbility().addEffect(new DreamCacheEffect());
    }

    public DreamCache(final DreamCache card) {
        super(card);
    }

    @Override
    public DreamCache copy() {
        return new DreamCache(this);
    }
}

class DreamCacheEffect extends OneShotEffect {

    public DreamCacheEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.";
    }

    public DreamCacheEffect(final DreamCacheEffect effect) {
        super(effect);
    }

    @Override
    public DreamCacheEffect copy() {
        return new DreamCacheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(3, game);
            boolean putOnTop = controller.chooseUse(Outcome.Neutral, "Put cards on top?", source, game);
            TargetCardInHand target = new TargetCardInHand(2, 2, new FilterCard());
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            Cards cardsToLibrary = new CardsImpl(target.getTargets());
            if (!cardsToLibrary.isEmpty()) {
                if (putOnTop) {
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                } else {
                    controller.putCardsOnBottomOfLibrary(cardsToLibrary, game, source, false);
                }
            }
            return true;
        }
        return false;
    }

}
