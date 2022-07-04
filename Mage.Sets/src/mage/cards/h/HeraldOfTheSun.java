package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfTheSun extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another target creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(AnotherPredicate.instance);
    }

    public HeraldOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Put a +1/+1 counter on another target creature with flying.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{W}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HeraldOfTheSun(final HeraldOfTheSun card) {
        super(card);
    }

    @Override
    public HeraldOfTheSun copy() {
        return new HeraldOfTheSun(this);
    }
}
