
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class UnyaroBeeSting extends CardImpl {

    public UnyaroBeeSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Unyaro Bee Sting deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private UnyaroBeeSting(final UnyaroBeeSting card) {
        super(card);
    }

    @Override
    public UnyaroBeeSting copy() {
        return new UnyaroBeeSting(this);
    }
}
