
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author spjspj
 */
public final class UnfriendlyFire extends CardImpl {

    public UnfriendlyFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Unfriendly Fire deals 4 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
    }

    private UnfriendlyFire(final UnfriendlyFire card) {
        super(card);
    }

    @Override
    public UnfriendlyFire copy() {
        return new UnfriendlyFire(this);
    }
}
