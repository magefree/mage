package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AdaptiveGemguard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public AdaptiveGemguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap two untapped artifacts and/or creatures you control: Put a +1/+1 counter on Adaptive Gemguard. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new TapTargetCost(new TargetControlledPermanent(2, filter))));

    }

    private AdaptiveGemguard(final AdaptiveGemguard card) {
        super(card);
    }

    @Override
    public AdaptiveGemguard copy() {
        return new AdaptiveGemguard(this);
    }
}
