
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class SereneHeart extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Auras");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public SereneHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Destroy all Auras.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private SereneHeart(final SereneHeart card) {
        super(card);
    }

    @Override
    public SereneHeart copy() {
        return new SereneHeart(this);
    }
}
