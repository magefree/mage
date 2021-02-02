
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HighspireInfusion extends CardImpl {

    public HighspireInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 until end of turn. You get {E}{E}.
        getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    private HighspireInfusion(final HighspireInfusion card) {
        super(card);
    }

    @Override
    public HighspireInfusion copy() {
        return new HighspireInfusion(this);
    }
}
