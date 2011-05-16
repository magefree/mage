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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantCounterSourceEffect;
import mage.abilities.effects.common.CantTargetSourceEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterStackObject;

/**
 *
 * @author Loki
 */
public class ThruntheLastTroll extends CardImpl<ThruntheLastTroll> {
    private static FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

	static {
		filter.setTargetController(Constants.TargetController.OPPONENT);
	}

    public ThruntheLastTroll (UUID ownerId) {
        super(ownerId, 92, "Thrun, the Last Troll", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "MBS";
        this.supertype.add("Legendary");
        this.subtype.add("Troll");
        this.subtype.add("Shaman");
		this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new CantCounterSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CantTargetSourceEffect(filter, Constants.Duration.WhileOnBattlefield)));
		this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{1}{G}")));
    }

    public ThruntheLastTroll (final ThruntheLastTroll card) {
        super(card);
    }

    @Override
    public ThruntheLastTroll copy() {
        return new ThruntheLastTroll(this);
    }

}
