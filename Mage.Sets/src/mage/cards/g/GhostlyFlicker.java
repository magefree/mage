package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 * @author noxx
 */
public final class GhostlyFlicker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, creatures, and/or lands you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.ARTIFACT.getPredicate()));
    }

    public GhostlyFlicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(true, true));
    }

    private GhostlyFlicker(final GhostlyFlicker card) {
        super(card);
    }

    @Override
    public GhostlyFlicker copy() {
        return new GhostlyFlicker(this);
    }
}
