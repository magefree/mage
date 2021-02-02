
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author emerald000
 */
public final class Tsunami extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Islands");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public Tsunami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Destroy all Islands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private Tsunami(final Tsunami card) {
        super(card);
    }

    @Override
    public Tsunami copy() {
        return new Tsunami(this);
    }
}
