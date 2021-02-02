
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class FlameJab extends CardImpl {

    public FlameJab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Flame Jab deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private FlameJab(final FlameJab card) {
        super(card);
    }

    @Override
    public FlameJab copy() {
        return new FlameJab(this);
    }
}
