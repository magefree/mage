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
package mage.sets.knightsvsdragons;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author FenrisulfrX
 */
public class EdgeOfAutumn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");
    private static final FilterBasicLandCard basiclandfilter = new FilterBasicLandCard();

    public EdgeOfAutumn(UUID ownerId) {
        super(ownerId, 25, "Edge of Autumn", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "DDG";

        // If you control four or fewer lands, search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(basiclandfilter), true),
                new PermanentsOnTheBattlefieldCondition(filter, PermanentsOnTheBattlefieldCondition.CountType.FEWER_THAN, 5),
                "If you control four or fewer lands, search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library."));

        // Cycling-Sacrifice a land.
        Ability cycling = new CyclingAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(cycling);
    }

    public EdgeOfAutumn(final EdgeOfAutumn card) {
        super(card);
    }

    @Override
    public EdgeOfAutumn copy() {
        return new EdgeOfAutumn(this);
    }
}
