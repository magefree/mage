
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class Purify extends CardImpl {

    public Purify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        // Destroy all artifacts and enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS));
    }

    private Purify(final Purify card) {
        super(card);
    }

    @Override
    public Purify copy() {
        return new Purify(this);
    }
}
