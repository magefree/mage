
package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WeldingSparks extends CardImpl {

    public WeldingSparks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Welding Sparks deals X damage to target creature, where X is 3 plus the number of artifacts you control.
        Effect effect = new DamageTargetEffect(new IntPlusDynamicValue(3, new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent("artifacts you control"))));
        effect.setText("{this} deals X damage to target creature, where X is 3 plus the number of artifacts you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WeldingSparks(final WeldingSparks card) {
        super(card);
    }

    @Override
    public WeldingSparks copy() {
        return new WeldingSparks(this);
    }
}
