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
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantTargetSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterStackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SacredWolf extends CardImpl<SacredWolf> {

	private static FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

	static {
		filter.setTargetController(TargetController.OPPONENT);
	}

	public SacredWolf(UUID ownerId) {
		super(ownerId, 196, "Sacred Wolf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
		this.expansionSetCode = "M11";
		this.subtype.add("Wolf");
		this.color.setGreen(true);
		this.power = new MageInt(3);
		this.toughness = new MageInt(1);
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantTargetSourceEffect(filter, Duration.WhileOnBattlefield)));
	}

	public SacredWolf(final SacredWolf card) {
		super(card);
	}

	@Override
	public SacredWolf copy() {
		return new SacredWolf(this);
	}

	@Override
	public String getArt() {
		return "129113_typ_reg_sty_010.jpg";
	}

}

