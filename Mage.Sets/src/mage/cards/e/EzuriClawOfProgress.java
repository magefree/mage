package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EzuriClawOfProgress extends CardImpl {

    final private static FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with power 2 or less");
    final private static FilterPermanent filter2
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public EzuriClawOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature with power 2 or less you control enters, you get an experience counter.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU), filter
        ));

        // At the beginning of combat on your turn, put X +1/+1 counters on another target creature you control, where X is the number of experience counters you have.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), xValue)
                        .setText("put X +1/+1 counters on another target creature you control, " +
                                "where X is the number of experience counters you have")
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private EzuriClawOfProgress(final EzuriClawOfProgress card) {
        super(card);
    }

    @Override
    public EzuriClawOfProgress copy() {
        return new EzuriClawOfProgress(this);
    }
}
