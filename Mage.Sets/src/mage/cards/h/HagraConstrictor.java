package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HagraConstrictor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public HagraConstrictor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Hagra Constrictor enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"
        ));

        // Each creature you control with a +1/+1 counter on it has menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, filter,
                "Each creature you control with a +1/+1 counter on it has menace. " +
                        "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"
        )));
    }

    private HagraConstrictor(final HagraConstrictor card) {
        super(card);
    }

    @Override
    public HagraConstrictor copy() {
        return new HagraConstrictor(this);
    }
}
