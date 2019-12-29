
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class BellowingAegisaur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(AnotherPredicate.instance);
    }

    public BellowingAegisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Enrage - Whenever Bellowing Aegisaur is dealt damage, put a +1/+1 counter on each other creature you control.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false, true);
        this.addAbility(ability);
    }

    public BellowingAegisaur(final BellowingAegisaur card) {
        super(card);
    }

    @Override
    public BellowingAegisaur copy() {
        return new BellowingAegisaur(this);
    }
}
