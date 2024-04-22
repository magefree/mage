

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class Demystify extends CardImpl {

    public Demystify (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private Demystify(final Demystify card) {
        super(card);
    }

    @Override
    public Demystify copy() {
        return new Demystify(this);
    }
}
