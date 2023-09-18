package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoaringEarth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("creature or Vehicle you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public RoaringEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a land enters the battlefield under your control, put a +1/+1 counter on target creature or Vehicle you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Channel — {X}{G}{G}, Discard Roaring Earth; Put X +1/+1 counters on target land you control. It becomes a 0/0 green Spirit creature with haste. It's still a land.
        ability = new ChannelAbility("{X}{G}{G}", new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(0), ManacostVariableValue.REGULAR
        ).setText("Put X +1/+1 counters on target land you control."));
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0)
                        .withColor("G")
                        .withSubType(SubType.SPIRIT)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ).setText("It becomes a 0/0 green Spirit creature with haste. It's still a land"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private RoaringEarth(final RoaringEarth card) {
        super(card);
    }

    @Override
    public RoaringEarth copy() {
        return new RoaringEarth(this);
    }
}
