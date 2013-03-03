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
package mage.sets.timespiral;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class SearchForTomorrow extends CardImpl<SearchForTomorrow> {

    public SearchForTomorrow(UUID ownerId) {
        super(ownerId, 216, "Search for Tomorrow", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "TSP";

        this.color.setGreen(true);

        // Search your library for a basic land card and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, true, Constants.Outcome.PutLandInPlay));
        
        // Suspend 2-{G}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl("{G}"), this));
    }

    public SearchForTomorrow(final SearchForTomorrow card) {
        super(card);
    }

    @Override
    public SearchForTomorrow copy() {
        return new SearchForTomorrow(this);
    }
}
