package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseAndShine extends CardImpl {

    static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public RiseAndShine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target noncreature artifact you control becomes a 0/0 artifact creature. Put four +1/+1 counters on each artifact that became a creature this way.
        // Overload {4}{U}{U}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{4}{U}{U}"), new TargetPermanent(filter),
            new AddCardTypeTargetEffect(Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE).setText("Target noncreature artifact you control becomes"),
            new SetBasePowerToughnessTargetEffect(0, 0, Duration.EndOfGame).setText(" a 0/0 artifact creature"),
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)).setText("Put four +1/+1 counters on each artifact that became a creature this way"));
    }

    private RiseAndShine(final RiseAndShine card) {
        super(card);
    }

    @Override
    public RiseAndShine copy() {
        return new RiseAndShine(this);
    }
}
