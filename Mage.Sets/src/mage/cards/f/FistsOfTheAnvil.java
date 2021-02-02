
package mage.cards.f;

import java.util.UUID;
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
public final class FistsOfTheAnvil extends CardImpl {

    public FistsOfTheAnvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FistsOfTheAnvil(final FistsOfTheAnvil card) {
        super(card);
    }

    @Override
    public FistsOfTheAnvil copy() {
        return new FistsOfTheAnvil(this);
    }
}
