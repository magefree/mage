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
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public class HeartOfKiran extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalker you control");

    public HeartOfKiran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add("Legendary");
        this.subtype.add("Vehicle");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // You may remove a loyalty counter from a planeswalker you control rather than pay Heart of Kiran's crew cost.
        Cost cost = new RemoveCounterCost(new TargetControlledPermanent(filter), CounterType.LOYALTY, 1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCardTypeSourceEffect(CardType.CREATURE, Duration.EndOfTurn), cost) {
            @Override
            public String getRule() {
                return "You may remove a loyalty counter from a planeswalker you control rather than pay {this}'s crew cost.";
            }
        };
        this.addAbility(ability);
    }

    public HeartOfKiran(final HeartOfKiran card) {
        super(card);
    }

    @Override
    public HeartOfKiran copy() {
        return new HeartOfKiran(this);
    }
}
