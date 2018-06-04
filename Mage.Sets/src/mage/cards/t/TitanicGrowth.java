

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class TitanicGrowth extends CardImpl {

    public TitanicGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TitanicGrowth(final TitanicGrowth card) {
        super(card);
    }

    @Override
    public TitanicGrowth copy() {
        return new TitanicGrowth(this);
    }

}
