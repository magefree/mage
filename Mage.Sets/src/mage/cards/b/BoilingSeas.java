
package mage.cards.b;

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
public final class BoilingSeas extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Islands");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public BoilingSeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Destroy all Islands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private BoilingSeas(final BoilingSeas card) {
        super(card);
    }

    @Override
    public BoilingSeas copy() {
        return new BoilingSeas(this);
    }
}
