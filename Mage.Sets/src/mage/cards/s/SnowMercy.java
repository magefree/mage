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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class SnowMercy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with globe counters on them");

    static {
        filter.add(new CounterPredicate(CounterType.GLOBE));
    }

    public SnowMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        addSuperType(SuperType.SNOW);

        // Whenever a creature deals damage to you, put a globe counter on it.
        this.addAbility(new AddGlobeCountersAbility());

        // {t},{q},{t},{q},{t}: Tap all creatures with globe counters on them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(filter), new SnowMercyCost());
        this.addAbility(ability);
    }

    public SnowMercy(final SnowMercy card) {
        super(card);
    }

    @Override
    public SnowMercy copy() {
        return new SnowMercy(this);
    }
}

class AddGlobeCountersAbility extends TriggeredAbilityImpl {

    public AddGlobeCountersAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.GLOBE.createInstance()));
    }

    public AddGlobeCountersAbility(final AddGlobeCountersAbility ability) {
        super(ability);
    }

    @Override
    public AddGlobeCountersAbility copy() {
        return new AddGlobeCountersAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals damage to you, put a globe counter on it.";
    }

}

class SnowMercyCost extends CostImpl {

    SnowMercyCost() {
        this.text = "{t}, {q}, {t}, {q}, {t}";
    }

    SnowMercyCost(final SnowMercyCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        Permanent permanent = game.getPermanent(sourceId);
        if (controller != null && permanent != null) {
            // Tap, Untap, Tap, Untap, Tap:
            if (!permanent.isTapped() && permanent.tap(game)) {
                if (permanent.isTapped() && permanent.untap(game)) {
                    if (!permanent.isTapped() && permanent.tap(game)) {
                        if (permanent.isTapped() && permanent.untap(game)) {
                            if (!permanent.isTapped() && permanent.tap(game)) {
                                paid = true;
                            }
                        }
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            return !permanent.isTapped();
        }
        return false;
    }

    @Override
    public SnowMercyCost copy() {
        return new SnowMercyCost(this);
    }
}
