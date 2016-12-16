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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class SyndicateEnforcerSWS extends CardImpl {

    public SyndicateEnforcerSWS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add("Gand");
        this.subtype.add("Hunter");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When {this} enters the battlefield, put a bounty counter on target creature an opponent controls 
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever a bounty counter is placed on a creature an opponents controls, that creature can't block this turn.
        this.addAbility(new SyndicateEnforcerTriggeredAbility());
    }

    public SyndicateEnforcerSWS(final SyndicateEnforcerSWS card) {
        super(card);
    }

    @Override
    public SyndicateEnforcerSWS copy() {
        return new SyndicateEnforcerSWS(this);
    }
}

class SyndicateEnforcerTriggeredAbility extends TriggeredAbilityImpl {

    private static final Effect effect = new CantBlockTargetEffect(Duration.EndOfTurn);

    public SyndicateEnforcerTriggeredAbility() {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public SyndicateEnforcerTriggeredAbility(final SyndicateEnforcerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyndicateEnforcerTriggeredAbility copy() {
        return new SyndicateEnforcerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.BOUNTY.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a bounty counter is placed on a creature an opponents controls, that creature can't block this turn.";
    }
}
