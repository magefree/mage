
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class SurgingFlame extends CardImpl {

    public SurgingFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Ripple 4
        this.addAbility(new RippleAbility(4).setRuleAtTheTop(true));

        // Surging Flame deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SurgingFlame(final SurgingFlame card) {
        super(card);
    }

    @Override
    public SurgingFlame copy() {
        return new SurgingFlame(this);
    }
}
