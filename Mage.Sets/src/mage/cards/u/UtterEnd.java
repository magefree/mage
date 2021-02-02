
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class UtterEnd extends CardImpl {

    public UtterEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{B}");


        // Exile target nonland permanent.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private UtterEnd(final UtterEnd card) {
        super(card);
    }

    @Override
    public UtterEnd copy() {
        return new UtterEnd(this);
    }
}
