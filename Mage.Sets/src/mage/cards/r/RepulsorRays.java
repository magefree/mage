package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RepulsorRays extends CardImpl {

    public RepulsorRays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Repulsor Rays deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RepulsorRays(final RepulsorRays card) {
        super(card);
    }

    @Override
    public RepulsorRays copy() {
        return new RepulsorRays(this);
    }
}
