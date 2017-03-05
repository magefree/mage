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
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
 * @author Derpthemeus
 */
public class FathomTrawl extends CardImpl {

    public FathomTrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Reveal cards from the top of your library until you reveal three nonland cards. Put the nonland cards revealed this way into your hand, then put the rest of the revealed cards on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new FathomTrawlEffect());
    }

    public FathomTrawl(final FathomTrawl card) {
        super(card);
    }

    @Override
    public FathomTrawl copy() {
        return new FathomTrawl(this);
    }

    class FathomTrawlEffect extends OneShotEffect {

        public FathomTrawlEffect() {
            super(Outcome.DrawCard);
            this.staticText = "Reveal cards from the top of your library until you reveal three nonland cards. Put the nonland cards revealed this way into your hand, then put the rest of the revealed cards on the bottom of your library in any order";
        }

        public FathomTrawlEffect(final FathomTrawlEffect effect) {
            super(effect);
        }

        @Override
        public FathomTrawlEffect copy() {
            return new FathomTrawlEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            MageObject sourceObject = game.getObject(source.getSourceId());
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null || sourceObject == null) {
                return false;
            }
            Cards cards = new CardsImpl();
            Cards nonlandCards = new CardsImpl();
            Cards landCards = new CardsImpl();
            while (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (!card.isLand()) {
                        nonlandCards.add(card);
                        if (nonlandCards.size() == 3) {
                            break;
                        }
                    } else {
                        landCards.add(card);
                    }
                } else {
                    break;
                }
            }
            controller.revealCards(sourceObject.getName(), cards, game);
            controller.moveCards(nonlandCards, Zone.HAND, source, game);
            controller.putCardsOnBottomOfLibrary(landCards, game, source, true);
            return true;
        }
    }
}
