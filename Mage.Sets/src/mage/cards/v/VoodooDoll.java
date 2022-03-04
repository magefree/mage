package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class VoodooDoll extends CardImpl {

    public VoodooDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of your upkeep, put a pin counter on Voodoo Doll.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PIN.createInstance()), TargetController.YOU, false
        ));

        // At the beginning of your end step, if Voodoo Doll is untapped, destroy Voodoo Doll and it deals damage to you equal to the number of pin counters on it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DestroySourceEffect(), TargetController.YOU, false
                ), SourceTappedCondition.UNTAPPED, "At the beginning of your end step, " +
                "if {this} is untapped, destroy {this} and it deals damage to you equal to the number of pin counters on it."
        );
        ability.addEffect(new DamageControllerEffect(new CountersSourceCount(CounterType.PIN)));
        this.addAbility(ability);

        // {X}{X}, {T}: Voodoo Doll deals damage equal to the number of pin counters on it to any target. X is the number of pin counters on Voodoo Doll.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(new CountersSourceCount(CounterType.PIN)), new ManaCostsImpl("{X}{X}")
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
    public void adjustCosts(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (sourcePermanent != null) {
            int pin = sourcePermanent.getCounters(game).getCount(CounterType.PIN);
            ability.getManaCostsToPay().clear();
            ability.getManaCostsToPay().add(0, new GenericManaCost(pin * 2));
        }
    }
}
