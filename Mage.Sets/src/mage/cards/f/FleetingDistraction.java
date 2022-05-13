

package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class FleetingDistraction extends CardImpl {

    public FleetingDistraction (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public FleetingDistraction (final FleetingDistraction card) {
        super(card);
    }

    @Override
    public FleetingDistraction copy() {
        return new FleetingDistraction(this);
    }

}
