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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class BloodcrazedHoplite extends CardImpl<BloodcrazedHoplite> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BloodcrazedHoplite(UUID ownerId) {
        super(ownerId, 61, "Bloodcrazed Hoplite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Bloodcrazed Hoplite, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), false)));
        // Whenever a +1/+1 counter is placed on Bloodcrazed Hoplite, remove a +1/+1 counter from target creature an opponent controls.


    }

    public BloodcrazedHoplite(final BloodcrazedHoplite card) {
        super(card);
    }

    @Override
    public BloodcrazedHoplite copy() {
        return new BloodcrazedHoplite(this);
    }
}

class BloodcrazedHopliteTriggeredAbility extends TriggeredAbilityImpl<BloodcrazedHopliteTriggeredAbility> {

    public BloodcrazedHopliteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterTargetEffect(CounterType.P1P1.createInstance()), true);
    }

    public BloodcrazedHopliteTriggeredAbility(BloodcrazedHopliteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            if (event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.P1P1.getName())) {
               return true;
            }
        }
        return false;
    }

    @Override
    public BloodcrazedHopliteTriggeredAbility copy() {
        return new BloodcrazedHopliteTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a +1/+1 counter is placed on {this}, " + super.getRule();
    }
}
