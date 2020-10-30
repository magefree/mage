
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class UnityOfPurpose extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public UnityOfPurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Support 2.
        getSpellAbility().addEffect(new SupportEffect(this, 2, false));

        // Untap each creature you control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(filter, "Untap each creature you control with a +1/+1 counter on it"));
    }

    public UnityOfPurpose(final UnityOfPurpose card) {
        super(card);
    }

    @Override
    public UnityOfPurpose copy() {
        return new UnityOfPurpose(this);
    }
}
