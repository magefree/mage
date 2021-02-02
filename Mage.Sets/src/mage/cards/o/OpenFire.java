
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class OpenFire extends CardImpl {

    public OpenFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Open Fire deals 3 damage to any target.
        getSpellAbility().addEffect(new DamageTargetEffect(3));
        getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private OpenFire(final OpenFire card) {
        super(card);
    }

    @Override
    public OpenFire copy() {
        return new OpenFire(this);
    }
}
