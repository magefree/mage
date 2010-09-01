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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PlayTargetWithoutPayingManaEffect;
import mage.abilities.effects.common.SearchLibraryRevealPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.sets.Worldwake;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StoneforgeMystic extends CardImpl<StoneforgeMystic> {

	private static FilterCard filter = new FilterCard("an Equipment card");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.getSubtype().add("Equipment");
	}

	public StoneforgeMystic(UUID ownerId) {
		super(ownerId, "Stoneforge Mystic", new CardType[]{CardType.CREATURE}, "{1}{W}");
		this.expansionSetId = Worldwake.getInstance().getId();
		this.color.setWhite(true);
		this.subtype.add("Kor");
		this.subtype.add("Artificer");
		this.power = new MageInt(1);
		this.toughness = new MageInt(2);

		TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
		this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryRevealPutInHandEffect(target), true));

		SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlayTargetWithoutPayingManaEffect(), new ManaCostsImpl("{1}{W}"));
		ability.addCost(new TapSourceCost());
		ability.addTarget(new TargetCardInHand(0, 1, filter));
		this.addAbility(ability);
	}

	public StoneforgeMystic(final StoneforgeMystic card) {
		super(card);
	}

	@Override
	public StoneforgeMystic copy() {
		return new StoneforgeMystic(this);
	}

	@Override
	public String getArt() {
		return "126571_typ_reg_sty_010.jpg";
	}

}
