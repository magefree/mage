package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class VoodooDoll extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.PIN);

    public VoodooDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of your upkeep, put a pin counter on Voodoo Doll.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PIN.createInstance())
        ));

        // At the beginning of your end step, if Voodoo Doll is untapped, destroy Voodoo Doll and it deals damage to you equal to the number of pin counters on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DestroySourceEffect())
                .withInterveningIf(SourceTappedCondition.UNTAPPED);
        ability.addEffect(new DamageControllerEffect(xValue)
                .setText("and it deals damage to you equal to the number of pin counters on it"));
        this.addAbility(ability);

        // {X}{X}, {T}: Voodoo Doll deals damage equal to the number of pin counters on it to any target. X is the number of pin counters on Voodoo Doll.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(new CountersSourceCount(CounterType.PIN))
                        .setText("{this} deals damage equal to the number of pin counters on it " +
                                "to any target. X is the number of pin counters on {this}"),
                new ManaCostsImpl<>("{X}{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.setCostAdjuster(VoodooDollAdjuster.instance);
        this.addAbility(ability);
    }

    private VoodooDoll(final VoodooDoll card) {
        super(card);
    }

    @Override
    public VoodooDoll copy() {
        return new VoodooDoll(this);
    }
}

enum VoodooDollAdjuster implements CostAdjuster {
    instance;

    @Override
    public void prepareCost(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent != null) {
            int pin = sourcePermanent.getCounters(game).getCount(CounterType.PIN);
            ability.clearManaCostsToPay();
            ability.addManaCostsToPay(new GenericManaCost(pin * 2));
        }
    }
}
