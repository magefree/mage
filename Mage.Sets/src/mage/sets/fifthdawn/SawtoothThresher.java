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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public class SawtoothThresher extends CardImpl<SawtoothThresher> {

    public SawtoothThresher(UUID ownerId) {
        super(ownerId, 149, "Sawtooth Thresher", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Construct");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sunburst
        this.addAbility(new SunburstAbility());
        // Remove two +1/+1 counters from Sawtooth Thresher: Sawtooth Thresher gets +4/+4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Constants.Duration.EndOfTurn), new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2))));
    }

    public SawtoothThresher(final SawtoothThresher card) {
        super(card);
    }

    @Override
    public SawtoothThresher copy() {
        return new SawtoothThresher(this);
    }
}
