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

package mage.sets.shardsofalara;

import mage.abilities.effects.common.ReturnFromExileEffect;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class OblivionRing extends CardImpl<OblivionRing> {

	public OblivionRing(UUID ownerId) {
		super(ownerId, 20, "Oblivion Ring", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
		this.expansionSetCode = "ALA";
		this.color.setWhite(true);
		FilterNonlandPermanent filter = new FilterNonlandPermanent();
		filter.setId(this.getId());
		filter.setNotId(true);
		Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(this.getId(), "Oblivion Ring exile"), false);
		Target target = new TargetPermanent(filter);
        target.setRequired(true);
        ability1.addTarget(target);
		this.addAbility(ability1);
		Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileEffect(this.getId(), Zone.BATTLEFIELD), false);
		this.addAbility(ability2);
	}

	public OblivionRing(final OblivionRing card) {
		super(card);
	}

	@Override
	public OblivionRing copy() {
		return new OblivionRing(this);
	}

	@Override
	public String getArt() {
		return "115005_typ_reg_sty_010.jpg";
	}

}
