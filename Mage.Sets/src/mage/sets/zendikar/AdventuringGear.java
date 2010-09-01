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
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.sets.Zendikar;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AdventuringGear extends CardImpl<AdventuringGear> {

	public AdventuringGear(UUID ownerId) {
		super(ownerId, "Adventuring Gear", new CardType[]{CardType.ARTIFACT}, "{1}");
		this.expansionSetId = Zendikar.getInstance().getId();
		this.subtype.add("Equipment");
		this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
		this.addAbility(new LandfallAbility(new BoostEquippedEffect(2, 2, Duration.EndOfTurn), false));
	}

	public AdventuringGear(final AdventuringGear card) {
		super(card);
	}

	@Override
	public AdventuringGear copy() {
		return new AdventuringGear(this);
	}

	@Override
	public String getArt() {
		return "123577_typ_reg_sty_010.jpg";
	}

}
