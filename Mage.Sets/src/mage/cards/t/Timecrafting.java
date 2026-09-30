
package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.target.common.TargetPermanentOrSuspendedCard;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class Timecrafting extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");
    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    public Timecrafting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");

        // Choose one - Remove X time counters from target permanent or suspended card;
        this.getSpellAbility().addEffect(new RemoveCounterTargetEffect(
                CounterType.TIME.createInstance(), GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard());

        // or put X time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode(new AddCountersTargetEffect(
                CounterType.TIME.createInstance(), GetXValue.instance));
        mode.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        this.getSpellAbility().addMode(mode);
    }

    private Timecrafting(final Timecrafting card) {
        super(card);
    }

    @Override
    public Timecrafting copy() {
        return new Timecrafting(this);
    }
}
