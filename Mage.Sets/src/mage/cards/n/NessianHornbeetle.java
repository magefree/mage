package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NessianHornbeetle extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public NessianHornbeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, if you control another creature with power 4 or greater, put a +1/+1 counter on Nessian Hornbeetle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        TargetController.YOU, false
                ), condition, "At the beginning of combat on your turn, if you control " +
                "another creature with power 4 or greater, put a +1/+1 counter on {this}."
        ));
    }

    private NessianHornbeetle(final NessianHornbeetle card) {
        super(card);
    }

    @Override
    public NessianHornbeetle copy() {
        return new NessianHornbeetle(this);
    }
}
