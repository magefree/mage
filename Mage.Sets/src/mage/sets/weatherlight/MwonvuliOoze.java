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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class MwonvuliOoze extends CardImpl {

    public MwonvuliOoze(UUID ownerId) {
        super(ownerId, 77, "Mwonvuli Ooze", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "WTH";
        this.subtype.add("Ooze");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));
        // Mwonvuli Ooze's power and toughness are each equal to 1 plus twice the number of age counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new MwonvuliOozePTValue(), Duration.EndOfGame)));
    }

    public MwonvuliOoze(final MwonvuliOoze card) {
        super(card);
    }

    @Override
    public MwonvuliOoze copy() {
        return new MwonvuliOoze(this);
    }
}


class MwonvuliOozePTValue extends CountersSourceCount {

    public MwonvuliOozePTValue() {
        super(CounterType.AGE);
    }

    public MwonvuliOozePTValue(final MwonvuliOozePTValue value) {
        super(value);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 2 * super.calculate(game, sourceAbility, effect) + 1;
    }

    @Override
    public MwonvuliOozePTValue copy() {
        return new MwonvuliOozePTValue(this);
    }

    @Override
    public String getMessage() {
        return "1 plus twice the number of age counters on it";
    }
}
