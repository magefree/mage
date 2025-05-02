

package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class GuardiansPledge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");

        static {
            filter.add(new ColorPredicate(ObjectColor.WHITE));
        }

        public GuardiansPledge (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn, filter, false));
    }

    private GuardiansPledge(final GuardiansPledge card) {
        super(card);
    }

    @Override
    public GuardiansPledge copy() {
        return new GuardiansPledge(this);
    }

}
