
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.TurnFaceUpTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class BreakOpen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Face-down creature an opponent controls");

    static {
        filter.add(new FaceDownPredicate());
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BreakOpen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Turn target face-down creature an opponent controls face up.
        this.getSpellAbility().addEffect(new TurnFaceUpTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public BreakOpen(final BreakOpen card) {
        super(card);
    }

    @Override
    public BreakOpen copy() {
        return new BreakOpen(this);
    }
}
