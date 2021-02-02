
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author fireshoes
 */
public final class Unsubstantiate extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or creature");

    static {
        filter.setPermanentFilter(new FilterCreaturePermanent());
    }

    public Unsubstantiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Return target spell or creature to its owner's hand.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private Unsubstantiate(final Unsubstantiate card) {
        super(card);
    }

    @Override
    public Unsubstantiate copy() {
        return new Unsubstantiate(this);
    }
}
