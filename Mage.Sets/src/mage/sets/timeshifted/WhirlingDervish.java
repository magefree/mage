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
package mage.sets.timeshifted;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.DealtDamageToAnOpponent;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX
 */
public class WhirlingDervish extends CardImpl<WhirlingDervish> {

    private static final String ruleText = "At the beginning of each end step, if {this} dealt damage to an opponent this turn, put a +1/+1 counter on it.";
    private static final FilterCard filter = new FilterCard("black");

    static {
        filter.setColor(ObjectColor.BLACK);
        filter.setUseColor(true);
    }
    
    public WhirlingDervish(UUID ownerId) {
        super(ownerId, 90, "Whirling Dervish", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.expansionSetCode = "TSB";
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // Protection from black
        this.addAbility(new ProtectionAbility(filter));
        // At the beginning of each end step, if Whirling Dervish dealt damage to an opponent this turn, put a +1/+1 counter on it.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each end step", true, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
	this.addAbility(new ConditionalTriggeredAbility(triggered, new DealtDamageToAnOpponent(), ruleText));
    }

    public WhirlingDervish(final WhirlingDervish card) {
        super(card);
    }

    @Override
    public WhirlingDervish copy() {
        return new WhirlingDervish(this);
    }
}
