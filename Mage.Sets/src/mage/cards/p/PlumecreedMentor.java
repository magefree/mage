package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class PlumecreedMentor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with flying");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creature you control without flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public PlumecreedMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Plumecreed Mentor or another creature you control with flying enters, put a +1/+1 counter on target creature you control without flying.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter, false, true
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private PlumecreedMentor(final PlumecreedMentor card) {
        super(card);
    }

    @Override
    public PlumecreedMentor copy() {
        return new PlumecreedMentor(this);
    }
}
