package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class HornbashMentor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("non-Human creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creature you control with trample");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
        filter2.add(new AbilityPredicate(TrampleAbility.class));
    }

    public HornbashMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hornbash Mentor enters the battlefield, put a trample counter on target non-Human creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.TRAMPLE.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {2}{G}, {T}: Put a +1/+1 counter on each creature you control with trample.
        ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter2), new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HornbashMentor(final HornbashMentor card) {
        super(card);
    }

    @Override
    public HornbashMentor copy() {
        return new HornbashMentor(this);
    }
}
