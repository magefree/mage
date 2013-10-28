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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth

 */
public class Quillspike extends CardImpl<Quillspike> {

    public Quillspike(UUID ownerId) {
        super(ownerId, 126, "Quillspike", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B/G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {BG}, Remove a -1/-1 counter from a creature you control: Quillspike gets +3/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl("{B/G}"));
        TargetPermanent target = new TargetPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
        ability.addCost(new RemoveCounterCost(target, CounterType.M1M1));
        this.addAbility(ability);
        
    }

    public Quillspike(final Quillspike card) {
        super(card);
    }

    @Override
    public Quillspike copy() {
        return new Quillspike(this);
    }
}
