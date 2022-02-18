
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author TheElk801
 */
public final class Rust extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
    }

    public Rust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Counter target activated ability from an artifact source.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetActivatedAbility(filter));
    }

    private Rust(final Rust card) {
        super(card);
    }

    @Override
    public Rust copy() {
        return new Rust(this);
    }
}
