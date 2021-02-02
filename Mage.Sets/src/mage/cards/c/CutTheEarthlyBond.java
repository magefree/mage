
package mage.cards.c;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CutTheEarthlyBond extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchanted permanent");
    static {
        filter.add(EnchantedPredicate.instance);
    }

    public CutTheEarthlyBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");
        this.subtype.add(SubType.ARCANE);


        // Return target enchanted permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);

    }

    private CutTheEarthlyBond(final CutTheEarthlyBond card) {
        super(card);
    }

    @Override
    public CutTheEarthlyBond copy() {
        return new CutTheEarthlyBond(this);
    }
}
