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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LevelAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.LevelerCard;
import mage.sets.RiseOfTheEldrazi;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KarganDragonlord extends LevelerCard {

	public KarganDragonlord(UUID ownerId) {
		super(ownerId, "Kargan Dragonlord", new CardType[]{CardType.CREATURE}, "{R}{R}");
		this.expansionSetId = RiseOfTheEldrazi.getInstance().getId();
		this.subtype.add("Human");
		this.subtype.add("Warrior");
		this.color.setRed(true);
		this.art = "";
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		this.addAbility(new LevelUpAbility(new ManaCosts("{R}")));
		Abilities abilities1 = new AbilitiesImpl();
		abilities1.add(FlyingAbility.getInstance());
		this.getLevels().add(new LevelAbility(4, 7, abilities1, 4, 4));
		Abilities abilities2 = new AbilitiesImpl();
		abilities2.add(FlyingAbility.getInstance());
		abilities2.add(TrampleAbility.getInstance());
		abilities2.add(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCosts("{R}")));
		this.getLevels().add(new LevelAbility(8, -1, abilities2, 8, 8));
	}


}
