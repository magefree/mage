
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Plopman
 */
public final class WildwoodRebirth extends CardImpl {

    public WildwoodRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    public WildwoodRebirth(final WildwoodRebirth card) {
        super(card);
    }

    @Override
    public WildwoodRebirth copy() {
        return new WildwoodRebirth(this);
    }
}
