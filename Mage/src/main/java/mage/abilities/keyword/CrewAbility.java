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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author emerald000
 */
public class CrewAbility extends SimpleActivatedAbility {

    private final int value;

    public CrewAbility(int value) {
        super(Zone.BATTLEFIELD, new AddCardTypeSourceEffect(CardType.ARTIFACT, Duration.EndOfTurn), new CrewCost(value));
        this.addEffect(new AddCardTypeSourceEffect(CardType.CREATURE, Duration.EndOfTurn));
        this.value = value;
    }

    public CrewAbility(final CrewAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    @Override
    public CrewAbility copy() {
        return new CrewAbility(this);
    }

    @Override
    public String getRule() {
        return "Crew " + value + " <i>(Tap any number of creatures you control with total power " + value + " or more: This Vehicle becomes an artifact creature until end of turn.)</i>";
    }
}

class CrewCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    private final int value;

    CrewCost(int value) {
        this.value = value;
    }

    CrewCost(final CrewCost cost) {
        super(cost);
        this.value = cost.value;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            int sumPower = 0;
            for (UUID targetId : target.getTargets()) {
                GameEvent event = new GameEvent(GameEvent.EventType.CREW_VEHICLE, targetId, sourceId, controllerId);
                if (!game.replaceEvent(event)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.tap(game)) {
                        sumPower += permanent.getPower().getValue();
                    }
                }
            }
            paid = sumPower >= value;
            if (paid) {
                for (UUID targetId : target.getTargets()) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREWED_VEHICLE, targetId, sourceId, controllerId));
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            sumPower += permanent.getPower().getValue();
            if (sumPower >= value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CrewCost copy() {
        return new CrewCost(this);
    }
}
