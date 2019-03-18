
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VishKalBloodArbiter extends CardImpl {

    public VishKalBloodArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Sacrifice a creature: Put X +1/+1 counters on Vish Kal, Blood Arbiter, where X is the sacrificed creature's power.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), SacrificeCostCreaturesPower.instance, true),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT))));
        // Remove all +1/+1 counters from Vish Kal: Target creature gets -1/-1 until end of turn for each +1/+1 counter removed this way.
        DynamicValue removedCounters = new SignInversionDynamicValue(new VishKalBloodArbiterDynamicValue());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(removedCounters, removedCounters, Duration.EndOfTurn), new VishKalBloodArbiterCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public VishKalBloodArbiter(final VishKalBloodArbiter card) {
        super(card);
    }

    @Override
    public VishKalBloodArbiter copy() {
        return new VishKalBloodArbiter(this);
    }
}

class VishKalBloodArbiterCost extends CostImpl {

    private int amount;
    private String name;

    public VishKalBloodArbiterCost(Counter counter) {
        this.name = counter.getName();
        this.amount = counter.getCount();
        this.text = "Remove all " + name + " counters from {this}";
    }

    public VishKalBloodArbiterCost(VishKalBloodArbiterCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            this.amount = permanent.getCounters(game).getCount(name);
            permanent.removeCounters(name, amount, game);
            this.paid = true;
        } else {
            this.amount = 0;
        }
        return paid;
    }

    @Override
    public VishKalBloodArbiterCost copy() {
        return new VishKalBloodArbiterCost(this);
    }

    public int getAmount() {
        return amount;
    }

}

class VishKalBloodArbiterDynamicValue implements DynamicValue {

    public VishKalBloodArbiterDynamicValue() {

    }

    public VishKalBloodArbiterDynamicValue(final VishKalBloodArbiterDynamicValue dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof VishKalBloodArbiterCost) {
                count += ((VishKalBloodArbiterCost) cost).getAmount();
            }
        }
        return count;
    }

    @Override
    public VishKalBloodArbiterDynamicValue copy() {
        return new VishKalBloodArbiterDynamicValue(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of +1/+1 counters removed this way";
    }
}
