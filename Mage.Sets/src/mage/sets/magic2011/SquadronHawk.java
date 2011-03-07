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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SearchLibraryRevealPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SquadronHawk extends CardImpl<SquadronHawk> {

	private static FilterCard filter = new FilterCard("cards named Squadron Hawk");

	static {
		filter.getName().add("Squadron Hawk");
	}

	public SquadronHawk(UUID ownerId) {
		super(ownerId, 33, "Squadron Hawk", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Bird");
		this.color.setWhite(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(FlyingAbility.getInstance());
		TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filter);
		this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryRevealPutInHandEffect(target), true));
	}

	public SquadronHawk(final SquadronHawk card) {
		super(card);
	}

	@Override
	public SquadronHawk copy() {
		return new SquadronHawk(this);
	}

	@Override
	public String getArt() {
		return "129185_typ_reg_sty_010.jpg";
	}

}
