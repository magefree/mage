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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public class SewerRats extends CardImpl {

    public SewerRats(UUID ownerId) {
        super(ownerId, 37, "Sewer Rats", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Rat");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, Pay 1 life: Sewer Rats gets +1/+0 until end of turn. Activate this ability no more than three times each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{B}"), 3);
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    public SewerRats(final SewerRats card) {
        super(card);
    }

    @Override
    public SewerRats copy() {
        return new SewerRats(this);
    }
}
