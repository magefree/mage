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
package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class WorldlyCounsel extends CardImpl<WorldlyCounsel> {

    public WorldlyCounsel(UUID ownerId) {
        super(ownerId, 39, "Worldly Counsel", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "CON";

        this.color.setBlue(true);

        // Domain - Look at the top X cards of your library, where X is the number of basic land types among lands you control. Put one of those cards into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new WorldlyCounselEffect());
    }

    public WorldlyCounsel(final WorldlyCounsel card) {
        super(card);
    }

    @Override
    public WorldlyCounsel copy() {
        return new WorldlyCounsel(this);
    }
}

class WorldlyCounselEffect extends OneShotEffect<WorldlyCounselEffect> {

    public WorldlyCounselEffect() {
        super(Outcome.DrawCard);
        this.staticText = "// Domain - Look at the top X cards of your library, where X is the number of basic land types among lands you control. Put one of those cards into your hand and the rest on the bottom of your library in any order";
    }

    public WorldlyCounselEffect(final WorldlyCounselEffect effect) {
        super(effect);
    }

    @Override
    public WorldlyCounselEffect copy() {
        return new WorldlyCounselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = (new DomainValue()).calculate(game, source);
        count = Math.min(player.getLibrary().size(), count);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards("Worldly Counsel", cards, game);

        if (!cards.isEmpty()) {
            if (cards.size() == 1) {
                Card card = cards.getRandom(game);
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getId(), game, false);
                return true;
            } else {
                TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put into your hand"));
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                }
            }
        }


        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }
        return true;
    }
}
