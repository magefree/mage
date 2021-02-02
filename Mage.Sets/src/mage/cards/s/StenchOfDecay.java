
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class StenchOfDecay extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public StenchOfDecay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Nonartifact creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false));
    }

    private StenchOfDecay(final StenchOfDecay card) {
        super(card);
    }

    @Override
    public StenchOfDecay copy() {
        return new StenchOfDecay(this);
    }
}
