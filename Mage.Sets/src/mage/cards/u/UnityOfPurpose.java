
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class UnityOfPurpose extends CardImpl {

    public UnityOfPurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Support 2.
        this.getSpellAbility().addEffect(new SupportEffect(this, 2, false));

        // Untap each creature you control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(
                StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1, "Untap each creature you control with a +1/+1 counter on it"));
    }

    private UnityOfPurpose(final UnityOfPurpose card) {
        super(card);
    }

    @Override
    public UnityOfPurpose copy() {
        return new UnityOfPurpose(this);
    }
}
