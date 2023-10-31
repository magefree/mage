package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author Plopman
 */
public final class MightyEmergence extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public MightyEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature with power 5 or greater enters the battlefield under your control, you may put two +1/+1 counters on it.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(2));
        effect.setText("you may put two +1/+1 counters on it");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filter, true, SetTargetPointer.PERMANENT));
    }

    private MightyEmergence(final MightyEmergence card) {
        super(card);
    }

    @Override
    public MightyEmergence copy() {
        return new MightyEmergence(this);
    }
}
