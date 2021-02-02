
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author fireshoes
 */
public final class IncendiaryFlow extends CardImpl {

    public IncendiaryFlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Incendiary Flow deals 3 damage to any target. If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        Effect effect = new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn);
        effect.setText("If a creature dealt damage this way would die this turn, exile it instead");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private IncendiaryFlow(final IncendiaryFlow card) {
        super(card);
    }

    @Override
    public IncendiaryFlow copy() {
        return new IncendiaryFlow(this);
    }
}
