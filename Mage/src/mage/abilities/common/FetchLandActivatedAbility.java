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

package mage.abilities.common;

import java.util.Arrays;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FetchLandActivatedAbility extends ActivatedAbilityImpl<FetchLandActivatedAbility> {

    public FetchLandActivatedAbility(String[] subTypes) {
        super(Zone.BATTLEFIELD, null);
        addCost(new TapSourceCost());
        addCost(new PayLifeCost(1));
        addCost(new SacrificeSourceCost());
        FilterCard filter = new FilterCard(subTypeNames(subTypes));
        filter.getCardType().add(CardType.LAND);
        filter.getSubtype().addAll(Arrays.asList(subTypes));
        filter.setScopeSubtype(ComparisonScope.Any);
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, false, true, Outcome.PutLandInPlay));
    }

    public FetchLandActivatedAbility(FetchLandActivatedAbility ability) {
        super(ability);
    }

    private String subTypeNames(String[] subTypes) {
        StringBuilder sb = new StringBuilder();
        for (String subType: subTypes) {
            sb.append(subType).append(" or ");
        }
        return sb.substring(0, sb.length() - 4);
    }

    @Override
    public FetchLandActivatedAbility copy() {
        return new FetchLandActivatedAbility(this);
    }
}
