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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TimeSieve extends CardImpl {

    public TimeSieve(UUID ownerId) {
        super(ownerId, 31, "Time Sieve", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{U}{B}");
        this.expansionSetCode = "ARB";

        // {tap}, Sacrifice five artifacts: Take an extra turn after this one.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(5, 5, new FilterControlledArtifactPermanent("five artifacts"), true)));
        this.addAbility(ability);

    }

    public TimeSieve(final TimeSieve card) {
        super(card);
    }

    @Override
    public TimeSieve copy() {
        return new TimeSieve(this);
    }
}
