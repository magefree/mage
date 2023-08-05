package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VishKalBloodArbiter extends CardImpl {

    public VishKalBloodArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Sacrifice a creature: Put X +1/+1 counters on Vish Kal, Blood Arbiter, where X is the sacrificed creature's power.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(), SacrificeCostCreaturesPower.instance, true
                ).setText("put X +1/+1 counters on {this}, where X is the sacrificed creature's power"),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Remove all +1/+1 counters from Vish Kal: Target creature gets -1/-1 until end of turn for each +1/+1 counter removed this way.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                VishKalBloodArbiterDynamicValue.instance,
                VishKalBloodArbiterDynamicValue.instance,
                Duration.EndOfTurn), new RemoveAllCountersSourceCost(CounterType.P1P1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VishKalBloodArbiter(final VishKalBloodArbiter card) {
        super(card);
    }

    @Override
    public VishKalBloodArbiter copy() {
        return new VishKalBloodArbiter(this);
    }
}

enum VishKalBloodArbiterDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                count += ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return -count;
    }

    @Override
    public VishKalBloodArbiterDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "-1";
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter removed this way";
    }

    @Override
    public int getSign() {
        return -1;
    }
}
