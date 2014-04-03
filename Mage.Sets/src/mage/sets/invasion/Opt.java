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
package mage.sets.invasion;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author KholdFuzion
 */
public class Opt extends CardImpl<Opt> {

    public Opt(UUID ownerId) {
        super(ownerId, 64, "Opt", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "INV";

        this.color.setBlue(true);

        // Look at the top card of your library. You may put that card on the bottom of your library.
        // This is functionally the same as scry, copy scry effect, removing "scry", unless theres a simpler way im overlooking?
        this.getSpellAbility().addEffect(new OptEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Opt(final Opt card) {
        super(card);
    }

    @Override
    public Opt copy() {
        return new Opt(this);
    }
}
class OptEffect extends OneShotEffect<OptEffect> {

    protected static FilterCard filter1 = new FilterCard("card to put on the bottom of your library");

    public OptEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. You may put that card on the bottom of your library.";
    }

    public OptEffect(final OptEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            int count = Math.min( 1, player.getLibrary().size());
            if (count == 0) {
                return false;
            }
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
            TargetCard target1 = new TargetCard(Zone.PICK, filter1);
            // move cards to the bottom of the library
            while (cards.size() > 0 && player.choose(Outcome.Detriment, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                }
                target1.clearChosen();
            }
            // move cards to the top of the library
            int onBottom = 1 - cards.size();

            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            }
            game.informPlayers(new StringBuilder(player.getName()).append(" puts ")
                    .append(onBottom).append(onBottom == 1 ?" card":" cards")
                    .append(" on the bottom of his or her library")
                    .append(1).append(")").toString());
            return true;
        }
        return false;
    }

    @Override
    public OptEffect copy() {
        return new OptEffect(this);
    }

}
