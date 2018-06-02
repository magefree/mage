
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Styxo
 */
public final class Order66 extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public Order66(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{B}{B}");

        // Put a bounty counter on each creature you don't control, then destroy all creatures you don't control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.BOUNTY.createInstance(), filter));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));

    }

    public Order66(final Order66 card) {
        super(card);
    }

    @Override
    public Order66 copy() {
        return new Order66(this);
    }
}
