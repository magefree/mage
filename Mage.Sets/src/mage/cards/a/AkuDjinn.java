
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class AkuDjinn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature each opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public AkuDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, put a +1/+1 counter on each creature each opponent controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), TargetController.YOU, false));
    }

    public AkuDjinn(final AkuDjinn card) {
        super(card);
    }

    @Override
    public AkuDjinn copy() {
        return new AkuDjinn(this);
    }
}
