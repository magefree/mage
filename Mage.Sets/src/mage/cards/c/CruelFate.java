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
package mage.cards.c;

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
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801 & L_J
 */
public class CruelFate extends CardImpl {

    public CruelFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Look at the top five cards of target opponent's library. Put one of those cards into that player's graveyard and the rest on top of their library in any order.
        this.getSpellAbility().addEffect(new CruelFateEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public CruelFate(final CruelFate card) {
        super(card);
    }

    @Override
    public CruelFate copy() {
        return new CruelFate(this);
    }
}

class CruelFateEffect extends OneShotEffect {

    public CruelFateEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of target opponent's library. Put one of those cards into that player's graveyard and the rest on top of their library in any order";
    }

    public CruelFateEffect(final CruelFateEffect effect) {
        super(effect);
    }

    @Override
    public CruelFateEffect copy() {
        return new CruelFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            Player you = game.getPlayer(source.getControllerId());
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null && you != null) {
                Cards cards = new CardsImpl();
                int count = Math.min(player.getLibrary().size(), 5);
                for (int i = 0; i < count; i++) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null) {
                        cards.add(card);
                    }
                }

                you.lookAtCards(sourceCard.getIdName(), cards, game);

                // card to put into opponent's graveyard
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into target opponent's graveyard"));
                if (player.canRespond()) {
                    if (cards.size() > 1) {
                        you.choose(Outcome.Detriment, cards, target, game);
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        }
                    }
                    else if (cards.size() == 1) {
                        Card card = cards.get(cards.iterator().next(), game);
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                    }
                }
                // cards to put on the top of opponent's library
                TargetCard target2 = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on the top of target opponent's library"));
                while (player.canRespond() && cards.size() > 1) {
                    you.choose(Outcome.Neutral, cards, target2, game);
                    Card card = cards.get(target2.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                    target2.clearChosen();
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                return true;
            }
        }
        return false;
    }
}
