
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class VenomousVines extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchanted permanent");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public VenomousVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");

        // Destroy target enchanted permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private VenomousVines(final VenomousVines card) {
        super(card);
    }

    @Override
    public VenomousVines copy() {
        return new VenomousVines(this);
    }
}
