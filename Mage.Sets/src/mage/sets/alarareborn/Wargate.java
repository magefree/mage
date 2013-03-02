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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class Wargate extends CardImpl<Wargate> {

    public Wargate(UUID ownerId) {
        super(ownerId, 129, "Wargate", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}{W}{U}");
        this.expansionSetCode = "ARB";

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);

        // Search your library for a permanent card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new WargateEffect());
    }

    public Wargate(final Wargate card) {
        super(card);
    }

    @Override
    public Wargate copy() {
        return new Wargate(this);
    }
}


class WargateEffect extends OneShotEffect<WargateEffect> {
    WargateEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Search your library for a permanent card with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    WargateEffect(final WargateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanentCard filter = new FilterPermanentCard("permanent card with converted mana cost X or less");
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    card.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getId(), source.getControllerId());
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

    @Override
    public WargateEffect copy() {
        return new WargateEffect(this);
    }

}