
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author jonubuu
 */
public final class Shatterstorm extends CardImpl {

    public Shatterstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Destroy all artifacts. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterArtifactPermanent("artifacts"), true));
    }

    private Shatterstorm(final Shatterstorm card) {
        super(card);
    }

    @Override
    public Shatterstorm copy() {
        return new Shatterstorm(this);
    }
}
