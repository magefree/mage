package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jmharmon
 */
public final class ChamberSentry extends CardImpl {

    public ChamberSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Chamber Sentry enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true),
                "with a +1/+1 counter on it for each color of mana spent to cast it"));

        // {X}, {T}, Remove X +1/+1 counters from Chamber Sentry: It deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.REGULAR)
                .setText("It deals X damage to any target"),
                new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ChamberSentryRemoveVariableCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {W}{U}{B}{R}{G}: Return Chamber Sentry from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
    }

    private ChamberSentry(final ChamberSentry card) {
        super(card);
    }

    @Override
    public ChamberSentry copy() {
        return new ChamberSentry(this);
    }
}

class ChamberSentryRemoveVariableCountersSourceCost extends VariableCostImpl {

    protected int minimalCountersToPay = 0;
    private final String counterName;

    public ChamberSentryRemoveVariableCountersSourceCost(Counter counter) {
        this(counter, 0);
    }

    public ChamberSentryRemoveVariableCountersSourceCost(Counter counter, String text) {
        this(counter, 0, text);
    }

    public ChamberSentryRemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay) {
        this(counter, minimalCountersToPay, "");
    }

    public ChamberSentryRemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay, String text) {
        super(VariableCostType.NORMAL, counter.getName() + " counters to remove");
        this.minimalCountersToPay = minimalCountersToPay;
        this.counterName = counter.getName();
        if (text == null || text.isEmpty()) {
            this.text = "Remove X " + counterName + " counters from {this}";
        } else {
            this.text = text;
        }
    }

    public ChamberSentryRemoveVariableCountersSourceCost(final ChamberSentryRemoveVariableCountersSourceCost cost) {
        super(cost);
        this.minimalCountersToPay = cost.minimalCountersToPay;
        this.counterName = cost.counterName;
    }

    @Override
    public ChamberSentryRemoveVariableCountersSourceCost copy() {
        return new ChamberSentryRemoveVariableCountersSourceCost(this);
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
