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

package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public class GhaveGuruOfSpores extends CardImpl<GhaveGuruOfSpores> {

    final static FilterControlledPermanent filter = new FilterControlledPermanent("creature to sacrifice");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GhaveGuruOfSpores(UUID ownerId) {
        super(ownerId, 200, "Ghave, Guru of Spores", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{W}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Fungus");
        this.subtype.add("Shaman");
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
		
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
		
        // Ghave, Guru of Spores enters the battlefield with five +1/+1 counters on it.
		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5))));
		
		// {1}, Remove a +1/+1 counter from a creature you control: Put a 1/1 green Saproling creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new GenericManaCost(1));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        this.addAbility(ability);
		
		// {1}, Sacrifice a creature: Put a +1/+1 counter on target creature.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1));
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability2.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability2);
    }

    public GhaveGuruOfSpores(final GhaveGuruOfSpores card) {
        super(card);
    }

    @Override
    public GhaveGuruOfSpores copy() {
        return new GhaveGuruOfSpores(this);
    }

}
