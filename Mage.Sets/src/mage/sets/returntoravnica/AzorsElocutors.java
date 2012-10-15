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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.GameEvent;


/**
 *
 * @author LevelX2
 */
public class AzorsElocutors extends CardImpl<AzorsElocutors> {

    public AzorsElocutors(UUID ownerId) {
        super(ownerId, 210, "Azor's Elocutors", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(new Counter("filibuster",1)), TargetController.YOU, false));

        // Whenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.
        this.addAbility(new AzorsElocutorsTriggeredAbility());

    }

    public AzorsElocutors(final AzorsElocutors card) {
        super(card);
    }

    @Override
    public AzorsElocutors copy() {
        return new AzorsElocutors(this);
    }
}
class AzorsElocutorsTriggeredAbility extends TriggeredAbilityImpl<AzorsElocutorsTriggeredAbility> {

    public AzorsElocutorsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new RemoveCounterSourceEffect(new Counter("filibuster",1)), false);
    }

    public AzorsElocutorsTriggeredAbility(final AzorsElocutorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AzorsElocutorsTriggeredAbility copy() {
        return new AzorsElocutorsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to you, " + super.getRule();
    }

}
