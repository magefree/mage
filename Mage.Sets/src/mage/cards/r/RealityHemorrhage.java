
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class RealityHemorrhage extends CardImpl {

    public RealityHemorrhage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Reality Hemorrhage deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private RealityHemorrhage(final RealityHemorrhage card) {
        super(card);
    }

    @Override
    public RealityHemorrhage copy() {
        return new RealityHemorrhage(this);
    }
}
