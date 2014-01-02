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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.common.PlusOneCounter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author cbt33
 */
public class SavageFirecat extends CardImpl<SavageFirecat> {

    public SavageFirecat(UUID ownerId) {
        super(ownerId, 218, "Savage Firecat", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Elemental");
        this.subtype.add("Cat");

        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Savage Firecat enters the battlefield with seven +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(new PlusOneCounter(7))));
        // Whenever you tap a land for mana, remove a +1/+1 counter from Savage Firecat.
        this.addAbility(new SavageFirecatTriggeredAbility());
    }

    public SavageFirecat(final SavageFirecat card) {
        super(card);
    }

    @Override
    public SavageFirecat copy() {
        return new SavageFirecat(this);
    }
}

class SavageFirecatTriggeredAbility extends TriggeredAbilityImpl<SavageFirecatTriggeredAbility> {

    public SavageFirecatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(new PlusOneCounter(1)), false);
    }

    public SavageFirecatTriggeredAbility(final SavageFirecatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SavageFirecatTriggeredAbility copy() {
            return new SavageFirecatTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.TAPPED_FOR_MANA && 
                game.getCard(event.getSourceId()).getCardType().contains(CardType.LAND) &&
                event.getPlayerId().equals(this.controllerId)){
            return true;
        }
    return false;
}
    
}