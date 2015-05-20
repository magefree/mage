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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.condition.common.OpponentControllsMoreCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class GiftOfEstates extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Plains cards");
    
    static {
        filter.add(new SubtypePredicate("Plains"));
    }
    

    public GiftOfEstates(UUID ownerId) {
        super(ownerId, 73, "Gift of Estates", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{W}");
        this.expansionSetCode = "C14";


        // If an opponent controls more lands than you, search your library for up to three Plains cards, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 3, filter), true),
                new OpponentControllsMoreCondition(new FilterLandPermanent("lands"))));
    }

    public GiftOfEstates(final GiftOfEstates card) {
        super(card);
    }

    @Override
    public GiftOfEstates copy() {
        return new GiftOfEstates(this);
    }
}
