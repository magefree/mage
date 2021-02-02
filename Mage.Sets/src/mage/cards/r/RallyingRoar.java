
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RallyingRoar extends CardImpl {

    public RallyingRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Creatures you control get +1/+1 until end of turn.  Untap them.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "Untap them."));
    }

    private RallyingRoar(final RallyingRoar card) {
        super(card);
    }

    @Override
    public RallyingRoar copy() {
        return new RallyingRoar(this);
    }
}
