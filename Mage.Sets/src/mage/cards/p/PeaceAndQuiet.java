
package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class PeaceAndQuiet extends CardImpl {

    public PeaceAndQuiet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Destroy two target enchantments.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
    }

    private PeaceAndQuiet(final PeaceAndQuiet card) {
        super(card);
    }

    @Override
    public PeaceAndQuiet copy() {
        return new PeaceAndQuiet(this);
    }
}
