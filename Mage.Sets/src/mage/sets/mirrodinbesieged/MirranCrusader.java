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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;

/**
 *
 * @author ayratn
 */
public class MirranCrusader extends CardImpl<MirranCrusader> {
	private static FilterCard filter = new FilterCard("Black");
	private static FilterCard filter2 = new FilterCard("Green");

	static {
		filter.setUseColor(true);
		filter.getColor().setBlack(true);
		filter.setScopeColor(ComparisonScope.Any);

		filter2.setUseColor(true);
		filter2.getColor().setGreen(true);
		filter2.setScopeColor(ComparisonScope.Any);
	}

    public MirranCrusader (UUID ownerId) {
        super(ownerId, 14, "Mirran Crusader", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Human");
        this.subtype.add("Knight");
		this.color.setWhite(true);
		this.power = new MageInt(2);
        this.toughness = new MageInt(2);
		this.addAbility(DoubleStrikeAbility.getInstance());
		this.addAbility(new ProtectionAbility(filter));
		this.addAbility(new ProtectionAbility(filter2));
    }

    public MirranCrusader (final MirranCrusader card) {
        super(card);
    }

    @Override
    public MirranCrusader copy() {
        return new MirranCrusader(this);
    }

}
