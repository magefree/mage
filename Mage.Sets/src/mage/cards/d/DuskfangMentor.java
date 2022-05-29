package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuskfangMentor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("non-Human creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creature you control with lifelink");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
        filter2.add(new AbilityPredicate(LifelinkAbility.class));
    }

    public DuskfangMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Duskfang Mentor enters the battlefield, put a lifelink counter on target non-Human creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.LIFELINK.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}{B}, {T}: Put a +1/+1 counter on each creature you control with lifelink.
        ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter2), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DuskfangMentor(final DuskfangMentor card) {
        super(card);
    }

    @Override
    public DuskfangMentor copy() {
        return new DuskfangMentor(this);
    }
}
