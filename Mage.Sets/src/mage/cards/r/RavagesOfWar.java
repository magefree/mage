
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class RavagesOfWar extends CardImpl {

    public RavagesOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");


        // Destroy all lands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS));
    }

    private RavagesOfWar(final RavagesOfWar card) {
        super(card);
    }

    @Override
    public RavagesOfWar copy() {
        return new RavagesOfWar(this);
    }
}
