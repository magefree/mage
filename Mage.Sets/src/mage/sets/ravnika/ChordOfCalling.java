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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jonubuu
 */
public class ChordOfCalling extends CardImpl<ChordOfCalling> {

    public ChordOfCalling(UUID ownerId) {
        super(ownerId, 156, "Chord of Calling", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{G}{G}{G}");
        this.expansionSetCode = "RAV";

        this.color.setGreen(true);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Search your library for a creature card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new ChordofCallingSearchEffect());
    }

    public ChordOfCalling(final ChordOfCalling card) {
        super(card);
    }

    @Override
    public ChordOfCalling copy() {
        return new ChordOfCalling(this);
    }
}

class ChordofCallingSearchEffect extends OneShotEffect<ChordofCallingSearchEffect> {

    ChordofCallingSearchEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Search your library for a creature card with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    ChordofCallingSearchEffect(final ChordofCallingSearchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        int xCost = source.getManaCostsToPay().getX();
        FilterCard filter = new FilterCard(new StringBuilder("creature card with converted mana cost ").append(xCost).append(" or less").toString());
        filter.add(new CardTypePredicate(CardType.CREATURE));
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, xCost + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    game.informPlayers(new StringBuilder(sourceCard.getName()).append(": Put ").append(card.getName()).append(" onto the battlefield").toString());
                    card.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getSourceId(), source.getControllerId());
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

    @Override
    public ChordofCallingSearchEffect copy() {
        return new ChordofCallingSearchEffect(this);
    }
}
