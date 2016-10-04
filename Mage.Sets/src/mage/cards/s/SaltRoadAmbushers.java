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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public class SaltRoadAmbushers extends CardImpl {

    public SaltRoadAmbushers(UUID ownerId) {
        super(ownerId, 198, "Salt Road Ambushers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Hound");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another permanent you control is turned face up, if it's a creature, put two +1/+1 counters on it.
        this.addAbility(new SaltRoadAmbushersTriggeredAbility());
        
        // Megamorph {3}{G}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{3}{G}{G}"), true));
    }

    public SaltRoadAmbushers(final SaltRoadAmbushers card) {
        super(card);
    }

    @Override
    public SaltRoadAmbushers copy() {
        return new SaltRoadAmbushers(this);
    }
}

class SaltRoadAmbushersTriggeredAbility extends TurnedFaceUpAllTriggeredAbility {
    
    
private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another permanent you control");

    static {
        filter.add(new AnotherPredicate());
    }
    
    public SaltRoadAmbushersTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), filter, true);
    }

    public SaltRoadAmbushersTriggeredAbility(final SaltRoadAmbushersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SaltRoadAmbushersTriggeredAbility copy() {
        return new SaltRoadAmbushersTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another permanent you control is turned face up, if it's a creature, put two +1/+1 counters on it.";
    }
}