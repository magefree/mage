
package mage.cards.f;

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
public final class FireAmbush extends CardImpl {

    public FireAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Fire Ambush deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FireAmbush(final FireAmbush card) {
        super(card);
    }

    @Override
    public FireAmbush copy() {
        return new FireAmbush(this);
    }
}
