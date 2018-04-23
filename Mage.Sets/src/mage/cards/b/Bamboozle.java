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
package mage.cards.b;

import java.util.Set;
import java.util.UUID;
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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Bamboozle extends CardImpl {

    public Bamboozle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Target player reveals the top four cards of their library. You choose two of those cards and put them into their graveyard. Put the rest on top of their library in any order.
        this.getSpellAbility().addEffect(new BamboozleEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public Bamboozle(final Bamboozle card) {
        super(card);
    }

    @Override
    public Bamboozle copy() {
        return new Bamboozle(this);
    }
}

class BamboozleEffect extends OneShotEffect {

    BamboozleEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals the top four cards of their library. You choose two of those cards and put them into their graveyard. Put the rest on top of their library in any order";
    }

    BamboozleEffect(final BamboozleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Cards putOnTopLibrary = new CardsImpl();
        Cards putInGraveyard = new CardsImpl();
        if (targetPlayer != null
                && controller != null) {
            Set<Card> top4Cards = targetPlayer.getLibrary().getTopCards(game, 4);
            for (Card card : top4Cards) {
                putOnTopLibrary.add(card);
            }
            targetPlayer.revealCards("Bamboozle Reveal", putOnTopLibrary, game);
            TargetCard target = new TargetCard(2, Zone.LIBRARY, new FilterCard("2 cards out of this stack to put into their graveyard"));
            if (controller.choose(Outcome.Discard, putOnTopLibrary, target, game)) {
                for (UUID cardId : target.getTargets()) {
                    putInGraveyard.add(game.getCard(cardId));
                    putOnTopLibrary.remove(game.getCard(cardId));
                }
                targetPlayer.moveCards(putInGraveyard, Zone.GRAVEYARD, source, game);
                targetPlayer.putCardsOnTopOfLibrary(putOnTopLibrary, game, source, false);
                return true;
            }

        }
        return false;
    }

    @Override
    public BamboozleEffect copy() {
        return new BamboozleEffect(this);
    }

}
