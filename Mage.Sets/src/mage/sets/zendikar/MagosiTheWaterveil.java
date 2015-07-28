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
package mage.sets.zendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.ReturnToHandSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public class MagosiTheWaterveil extends CardImpl {

    public MagosiTheWaterveil(UUID ownerId) {
        super(ownerId, 218, "Magosi, the Waterveil", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ZEN";

        // Magosi, the Waterveil enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} to your mana pool.
        this.addAbility(new BlueManaAbility());

        // {U}, {T}: Put an eon counter on Magosi, the Waterveil. Skip your next turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.EON.createInstance()), new ManaCostsImpl("{U}"));
        ability.addEffect(new SkipNextTurnSourceEffect());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove an eon counter from Magosi, the Waterveil and return it to its owner's hand: Take an extra turn after this one.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.EON.createInstance()));
        ability2.addCost(new ReturnToHandSourceCost());
        this.addAbility(ability2);

    }

    public MagosiTheWaterveil(final MagosiTheWaterveil card) {
        super(card);
    }

    @Override
    public MagosiTheWaterveil copy() {
        return new MagosiTheWaterveil(this);
    }
}
