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
package mage.sets.planarchaos;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class PallidMycoderm extends CardImpl<PallidMycoderm> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature you control that's a Fungus or a Saproling");
    private static final FilterControlledCreaturePermanent filterSaproling = new FilterControlledCreaturePermanent("Saproling");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.or(new SubtypePredicate("Fungus"), new SubtypePredicate("Saproling")));
        filterSaproling.add(new SubtypePredicate("Saproling"));
    }

    public PallidMycoderm(UUID ownerId) {
        super(ownerId, 10, "Pallid Mycoderm", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Fungus");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, put a spore counter on Pallid Mycoderm.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        // Remove three spore counters from Pallid Mycoderm: Put a 1/1 green Saproling creature token onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
        // Sacrifice a Saproling: Each creature you control that's a Fungus or a Saproling gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new BoostAllEffect(1,1,Duration.EndOfTurn, filter, false), 
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filterSaproling, false))));
        
    }

    public PallidMycoderm(final PallidMycoderm card) {
        super(card);
    }

    @Override
    public PallidMycoderm copy() {
        return new PallidMycoderm(this);
    }
}
