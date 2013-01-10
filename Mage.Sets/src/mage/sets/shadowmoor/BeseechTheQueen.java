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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class BeseechTheQueen extends CardImpl<BeseechTheQueen> {

    private static final FilterCard filter = new FilterCard("card with converted mana cost less than or equal to the number of lands you control");
    static{
        filter.add(new BeseechTheQueenPredicate());
    }
    public BeseechTheQueen(UUID ownerId) {
        super(ownerId, 57, "Beseech the Queen", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2/B}{2/B}{2/B}");
        this.expansionSetCode = "SHM";

        this.color.setBlack(true);

        // <i>({2B} can be paid with any two mana or with {B}. This card's converted mana cost is 6.)</i>
        // Search your library for a card with converted mana cost less than or equal to the number of lands you control, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    public BeseechTheQueen(final BeseechTheQueen card) {
        super(card);
    }

    @Override
    public BeseechTheQueen copy() {
        return new BeseechTheQueen(this);
    }
}


class BeseechTheQueenPredicate implements Predicate<Card> {


    @Override
    public final boolean apply(Card input, Game game) {
        if(input.getManaCost().convertedManaCost() <= game.getBattlefield().getAllActivePermanents(new FilterControlledLandPermanent(), input.getOwnerId(), game).size()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "card with converted mana cost less than or equal to the number of lands you control";
    }
}