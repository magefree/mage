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
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public class HeartOfKiran extends CardImpl {

    public HeartOfKiran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // You may remove a loyalty counter from a planeswalker you control rather than pay Heart of Kiran's crew cost.
        Cost cost = new HeartOfKiranAlternateCrewCost(CounterType.LOYALTY, 1);
        Effect effect = new AddCardTypeSourceEffect(CardType.CREATURE, Duration.EndOfTurn);
        effect.setText("You may remove a loyalty counter from a planeswalker you control rather than pay {this}'s crew cost");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, cost));
    }

    public HeartOfKiran(final HeartOfKiran card) {
        super(card);
    }

    @Override
    public HeartOfKiran copy() {
        return new HeartOfKiran(this);
    }
}

class HeartOfKiranAlternateCrewCost extends CostImpl {

    private CounterType counterTypeToRemove;
    private int countersToRemove;

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalker you control");

    public HeartOfKiranAlternateCrewCost(CounterType counterTypeToRemove, int countersToRemove) {
        this.counterTypeToRemove = counterTypeToRemove;
        this.countersToRemove = countersToRemove;
    }

    public HeartOfKiranAlternateCrewCost(final HeartOfKiranAlternateCrewCost cost) {
        super(cost);
        this.counterTypeToRemove = cost.counterTypeToRemove;
        this.countersToRemove = cost.countersToRemove;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;

        Target target = new TargetControlledPermanent(1, 1, filter, true);

        if (target.choose(Outcome.Benefit, controllerId, sourceId, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            int originalLoyalty = permanent.getCounters(game).getCount(counterTypeToRemove);

            GameEvent event = new GameEvent(GameEvent.EventType.CREW_VEHICLE, target.getFirstTarget(), sourceId, controllerId);
            if (!game.replaceEvent(event)) {
                permanent.removeCounters(counterTypeToRemove.createInstance(), game);
            }

            paid = permanent.getCounters(game).getCount(counterTypeToRemove) < originalLoyalty;

            if (paid) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREWED_VEHICLE, target.getFirstTarget(), sourceId, controllerId));
            }
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return !game.getBattlefield().getAllActivePermanents(filter, game).isEmpty();
    }

    @Override
    public HeartOfKiranAlternateCrewCost copy() {
        return new HeartOfKiranAlternateCrewCost(this);
    }
}
