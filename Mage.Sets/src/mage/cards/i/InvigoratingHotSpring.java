package mage.cards.i;

import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvigoratingHotSpring extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("modified creatures");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public InvigoratingHotSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // Invigorating Hot Spring enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(4)
        ), "with four +1/+1 counters on it"));

        // Modified creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Remove a +1/+1 counter from Invigoration Hot Springs: Put a +1/+1 counter on target creature you control. Activate only as a sorcery and only once each turn.
        ActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability);
    }

    private InvigoratingHotSpring(final InvigoratingHotSpring card) {
        super(card);
    }

    @Override
    public InvigoratingHotSpring copy() {
        return new InvigoratingHotSpring(this);
    }
}
