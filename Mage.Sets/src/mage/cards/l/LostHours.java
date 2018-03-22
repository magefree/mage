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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public class LostHours extends CardImpl {

    public LostHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target player reveals their hand. You choose a nonland card from it. That player puts that card into their library third from the top.
        this.getSpellAbility().addEffect(new LostHoursEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public LostHours(final LostHours card) {
        super(card);
    }

    @Override
    public LostHours copy() {
        return new LostHours(this);
    }
}

class LostHoursEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public LostHoursEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand. You choose a nonland card from it. That player puts that card into their library third from the top.";
    }
    
    public LostHoursEffect(final LostHoursEffect effect) {
        super(effect);
    }

    @Override
    public LostHoursEffect copy() {
        return new LostHoursEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            player.revealCards("Lost Hours", player.getHand(), game);
            
            if (player.getHand().size() > 0) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard(filter));
                if (controller.choose(Outcome.Discard, player.getHand(), target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                    // Move card to third position
                        CardsImpl cards = new CardsImpl();
                        cards.add(card);
                        Card cardTop = null;
                        Card cardSecond = null;
                        if (player.getLibrary().hasCards()) {
                            cardTop = player.getLibrary().removeFromTop(game);
                        }
                        if (player.getLibrary().hasCards()) {
                            cardSecond = player.getLibrary().removeFromTop(game);
                        }
                        player.putCardsOnTopOfLibrary(cards, game, source, true);
                        if (cardSecond != null) {
                            player.getLibrary().putOnTop(cardSecond, game);
                        }
                        if (cardTop != null) {
                            player.getLibrary().putOnTop(cardTop, game);
                        }
                        game.informPlayers(card.getLogName() + " is put into " + player.getLogName() +"'s library " + (cardTop != null ? (cardSecond != null ? "third" : "second") : "first") + " from the top");
                    }
                }
            }
            return true;
        }
        return false;
    }
    
}
