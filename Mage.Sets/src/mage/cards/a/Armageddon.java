
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class Armageddon extends CardImpl {

    public Armageddon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");


        // Destroy all lands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS));
    }

    private Armageddon(final Armageddon card) {
        super(card);
    }

    @Override
    public Armageddon copy() {
        return new Armageddon(this);
    }
}
