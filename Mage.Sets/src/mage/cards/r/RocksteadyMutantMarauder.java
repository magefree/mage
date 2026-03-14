package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RocksteadyMutantMarauder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public RocksteadyMutantMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Bebop, Skull & Crossbones
        this.addAbility(new PartnerWithAbility("Bebop, Skull & Crossbones"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another nontoken creature you control enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RocksteadyMutantMarauder(final RocksteadyMutantMarauder card) {
        super(card);
    }

    @Override
    public RocksteadyMutantMarauder copy() {
        return new RocksteadyMutantMarauder(this);
    }
}
