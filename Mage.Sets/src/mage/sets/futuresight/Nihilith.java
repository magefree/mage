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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public class Nihilith extends CardImpl {

    public Nihilith(UUID ownerId) {
        super(ownerId, 72, "Nihilith", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Horror");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // Suspend 7-{1}{B}
        this.addAbility(new SuspendAbility(7, new ManaCostsImpl("{1}{B}"), this, false));
        
        // Whenever a card is put into an opponent's graveyard from anywhere, if Nihilith is suspended, you may remove a time counter from Nihilith.
        this.addAbility(new ConditionalTriggeredAbility(
                new PutCardIntoGraveFromAnywhereAllTriggeredAbility(Zone.EXILED, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), true, 
                        new FilterCard(), TargetController.OPPONENT, SetTargetPointer.NONE),
                SuspendedCondition.getInstance(),
                "Whenever a card is put into an opponent's graveyard from anywhere, if {this} is suspended, you may remove a time counter from {this}."
                ));        
        
    }

    public Nihilith(final Nihilith card) {
        super(card);
    }

    @Override
    public Nihilith copy() {
        return new Nihilith(this);
    }
}
