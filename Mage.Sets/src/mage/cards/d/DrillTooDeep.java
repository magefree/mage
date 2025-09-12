package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DrillTooDeep extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Spacecraft or Planet you control");

    static {
        filter.add(Predicates.or(
                SubType.SPACECRAFT.getPredicate(),
                SubType.PLANET.getPredicate()
        ));
    }

    public DrillTooDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one —
        // • Put five charge counters on target Spacecraft or Planet you control
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.CHARGE.createInstance(5)));
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));

        // • Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private DrillTooDeep(final DrillTooDeep card) {
        super(card);
    }

    @Override
    public DrillTooDeep copy() {
        return new DrillTooDeep(this);
    }
}
