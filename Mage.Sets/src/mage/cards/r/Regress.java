

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class Regress extends CardImpl {

    public Regress (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Regress(final Regress card) {
        super(card);
    }

    @Override
    public Regress copy() {
        return new Regress(this);
    }

}
