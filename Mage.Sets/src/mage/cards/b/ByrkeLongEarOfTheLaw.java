package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ByrkeLongEarOfTheLaw extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public ByrkeLongEarOfTheLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT, SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Byrke, Long Ear of the Law enters, put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to two target creatures")
        );
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENT_CREATURES));
        this.addAbility(ability);

        // Whenever a creature you control with a +1/+1 counter on it attacks, double the number of +1/+1 counters on it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new DoubleCountersTargetEffect(CounterType.P1P1), false, filter, true));
    }

    private ByrkeLongEarOfTheLaw(final ByrkeLongEarOfTheLaw card) {
        super(card);
    }

    @Override
    public ByrkeLongEarOfTheLaw copy() {
        return new ByrkeLongEarOfTheLaw(this);
    }
}
