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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class TimeBomb extends CardImpl {

    public TimeBomb(UUID ownerId) {
        super(ownerId, 223, "Time Bomb", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ME2";

        // At the beginning of your upkeep, put a time counter on Time Bomb.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(), true), TargetController.YOU, false));

        // {1}, {tap}, Sacrifice Time Bomb: Time Bomb deals damage equal to the number of time counters on it to each creature and each player.
        Effect effect = new DamageEverythingEffect(new CountersSourceCount(CounterType.TIME), new FilterCreaturePermanent());
        effect.setText("{this} deals damage equal to the number of time counters on it to each creature and each player");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public TimeBomb(final TimeBomb card) {
        super(card);
    }

    @Override
    public TimeBomb copy() {
        return new TimeBomb(this);
    }
}
