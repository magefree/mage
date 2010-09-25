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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterPermanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AcidicSlime extends CardImpl<AcidicSlime> {

	private static FilterPermanent filter = new FilterPermanent("artifact, enchantment, or land");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.getCardType().add(CardType.ENCHANTMENT);
		filter.getCardType().add(CardType.LAND);
		filter.setScopeCardType(ComparisonScope.Any);
	}

	public AcidicSlime(UUID ownerId) {
		super(ownerId, "Acidic Slime", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
		this.expansionSetCode = "M10";
		this.subtype.add("Ooze");
		this.color.setGreen(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		this.addAbility(DeathtouchAbility.getInstance());
		Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
		Target target = new TargetPermanent(filter, TargetController.ANY);
		target.setRequired(true);
		ability.addTarget(target);
		this.addAbility(ability);
	}

	public AcidicSlime(final AcidicSlime card) {
		super(card);
	}

	@Override
	public AcidicSlime copy() {
		return new AcidicSlime(this);
	}

	@Override
	public String getArt() {
		return "121561_typ_reg_sty_010.jpg";
	}

}
