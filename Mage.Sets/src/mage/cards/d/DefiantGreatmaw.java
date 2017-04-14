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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class DefiantGreatmaw extends CardImpl {

    public DefiantGreatmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add("Hippo");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Defiant Greatmaw enters the battlefield, put two -1/-1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more -1/-1 counters on Defiant Greatmaw, remove a -1/-1 counter from another target creature you control.
        this.addAbility(new DefiantGreatmawTriggeredAbility());
    }

    public DefiantGreatmaw(final DefiantGreatmaw card) {
        super(card);
    }

    @Override
    public DefiantGreatmaw copy() {
        return new DefiantGreatmaw(this);
    }
}

class DefiantGreatmawTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    DefiantGreatmawTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterTargetEffect(CounterType.M1M1.createInstance(1)), false);
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    DefiantGreatmawTriggeredAbility(final DefiantGreatmawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean weAreDoingIt = getControllerId().equals(game.getControllerId(event.getSourceId()));
        boolean isM1M1Counters = event.getData().equals(CounterType.M1M1.getName());
        if (weAreDoingIt && isM1M1Counters && event.getTargetId().equals(this.getSourceId())) {
                return true;
            }
        return false;
    }

    @Override
    public DefiantGreatmawTriggeredAbility copy() {
        return new DefiantGreatmawTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more -1/-1 counters on {this}, remove a -1/-1 counter from another target creature you control.";
    }
}
