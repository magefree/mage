
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author ilcartographer
 */
public final class AcidRain extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Forests");
    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public AcidRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Destroy all Forests.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private AcidRain(final AcidRain card) {
        super(card);
    }

    @Override
    public AcidRain copy() {
        return new AcidRain(this);
    }
}
