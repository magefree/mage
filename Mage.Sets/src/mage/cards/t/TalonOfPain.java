package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
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
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jerekwilson
 */
public final class TalonOfPain extends CardImpl {

    public TalonOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        /*
         * Whenever a source you control other than Talon of Pain deals damage to an opponent,
         * put a charge counter on Talon of Pain.
         */
        this.addAbility(new TalonOfPainTriggeredAbility());

        // {X}, {T}, Remove X charge counters from Talon of Pain: Talon of Pain deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TalonOfPainRemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private TalonOfPain(final TalonOfPain card) {
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
                // return true so the effect will fire and a charge counter will be added
                return sourceControllerId != null
                        && sourceControllerId.equals(this.getControllerId())
                        && !this.getSourceId().equals(event.getSourceId());
            }
            return false;
        }

        @Override
        public String getTriggerPhrase() {
            return "Whenever a source you control other than {this} deals damage to an opponent, ";
        }
    }
}

class TalonOfPainRemoveVariableCountersSourceCost extends VariableCostImpl {

    protected int minimalCountersToPay = 0;
    private final String counterName;

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
        super(VariableCostType.NORMAL, counter.getName() + " counters to remove");
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
