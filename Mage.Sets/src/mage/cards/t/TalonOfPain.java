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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jerekwilson
 */
public class TalonOfPain extends CardImpl {

    public TalonOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        /*
         * Whenever a source you control other than Talon of Pain deals damage to an opponent,
         * put a charge counter on Talon of Pain.
         */
        this.addAbility(new TalonOfPainTriggeredAbility());

        // {X}, {T}, Remove X charge counters from Talon of Pain: Talon of Pain deals X damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new ManacostVariableValue()), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TalonOfPainRemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public TalonOfPain(final TalonOfPain card) {
        super(card);
    }

    @Override
    public TalonOfPain copy() {
        return new TalonOfPain(this);
    }

    private class TalonOfPainTriggeredAbility extends TriggeredAbilityImpl {

        public TalonOfPainTriggeredAbility() {
            super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()));
        }

        public TalonOfPainTriggeredAbility(final TalonOfPainTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public TalonOfPainTriggeredAbility copy() {
            return new TalonOfPainTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            // to another player
            Player controller = game.getPlayer(this.getControllerId());
            if (controller == null) {
                return false;
            }
            if (controller.hasOpponent(event.getTargetId(), game)) {
                // a source you control other than Talon of Pain
                UUID sourceControllerId = game.getControllerId(event.getSourceId());
                if (sourceControllerId != null
                        && sourceControllerId.equals(this.getControllerId())
                        && this.getSourceId() != event.getSourceId()) {
                    // return true so the effect will fire and a charge counter will be added
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a source you control other than {this} deals damage to an opponent, " + super.getRule();
        }
    }
}

class TalonOfPainRemoveVariableCountersSourceCost extends VariableCostImpl {

    protected int minimalCountersToPay = 0;
    private String counterName;

    public TalonOfPainRemoveVariableCountersSourceCost(Counter counter) {
        this(counter, 0);
    }

    public TalonOfPainRemoveVariableCountersSourceCost(Counter counter, String text) {
        this(counter, 0, text);
    }

    public TalonOfPainRemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay) {
        this(counter, minimalCountersToPay, "");
    }

    public TalonOfPainRemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay, String text) {
        super(counter.getName() + " counters to remove");
        this.minimalCountersToPay = minimalCountersToPay;
        this.counterName = counter.getName();
        if (text == null || text.isEmpty()) {
            this.text = "Remove X " + counterName + " counters from {this}";
        } else {
            this.text = text;
        }
    }

    public TalonOfPainRemoveVariableCountersSourceCost(final TalonOfPainRemoveVariableCountersSourceCost cost) {
        super(cost);
        this.minimalCountersToPay = cost.minimalCountersToPay;
        this.counterName = cost.counterName;
    }

    @Override
    public TalonOfPainRemoveVariableCountersSourceCost copy() {
        return new TalonOfPainRemoveVariableCountersSourceCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RemoveCountersSourceCost(new Counter(counterName, xValue));
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return minimalCountersToPay;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        int maxValue = 0;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            maxValue = permanent.getCounters(game).getCount(counterName);
        }
        return maxValue;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        return source.getManaCostsToPay().getX();
    }

}
