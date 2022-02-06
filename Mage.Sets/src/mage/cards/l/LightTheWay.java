package mage.cards.l;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class LightTheWay extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), SubType.VEHICLE.getPredicate()));
    }

    public LightTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one —
        // • Put a +1/+1 counter on target creature or Vehicle. Untap it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Return target permanent you control to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private LightTheWay(final LightTheWay card) {
        super(card);
    }

    @Override
    public LightTheWay copy() {
        return new LightTheWay(this);
    }
}
