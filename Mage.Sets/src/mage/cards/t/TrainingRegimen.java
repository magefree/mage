package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TrainingRegimen extends CardImpl {

    public TrainingRegimen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Creatures you control with +1/+1 counters on them have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_CONTROLLED_CREATURES_P1P1
        ).setText("creatures you control with +1/+1 counters on them have trample")));

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TrainingRegimen(final TrainingRegimen card) {
        super(card);
    }

    @Override
    public TrainingRegimen copy() {
        return new TrainingRegimen(this);
    }
}
