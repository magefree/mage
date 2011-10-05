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
package mage.sets.innistrad;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.Constants.Duration;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;

/**
 * @author nantuko
 */
public class MorkrutBanshee extends CardImpl<MorkrutBanshee> {

    private final static String staticText = "Morbid - When {this} enters the battlefield, if a creature died this turn, target creature gets -4/-4 until end of turn.";

	public MorkrutBanshee(UUID ownerId) {
		super(ownerId, 110, "Morkrut Banshee", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
		this.expansionSetCode = "ISD";
		this.subtype.add("Spirit");

		this.color.setBlack(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(4);

		// Morbid - When Morkut Banshee enters the battlefield, if a creature died this turn, target creature gets -4/-4 until end of turn.
        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-4, -4, Duration.EndOfTurn));
		TriggeredAbility ability = new ConditionalTriggeredAbility(triggeredAbility, MorbidCondition.getInstance(), staticText);
		ability.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability);
	}

	public MorkrutBanshee(final MorkrutBanshee card) {
		super(card);
	}

	@Override
	public MorkrutBanshee copy() {
		return new MorkrutBanshee(this);
	}
}
