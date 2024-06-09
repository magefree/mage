package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class WrapInVigor extends CardImpl {
    
    public WrapInVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Regenerate each creature you control.
        this.getSpellAbility().addEffect(new RegenerateAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    private WrapInVigor(final WrapInVigor card) {
        super(card);
    }

    @Override
    public WrapInVigor copy() {
        return new WrapInVigor(this);
    }
}
