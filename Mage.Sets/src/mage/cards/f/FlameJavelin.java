
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class FlameJavelin extends CardImpl {

    public FlameJavelin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2/R}{2/R}{2/R}");


        // Flame Javelin deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FlameJavelin(final FlameJavelin card) {
        super(card);
    }

    @Override
    public FlameJavelin copy() {
        return new FlameJavelin(this);
    }
}
