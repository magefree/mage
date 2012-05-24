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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class DescendantsPath extends CardImpl<DescendantsPath> {

    public DescendantsPath(UUID ownerId) {
        super(ownerId, 173, "Descendants' Path", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // At the beginning of your upkeep, reveal the top card of your library. If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DescendantsPathEffect(), Constants.TargetController.YOU, false);
        this.addAbility(ability);
    }

    public DescendantsPath(final DescendantsPath card) {
        super(card);
    }

    @Override
    public DescendantsPath copy() {
        return new DescendantsPath(this);
    }
}

class DescendantsPathEffect extends OneShotEffect<DescendantsPathEffect> {

    public DescendantsPathEffect() {
        super(Constants.Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library";
    }

    public DescendantsPathEffect(final DescendantsPathEffect effect) {
        super(effect);
    }

    @Override
    public DescendantsPathEffect copy() {
        return new DescendantsPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().getFromTop(game);
                player.revealCards("DescendantsPath", new CardsImpl(card), game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                    filter.getSubtype().addAll(card.getSubtype());
                    filter.setScopeSubtype(Filter.ComparisonScope.Any);
                    int count = game.getBattlefield().getAllActivePermanents(filter, player.getId()).size();
                    if (count > 0) {
                        game.informPlayers("DescendantsPath: Found a creature that shares a creature type with the revealed card.");
                        if (player.chooseUse(Constants.Outcome.Benefit, "Cast the card?", game)) {
                            player.cast(card.getSpellAbility(), game, true);
                        } else {
                            game.informPlayers("DescendantsPath: " + player.getName() + " canceled casting the card.");
                            player.getLibrary().putOnBottom(card, game);
                        }
                    } else {
                        game.informPlayers("DescendantsPath: No creature that shares a creature type with the revealed card.");
                        player.getLibrary().putOnBottom(card, game);
                    }
                } else {
                    player.getLibrary().putOnBottom(card, game);
                }

                return true;
            }
        }
        return false;
    }
}

