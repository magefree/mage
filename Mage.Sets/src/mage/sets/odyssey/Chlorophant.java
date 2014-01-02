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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author cbt33
 */
public class Chlorophant extends CardImpl<Chlorophant> {

    public Chlorophant(UUID ownerId) {
        super(ownerId, 234, "Chlorophant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may put a +1/+1 counter on Chlorophant.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, true));
        // Threshold - As long as seven or more cards are in your graveyard, Chlorophant has "At the beginning of your upkeep, you may put another +1/+1 counter on Chlorophant."
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, true),
                                                        new CardsInControllerGraveCondition(7),
                                                        "<i>Threshold</i> As long as seven or more cards are in your graveyard, {this} has \"At the beginning of your upkeep, you may put another +1/+1 counter on {this}\""));
    }

    public Chlorophant(final Chlorophant card) {
        super(card);
    }

    @Override
    public Chlorophant copy() {
        return new Chlorophant(this);
    }
}
