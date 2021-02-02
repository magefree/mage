
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author Loki
 */
public final class PlanarCleansing extends CardImpl {

    public PlanarCleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}{W}");


        // Destroy all nonland permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterNonlandPermanent("nonland permanents")));
    }

    private PlanarCleansing(final PlanarCleansing card) {
        super(card);
    }

    @Override
    public PlanarCleansing copy() {
        return new PlanarCleansing(this);
    }
}
