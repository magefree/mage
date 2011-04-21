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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Viserion
 */
public class Harrow extends CardImpl<Harrow> {
	
	static FilterControlledPermanent filter = new FilterControlledPermanent("a land.");
	
	static{
		filter.getCardType().add(CardType.LAND);
	}

	public Harrow(UUID ownerId){
		super(ownerId, 165, "Harrow", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
		this.expansionSetCode = "ZEN";
		this.color.setGreen(true);		
		
		this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
		TargetCardInLibrary target = new TargetCardInLibrary(0, 2, new FilterBasicLandCard());
		this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, false, Outcome.PutLandInPlay));
	}

	public Harrow(final Harrow card) {
		super(card);
	}

	@Override
	public Harrow copy() {
		return new Harrow(this);
	}
}
