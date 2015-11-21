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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FungalBehemoth extends CardImpl {

    public FungalBehemoth(UUID ownerId) {
        super(ownerId, 128, "Fungal Behemoth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Fungus");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fungal Behemoth's power and toughness are each equal to the number of +1/+1 counters on creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new P1P1CountersOnControlledCreaturesCount(), Duration.EndOfGame)));

        // Suspend X-{X}{G}{G}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl("{G}{G}"), this, true));

        // Whenever a time counter is removed from Fungal Behemoth while it's exiled, you may put a +1/+1 counter on target creature.
        this.addAbility(new FungalBehemothTriggeredAbility());
    }

    public FungalBehemoth(final FungalBehemoth card) {
        super(card);
    }

    @Override
    public FungalBehemoth copy() {
        return new FungalBehemoth(this);
    }
}

class FungalBehemothTriggeredAbility extends TriggeredAbilityImpl {

    public FungalBehemothTriggeredAbility() {
        super(Zone.EXILED, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true);
        addTarget(new TargetCreaturePermanent());
    }

    public FungalBehemothTriggeredAbility(final FungalBehemothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FungalBehemothTriggeredAbility copy() {
        return new FungalBehemothTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever a time counter is removed from {this} while it's exiled, " + super.getRule();
    }

}

class P1P1CountersOnControlledCreaturesCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), sourceAbility.getControllerId(), game)) {
            count += permanent.getCounters().getCount(CounterType.P1P1);
        }
        return count;
    }

    @Override
    public P1P1CountersOnControlledCreaturesCount copy() {
        return new P1P1CountersOnControlledCreaturesCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of +1/+1 counters on creatures you control";
    }
}
