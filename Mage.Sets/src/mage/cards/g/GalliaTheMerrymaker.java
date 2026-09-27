package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GalliaTheMerrymaker extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that entered this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public GalliaTheMerrymaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Each other creature you control with +1/+1 counter on it has haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            HasteAbility.getInstance(),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1,
            "Each other creature you control with a +1/+1 counter on it has haste."
        )));

        // {1}{R}, {T}: Put a +1/+1 counter on target creature that entered this turn.
        Ability ability = new SimpleActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GalliaTheMerrymaker(final GalliaTheMerrymaker card) {
        super(card);
    }

    @Override
    public GalliaTheMerrymaker copy() {
        return new GalliaTheMerrymaker(this);
    }
}
