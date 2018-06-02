
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class CowerInFear extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public CowerInFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");


        // Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false));
    }

    public CowerInFear(final CowerInFear card) {
        super(card);
    }

    @Override
    public CowerInFear copy() {
        return new CowerInFear(this);
    }
}
